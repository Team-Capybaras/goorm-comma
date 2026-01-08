package groom.backend.domain.parking.service.impl;

import groom.backend.domain.parking.dto.response.ChargerStationResponse;
import groom.backend.domain.parking.dto.response.ParkingStatusResponse;
import groom.backend.domain.parking.dto.response.ParkingLotResponse;
import groom.backend.domain.parking.entity.*;
import groom.backend.domain.parking.mapper.ChargerMapper;
import groom.backend.domain.parking.mapper.ParkingLotMapper;
import groom.backend.domain.parking.repository.*;
import groom.backend.domain.parking.service.spec.ParkingStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParkingStatusServiceImpl implements ParkingStatusService {
  private final ParkingLotMapper parkingLotMapper;
  private final ChargerMapper chargerMapper;

  private final ParkingLotRepository parkingLotRepository;
  private final ParkingLotStatusRepository parkingLotStatusRepository;

  private final ChargerStationRepository chargerStationRepository;
  private final ChargerDetailRepository chargerDetailRepository;
  private final ChargerStatusRepository chargerStatusRepository;

  @Override
  public ParkingStatusResponse getParkingStatus(String areaCode) {
    // 주차장 정보 종합

    List<ParkingLotResponse> parkingLotResponses = getParkingLotStatus(areaCode);
    List<ChargerStationResponse> chargerStationResponses = getChargerStationStatus(areaCode);

    return ParkingStatusResponse.builder()
            .parkingLots(parkingLotResponses)
            .chargerStations(chargerStationResponses)
            .build();
  }

  /**
   * 해당하는 areaCode의 주차장 목록 조회
   * 위치 및 현황 정보를 포함함.
   * @param areaCode 공원 코드
   * @return
   */
  private List<ParkingLotResponse> getParkingLotStatus(String areaCode) {

    // 1) areaCode에 해당하는 주차장만 조회
    List<ParkingLot> lots = parkingLotRepository.findByAreaCode(areaCode);

    // 주차장이 없을 경우 빈 리스트 반환
    if (lots.isEmpty()) {
      return List.of();
    }

    // 2) prkCode 리스트 추출
    List<Long> prkCodes = lots.stream()
            .map(ParkingLot::getPrkCode)
            .toList();

    // 3) 각 주차장의 최신 현황 1건씩을 한 번에 조회
    List<ParkingLotStatus> latestStatuses =
            parkingLotStatusRepository.findLatestStatusesByPrkCodes(prkCodes);

    // 4) prkCode -> latestStatus 로 매핑
    Map<Long, ParkingLotStatus> statusMap = latestStatuses.stream()
            .collect(java.util.stream.Collectors.toMap(
                    ParkingLotStatus::getPrkCode,
                    s -> s
            ));

    // 5) 정적 + 최신현황 병합하여 Response 생성
    List<ParkingLotResponse> parkingLotResponses = lots.stream()
            .map(lot -> parkingLotMapper.toParkingLotDto(lot, statusMap.get(lot.getPrkCode())))
            .toList();

    return parkingLotResponses;
  }

  private List<ChargerStationResponse> getChargerStationStatus(String areaCode) {
    // 전기차 충전소 위치 및 정보 조회
    List<ChargerStation> stations = chargerStationRepository.findByAreaCode(areaCode);

    if(stations.isEmpty()) {
      return List.of();
    }

    // station id 추출
    List<String> stationIds = stations.stream()
            .map(ChargerStation::getStationId)
            .toList();


    // 각 리스트 별 충전기 조회
    List<ChargerDetail> chargers = chargerDetailRepository.findByStationIdIn(stationIds);

    // 충전기 id 추출
    List<Integer> chargerIds = chargers.stream()
            .map(ChargerDetail::getChargerId)
            .toList();

    // 각 충전기 별 최신 현황 조회
    List<ChargerStatus> statuses = chargerStatusRepository.findLatestByChargerKeys(stationIds, chargerIds);

    Map<Integer, ChargerStatus> statusMap =
            statuses.stream()
                    .collect(Collectors.toMap(
                            ChargerStatus::getChargerId,
                            status -> status
                    ));


//    chargerMapper.toChargerStationResponse()
    // 5) 정적 + 최신현황 병합하여 Response 생성
    List<ChargerStationResponse> chargerStationResponses = stations.stream()
            .map(station -> chargerMapper.toChargerStationResponse(station, chargers, statusMap))
            .toList();

    return chargerStationResponses;
  }
}
