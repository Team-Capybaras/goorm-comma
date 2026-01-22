package groom.backend.domain.parking.mapper;

import groom.backend.domain.parking.dto.response.ChargerStationResponse;
import groom.backend.domain.parking.dto.response.ParkingLotResponse;
import groom.backend.domain.parking.entity.ChargerStation;
import groom.backend.domain.parking.entity.ParkingLot;
import groom.backend.domain.parking.entity.ParkingLotStatus;
import org.springframework.stereotype.Component;

@Component
public class ParkingLotMapper {

  public ChargerStationResponse toChargerStationDto(ChargerStation station) {
    return ChargerStationResponse.builder()
            .stationId(station.getStationId())
            .stationName(station.getStationName())
            .stationAddr(station.getStationAddr())
            .stationX(station.getStationX())
            .stationY(station.getStationY())
            .stationUsetime(station.getStationUsetime())
            .stationParkpay(station.getStationParkpay())
            .stationKindDetail(station.getStationKindDetail())
            .stationLimitDetail(station.getStationLimitDetail())
            .build();
  }

  public ParkingLotResponse toParkingLotDto(ParkingLot lot, ParkingLotStatus latestStatus) {
    // 현재 정보 제공 현황이 false임에도 기존 DB에서 제공할 수 있음.
    Boolean currentInfoYn = latestStatus != null && lot.getCurrentInfoYn();

    return ParkingLotResponse.builder()
            .prkCode(lot.getPrkCode())
            .prkName(lot.getPrkName())
            .prkType(lot.getPrkType())
            .capacity(lot.getCapacity())
            .payYn(lot.getPayYn())
            .addr(lot.getAddr())
            .roadAddr(lot.getRoadAddr())
            .prkX(lot.getPrkX())
            .prkY(lot.getPrkY())
            // current status field
            // if currentInfoYn is false, then null
            .currentInfoYn(currentInfoYn)
            .currentPrkTime(currentInfoYn ? null : latestStatus.getCurrentPrkTime())
            .currentPrkCnt(currentInfoYn ? null : latestStatus.getCurrentPrkCnt())


            .build();
  }

  public ParkingLotResponse toParkingLotDto(ParkingLot lot) {
    return ParkingLotResponse.builder()
            .prkCode(lot.getPrkCode())
            .prkName(lot.getPrkName())
            .prkType(lot.getPrkType())
            .capacity(lot.getCapacity())
            .payYn(lot.getPayYn())
            .addr(lot.getAddr())
            .roadAddr(lot.getRoadAddr())
            .prkX(lot.getPrkX())
            .prkY(lot.getPrkY())
            .currentInfoYn(lot.getCurrentInfoYn())
            .build();
  }
}

