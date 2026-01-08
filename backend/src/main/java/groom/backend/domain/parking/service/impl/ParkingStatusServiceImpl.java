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
    // 1. areaCode의 모든 주차장 정적 정보 조회
    List<ParkingLot> parkingLots = parkingLotRepository.findByAreaCode(areaCode);

    // 2. 각 주차장의 최신 현황 조회 및 매핑
    return parkingLots.stream()
            .map(parkingLot -> {
              ParkingLotStatus latestStatus = parkingLotStatusRepository
                              .findTopByPrkCodeOrderByDataGetTimeDesc(parkingLot.getPrkCode());

              return parkingMapper.toParkingLotDto(parkingLot, latestStatus);
            })
            .toList();
  }

  private ChargerStationResponse getChargerStationStatus(String areaCode) {
    // 전기차 충전소 위치 및 정보 조회

    return null;
  }
}
