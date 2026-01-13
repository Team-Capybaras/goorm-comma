package groom.backend.application.avoidance.dto.response;

import groom.backend.domain.avoidance.enums.Weekday;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Schema(
        name = "WeekdayAggregateResponse",
        description = """
                특정 요일에 대한 시간대별 혼잡도 집계 정보입니다.
                
                - 요일별로 가장 여유로운 혼잡 시간대(uncrowdedTime)를 제공합니다.
                - uncrowdedHours는 동일한 기준으로 계산된 후보 시간대 목록입니다.
                - today 여부에 따라 실시간(now) 데이터 포함 여부가 달라집니다.
                """
)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class WeekdayAggregateResponse {

  @Schema(
          description = "요일 구분 값입니다.",
          example = "MON",
          allowableValues = {
                  "MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"
          },
          nullable = false
  )
  private Weekday weekday;

  @Schema(
          description = """
                  요청 기준일이 해당 요일인지 여부입니다.
                  
                  - true 인 경우, hour.now 필드가 포함될 수 있습니다.
                  - false 인 경우, hour.now 는 항상 null 입니다.
                  """,
          example = "true",
          nullable = false
  )
  private boolean today;

  @Schema(
          description = """
                  해당 요일에서 가장 여유로운 혼잡대의 대표 시간입니다.
                  
                  - uncrowdedHours의 첫 번째 값과 동일합니다.
                  - 과거/예측 통계를 기반으로 계산됩니다.
                  """,
          example = "13",
          nullable = false
  )
  private int uncrowdedTime;

  @Schema(
          description = """
                  가장 여유로운 혼잡대 후보 시간 목록입니다.
                  
                  - uncrowdedTime을 포함합니다.
                  - 혼잡도가 낮은 순으로 정렬되어 있습니다.
                  - 정책에 따라 상위 N개만 포함됩니다.
                  """,
          example = "[13, 14]",
          nullable = false
  )
  private List<Integer> uncrowdedHours;

  @Schema(
          description = """
                  시간대별 상세 혼잡도 정보 목록입니다.
                  
                  - hour 값은 0~23 기준입니다.
                  - 데이터가 부족한 시간대는 제외될 수 있습니다.
                  """,
          nullable = false
  )
  private List<HourAggregateResponse> hours;


  @Schema(
          name = "HourAggregateResponse",
          description = """
                특정 요일의 특정 시간대에 대한 혼잡도 정보입니다.
                
                - past: 과거 통계 기반 평균 혼잡도
                - now: 실시간 혼잡도 (오늘 요일에만 제공)
                - future: 예측 혼잡도
                """
  )
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class HourAggregateResponse {

    @Schema(
            description = "시간 값 (24시간 기준)",
            example = "10",
            minimum = "0",
            maximum = "23",
            nullable = false
    )
    private int hour;

    @Schema(
            description = """
                  과거 통계 기반 평균 혼잡도 값입니다.
                  
                  - 해당 요일·시간대의 누적 통계 결과입니다.
                  - 항상 존재하지 않을 수 있습니다.
                  """,
            example = "120",
            nullable = true
    )
    private Integer past;

    @Schema(
            description = """
                  실시간 혼잡도 값입니다.
                  
                  - today == true 인 요일에만 제공됩니다.
                  - 오늘이 아닌 경우 항상 null 입니다.
                  """,
            example = "180",
            nullable = true
    )
    private Integer now;

    @Schema(
            description = """
                  예측 혼잡도 값입니다.
                  
                  - 미래 방문 추이를 기반으로 계산된 값입니다.
                  - 예측 데이터가 없는 경우 null 입니다.
                  """,
            example = "200",
            nullable = true
    )
    private Integer future;
  }
}

