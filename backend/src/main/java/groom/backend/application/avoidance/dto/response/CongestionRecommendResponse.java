package groom.backend.application.avoidance.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Schema(
        name = "ParkStatisticsResponse",
        description = """
                특정 공원(지역)에 대한 요일·시간대별 혼잡도 통계 응답입니다.
                
                - 과거 통계(past), 실시간(now), 예측(future) 혼잡도를 모두 포함합니다.
                - 요일별로 추천 방문 시간(recommendedVisitHour)을 제공합니다.
                """
)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class CongestionRecommendResponse {

  @Schema(
          description = """
                  공원 또는 지역을 식별하는 코드입니다.
                  
                  - 요청 path 또는 query에서 사용된 areaCode와 동일합니다.
                  - 응답의 자기 식별성을 보장하기 위한 메타데이터입니다.
                  """,
          example = "POI085",
          nullable = false
  )
  private String areaCode;

  @Schema(
          description = """
                  본 응답이 생성된 기준 시각입니다.
                  
                  - 클라이언트는 이 값을 기준으로 데이터 신선도를 판단할 수 있습니다.
                  """,
          example = "2026-01-13T13:30:00",
          nullable = false
  )
  private LocalDateTime refreshTime;


  @Schema(
          description = """
                  요일별 혼잡도 집계 목록입니다.
                  
                  - MON ~ SUN 전체가 항상 존재하지 않을 수 있습니다.
                  - 데이터가 부족한 요일은 제외될 수 있습니다.
                  """,
          nullable = false
  )
  private List<WeekdayAggregateResponse> weekdays;
}