package groom.backend.domain.parking.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(
        name = "ParkingLotSummaryResponse",
        description = "주차장 위치 및 정적 요약 정보 DTO"
)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParkingLotResponse {

  @Schema(description = "주차장 코드", example = "3206241")
  private Long prkCode;

  @Schema(description = "주차장명", example = "국립중앙박물관")
  private String prkName;

  @Schema(description = "주차장 유형", example = "NW")
  private String prkType;

  @Schema(description = "총 수용 가능 대수", example = "120")
  private Integer capacity;

  @Schema(description = "실시간 주차 현황 제공 여부", example = "false")
  private Boolean currentInfoYn;

  @Schema(description = "현재 주차 대수, 실시간 주차 현황 미제공 시 null",
          nullable = true,
          example = "78")
  private Integer currentPrkCnt;

  //  갱신 시간 불안정함.
  @Schema(description = "데이터 업데이트 시간, 실시간 주차 현황 미제공 시 null",
          nullable = true,
          example = "2026-01-06T09:00:00")
  private LocalDateTime currentPrkTime;

  @Schema(description = "유료 여부", example = "true")
  private Boolean payYn;

  @Schema(description = "지번 주소", example = "서울특별시 중구 태평로1가")
  private String addr;

  @Schema(description = "도로명 주소", example = "서울특별시 중구 세종대로 110")
  private String roadAddr;

  @Schema(description = "경도", example = "126.9779000000")
  private BigDecimal prkX;

  @Schema(description = "위도", example = "37.5659000000")
  private BigDecimal prkY;
}