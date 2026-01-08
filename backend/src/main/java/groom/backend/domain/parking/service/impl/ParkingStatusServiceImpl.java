package groom.backend.domain.parking.service.impl;

import groom.backend.domain.parking.dto.response.ChargerStationResponse;
import groom.backend.domain.parking.dto.response.ParkingStatusResponse;
import groom.backend.domain.parking.dto.response.ParkingLotResponse;
import groom.backend.domain.parking.entity.ParkingLot;
import groom.backend.domain.parking.entity.ParkingLotStatus;
import groom.backend.domain.parking.mapper.ParkingMapper;
import groom.backend.domain.parking.repository.*;
import groom.backend.domain.parking.service.spec.ParkingStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ParkingStatusServiceImpl implements ParkingStatusService {
  private final ParkingMapper parkingMapper;

  private final ParkingLotRepository parkingLotRepository;
  private final ParkingLotStatusRepository parkingLotStatusRepository;

  private final ChargerStationRepository chargerStationRepository;
  private final ChargerDetailRepository chargerDetailRepository;
  private final ChargerStatusRepository chargerStatusRepository;

  @Override
  public ParkingStatusResponse getParkingStatus(String areaCode) {
    // 주차장 정보 종합

    List<ParkingLotResponse> parkingLotResponses = getParkingLotStatus(areaCode);

    return ParkingStatusResponse.builder()
            .parkingLots(parkingLotResponses)
            .chargerStations(List.of()) // TODO: 충전소 조회 구현
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
            .map(lot -> parkingMapper.toParkingLotDto(lot, statusMap.get(lot.getPrkCode())))
            .toList();

    return parkingLotResponses;
  }

  private ChargerStationResponse getChargerStationStatus(String areaCode) {
    // 전기차 충전소 위치 및 정보 조회

    return null;
  }
}
