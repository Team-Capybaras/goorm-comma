package groom.backend.application.avoidance.dto.response;

import groom.backend.domain.avoidance.enums.Weekday;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Schema(
        name = "WeekdayAggregateResponse",
        description = """
                특정 요일에 대한 시간대별 혼잡도 집계 정보입니다.
                
                - 요일별로 가장 여유로울 것으로 예측되는 시간대(recommendedVisitHour)를 제공합니다.
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
                  해당 요일 기준 추천 방문 시간입니다.
                  만약 오늘에 해당하는 요일일 경우, 09시에서 22시 사이의 시간대를 추천하며, 23시 이후부터는 다음날의 시간대를 추천합니다.
                  """,
          minimum = "9",
          maximum = "22",
          example = "14",
          nullable = false
  )
  private int recommendedVisitHour;

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
  @Setter
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
                  - 과거 데이터가 존재하지 않을 경우 null이 올 수 있습니다.
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

