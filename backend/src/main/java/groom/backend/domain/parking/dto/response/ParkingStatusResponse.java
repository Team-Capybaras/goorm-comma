package groom.backend.domain.parking.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Schema(
        name = "ParkingLocationResponse",
        description = "주차장 및 전기차 충전소 위치 정보 응답 DTO"
)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParkingStatusResponse {

  @Schema(
          description = "전기차 충전소 요약 정보 목록"
  )
  private List<ChargerStationResponse> chargerStations;

  @Schema(
          description = "주차장 요약 정보 목록"
  )
  private List<ParkingLotResponse> parkingLots;
}