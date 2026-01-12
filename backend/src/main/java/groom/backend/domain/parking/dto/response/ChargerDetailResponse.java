package groom.backend.domain.parking.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(
        name = "ChargerDetailResponse",
        description = "전기차 충전기 상세 정보 및 현재 상태 응답 DTO"
)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChargerDetailResponse {

  @Schema(
          description = "충전기 ID",
          example = "1"
  )
  private Integer chargerId;

  @Schema(
          description = "충전기 유형 (AC완속, DC콤보, DC차데모+AC3상+DC콤보 등)",
          example = "DC콤보"
  )
  private String chargerType;

  @Schema(
          description = "충전기 정보 업데이트 시각",
          example = "2026-01-06T08:30:00"
  )
  private LocalDateTime chargerUpdated;

  @Schema(
          description = "충전기 출력 (kW)",
          example = "50"
  )
  private Integer output;

  @Schema(
          description = "충전 방식 (단독, 동시 등, 값이 없을 수 있음)",
          example = "단독",
          nullable = true
  )
  private String method;

  @Schema(
          description = "충전기 현재 상태 (사용가능, 사용중, 상태미확인, 통신이상 등)",
          example = "사용가능"
  )
  private String chargerStatus;

  @Schema(
          description = "상태 업데이트 시각 (마지막 충전 또는 상태 변경 시각)",
          example = "2026-01-06T09:00:00"
  )
  private LocalDateTime statusUpdated;
}
