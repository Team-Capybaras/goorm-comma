package groom.backend.application.avoidance.dto.response;

import groom.backend.application.avoidance.enums.Weekday;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        name = "WeekdayAggregateResponse",
        description = "특정 요일과 시간(hour)에 대한 혼잡도 집계 데이터. 데이터가 부족할 경우 일부 시간대가 존재하지 않을 수 있습니다."
)
public class WeekdayAggregateResponse {

  /**
   * MON, TUE, WED..
   */
  @Schema(
          description = "요일",
          example = "MON",
          allowableValues = {
                  "MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"
          }
  )
  private Weekday weekday;

  /**
   * 0, 1, 2, 3, 4, 5, ... 23
   */
  @Schema(
          description = "시간 (24시간 기준)",
          example = "10",
          minimum = "0",
          maximum = "23"
  )
  private int time;

  /**
   * 인구 지표 최소값
   */
  @Schema(
          description = "해당 요일·시간대의 평균 인구 지표 최소값",
          example = "120"
  )
  private int popMeanMin;

  /**
   * 인구 지표 최대값
   */
  @Schema(
          description = "해당 요일·시간대의 평균 인구 지표 최대값",
          example = "340"
  )
  private int popMeanMax;
}
