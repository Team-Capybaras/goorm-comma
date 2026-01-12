package groom.backend.application.avoidance.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        name = "ParkStatisticsResponse",
        description = """
                공원 혼잡도 통계 응답 DTO.
                요일 및 시간 단위의 혼잡도 집계 결과를 제공합니다.
                데이터가 충분하지 않을 경우 일부 요일은 존재하지 않을 수 있으며,
                빈 배열 또한 발생할 수 있습니다.
                """
)
public class ParkStatisticsResponse {

  /**
   * 요일별 단위시간 혼잡도 분석 데이터
   * 단위시간 : hour
   */
  @Schema(
          description = "요일 및 시간(hour) 단위로 집계된 혼잡도 통계 목록",
          example = """
                      statistics: [
                        {
                          weekday,
                          hour: [
                            { time, popMeanMin, popMeanMax, uncrowdedTime }
                          ],
                          message
                        }
                      ]
                  """
  )
  private List<WeekdayAggregateResponse> statistics;
}