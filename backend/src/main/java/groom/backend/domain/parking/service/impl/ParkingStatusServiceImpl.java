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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
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
    log.info("주차장 및 충전소 현황 조회 시작 - AREA_CODE: {}", areaCode);

    List<ParkingLotResponse> parkingLotResponses = getParkingLotStatus(areaCode);
    List<ChargerStationResponse> chargerStationResponses = getChargerStationStatus(areaCode);

    log.info(
            "주차장 및 충전소 현황 조회 완료 - AREA_CODE: {}, 주차장: {}, 충전소: {}",
            areaCode,
            parkingLotResponses.size(),
            chargerStationResponses.size()
    );

    return ParkingStatusResponse.builder()
            .parkingLots(parkingLotResponses)
            .chargerStations(chargerStationResponses)
            .build();
  }

  /**
   * 해당하는 areaCode의 주차장 목록 조회
   */
  private List<ParkingLotResponse> getParkingLotStatus(String areaCode) {

    log.debug("주차장 정보 조회 시작 - AREA_CODE: {}", areaCode);

    List<ParkingLot> lots = parkingLotRepository.findByAreaCode(areaCode);

    if (lots.isEmpty()) {
      log.info("주차장 정보 없음 - AREA_CODE: {}", areaCode);
      return List.of();
    }

    log.debug("주차장 정적 정보 조회 완료 - AREA_CODE: {}, COUNT: {}", areaCode, lots.size());

    List<Long> prkCodes = lots.stream()
            .map(ParkingLot::getPrkCode)
            .toList();

    List<ParkingLotStatus> latestStatuses =
            parkingLotStatusRepository.findLatestStatusesByPrkCodes(prkCodes);

    log.debug(
            "주차장 최신 현황 조회 완료 - AREA_CODE: {}, STATUS_COUNT: {}",
            areaCode,
            latestStatuses.size()
    );

    Map<Long, ParkingLotStatus> statusMap = latestStatuses.stream()
            .collect(Collectors.toMap(
                    ParkingLotStatus::getPrkCode,
                    s -> s
            ));

    List<ParkingLotResponse> parkingLotResponses = lots.stream()
            .map(lot -> parkingLotMapper.toParkingLotDto(lot, statusMap.get(lot.getPrkCode())))
            .toList();

    log.info(
            "주차장 정보 매핑 완료 - AREA_CODE: {}, RESULT_COUNT: {}",
            areaCode,
            parkingLotResponses.size()
    );

    return parkingLotResponses;
  }

  /**
   * 해당하는 areaCode의 충전소 목록 조회
   */
  private List<ChargerStationResponse> getChargerStationStatus(String areaCode) {

    log.debug("충전소 정보 조회 시작 - AREA_CODE: {}", areaCode);

    List<ChargerStation> stations = chargerStationRepository.findByAreaCode(areaCode);

    if (stations.isEmpty()) {
      log.info("충전소 정보 없음 - AREA_CODE: {}", areaCode);
      return List.of();
    }

    log.debug("충전소 정적 정보 조회 완료 - AREA_CODE: {}, COUNT: {}", areaCode, stations.size());

    List<String> stationIds = stations.stream()
            .map(ChargerStation::getStationId)
            .toList();

    List<ChargerDetail> chargers =
            chargerDetailRepository.findByStationIdIn(stationIds);

    log.debug(
            "충전기 상세 정보 조회 완료 - AREA_CODE: {}, CHARGER_COUNT: {}",
            areaCode,
            chargers.size()
    );

    List<Integer> chargerIds = chargers.stream()
            .map(ChargerDetail::getChargerId)
            .toList();

    List<ChargerStatus> statuses =
            chargerStatusRepository.findLatestByChargerKeys(stationIds.toArray(new String[0]), chargerIds.toArray(new Integer[0]));

    log.debug(
            "충전기 최신 상태 조회 완료 - AREA_CODE: {}, STATUS_COUNT: {}",
            areaCode,
            statuses.size()
    );

    Map<Integer, ChargerStatus> statusMap =
            statuses.stream()
                    .collect(Collectors.toMap(
                            ChargerStatus::getChargerId,
                            status -> status
                    ));

    List<ChargerStationResponse> chargerStationResponses = stations.stream()
            .map(station -> chargerMapper.toChargerStationResponse(station, chargers, statusMap))
            .toList();

    log.info(
            "충전소 정보 매핑 완료 - AREA_CODE: {}, RESULT_COUNT: {}",
            areaCode,
            chargerStationResponses.size()
    );

    return chargerStationResponses;
  }
}