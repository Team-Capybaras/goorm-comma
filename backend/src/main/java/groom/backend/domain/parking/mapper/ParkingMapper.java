package groom.backend.domain.parking.mapper;

import groom.backend.domain.parking.dto.response.ChargerStationSummaryResponse;
import groom.backend.domain.parking.dto.response.ParkingLotSummaryResponse;
import groom.backend.domain.parking.entity.ChargerStation;
import groom.backend.domain.parking.entity.ParkingLot;
import org.springframework.stereotype.Component;

@Component
public class ParkingMapper {

  public ChargerStationSummaryResponse toChargerStationSummary(ChargerStation station) {
    return ChargerStationSummaryResponse.builder()
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

  public ParkingLotSummaryResponse toParkingLotSummary(ParkingLot lot) {
    return ParkingLotSummaryResponse.builder()
            .prkCode(lot.getPrkCode())
            .prkName(lot.getPrkName())
            .prkType(lot.getPrkType())
            .capacity(lot.getCapacity())
            .payYn(lot.getPayYn())
            .addr(lot.getAddr())
            .roadAddr(lot.getRoadAddr())
            .prkX(lot.getPrkX())
            .prkY(lot.getPrkY())
            .build();
  }
}

