package groom.backend.domain.parking.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@Schema(
        name = "ChargerStationSummaryResponse",
        description = "전기차 충전소 위치 및 정적 요약 정보 DTO"
)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChargerStationSummaryResponse {

  @Schema(description = "충전소 ID", example = "PI000740")
  private String stationId;

  @Schema(description = "충전소명", example = "서울시청 충전소")
  private String stationName;

  @Schema(description = "충전소 주소", example = "서울특별시 중구 세종대로 110")
  private String stationAddr;

  @Schema(description = "경도", example = "126.9784000000")
  private BigDecimal stationX;

  @Schema(description = "위도", example = "37.5665000000")
  private BigDecimal stationY;

  @Schema(description = "이용 가능 시간, 24시간 이용가능 또는 주중/주말 : 05시~23시 등 입력 형식이 다양하게 올 수 있음.", example = "평일 10:00-17:00/토,휴일 10:00-20:00")
  private String stationUsetime;

  @Schema(description = "주차 요금 여부", example = "false")
  private Boolean stationParkpay;

  @Schema(description = "충전소 종류 상세", example = "공영주차장")
  private String stationKindDetail;

  // TODO : 현재 station limit detail에 대해 blank로 저장중. null 형식으로 데이터 보내기
  @Schema(description = "이용 제한 상세. 제한이 없을 경우 null 반환", example = "시설 상황에 따라 이용이 제한될 수 있음")
  private String stationLimitDetail;
}