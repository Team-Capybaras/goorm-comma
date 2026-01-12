package groom.backend.domain.parking.mapper;

import groom.backend.domain.parking.dto.response.ChargerDetailResponse;
import groom.backend.domain.parking.dto.response.ChargerStationResponse;
import groom.backend.domain.parking.entity.ChargerDetail;
import groom.backend.domain.parking.entity.ChargerStation;
import groom.backend.domain.parking.entity.ChargerStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ChargerMapper {

  /**
   * ChargerDetail + ChargerStatus -> ChargerDetailResponse
   */
  public ChargerDetailResponse toChargerDetailResponse(
          ChargerDetail detail,
          ChargerStatus status
  ) {
    return ChargerDetailResponse.builder()
            .chargerId(detail.getChargerId())
            .chargerType(detail.getChargerType())
            .chargerUpdated(detail.getChargerUpdated())
            .output(detail.getOutput())
            .method(detail.getMethod())
            .chargerStatus(status != null ? status.getChargerStatus() : null)
            .statusUpdated(status != null ? status.getStatusUpdated() : null)
            .build();
  }

  /**
   * ChargerStation + ChargerDetail 목록 + (chargerId 기준 최신 ChargerStatus Map)
   */
  public ChargerStationResponse toChargerStationResponse(
          ChargerStation station,
          List<ChargerDetail> chargerDetails,
          Map<Integer, ChargerStatus> latestStatusByChargerId
  ) {
    List<ChargerDetailResponse> detailResponses =
            chargerDetails.stream()
                    .map(detail ->
                            toChargerDetailResponse(
                                    detail,
                                    latestStatusByChargerId.get(detail.getChargerId())
                            )
                    )
                    .collect(Collectors.toList());

    return ChargerStationResponse.builder()
            .stationId(station.getStationId())
            .stationName(station.getStationName())
            .stationAddr(station.getStationAddr())
            .stationX(station.getStationX())
            .stationY(station.getStationY())
            .stationUsetime(station.getStationUsetime())
            .stationParkpay(station.getStationParkpay())
            .stationKindDetail(station.getStationKindDetail())
            .stationLimitDetail(
                    station.getStationLimitDetail() == null || station.getStationLimitDetail().isBlank()
                            ? null
                            : station.getStationLimitDetail()
            )
            .chargerDetails(detailResponses)
            .build();
  }

  /**
   * ChargerStatus List -> (chargerId 기준 최신 상태 Map)
   * Mapper 보조 메서드 (서비스 로직 단순화용)
   */
  public Map<Integer, ChargerStatus> toLatestStatusMap(
          List<ChargerStatus> statuses
  ) {
    return statuses.stream()
            .collect(Collectors.toMap(
                    ChargerStatus::getChargerId,
                    s -> s
            ));
  }
}
