package groom.backend.application.avoidance.dto.response;

import groom.backend.application.avoidance.enums.Weekday;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        name = "WeekdayAggregateResponse",
        description = "특정 요일과 시간(hour)에 대한 혼잡도 집계 데이터. 데이터가 부족할 경우 부분적으로 시간대가 존재하지 않을 수 있습니다.",
        example = """
                weekday,
                hour: [
                  { time, popMeanMin, popMeanMax},
                  { time, popMeanMin, popMeanMax},
                  { time, popMeanMin, popMeanMax},
                  ...
                ],
                ,uncrowdedTime
                message
                """
)
public class WeekdayAggregateResponse {

  /**
   * MON, TUE, WED..
   */
  @Schema(
          description = "요일에 대한 구분입니다.",
          example = "MON",
          allowableValues = {
                  "MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"
          }
  )
  private Weekday weekday;

  @Schema(
          name = "hourAggregateResponse",
          description = "시간대에 대한 구분입니다."
  )
  private List<HourAggregateResponse> hour;

  /**
   * 09시 - 22시 중 가장 혼잡도가 낮은 시간대
   */
  @Schema(
          description = "9시 - 22시 중 가장 혼잡도가 낮은 시간대",
          example = "9",
          nullable = false
  )
  private int uncrowdedTime;

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  @Schema(
          name = "hourAggregateResponse",
          description = "특정 요일의 시간대에 대한 혼잡도 집계입니다."
  )
  public static class HourAggregateResponse {


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

  /**
   * 시간대에 대한 추천 메시지
   * ex: 오늘은 몇시가 제일 낮은 혼잡도를 가집니다.
   */
  @Schema(
          description = "시간대에 대한 추천 메시지",
          example = "오늘은 13시가 제일 낮은 혼잡도를 가집니다.",
          nullable = false
  )
  private String message;
}
