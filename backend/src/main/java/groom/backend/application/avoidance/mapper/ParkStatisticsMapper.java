package groom.backend.application.avoidance.mapper;

import groom.backend.application.avoidance.dto.response.ParkStatisticsResponse;
import groom.backend.application.avoidance.dto.response.WeekdayAggregateResponse;
import groom.backend.application.avoidance.entity.ParkStatistics;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ParkStatisticsMapper {

  /**
   * ParkStatistics 엔티티 리스트를
   * ParkStatisticsResponse DTO로 변환한다.
   *
   * @param statisticsList 집계 통계 엔티티 목록
   * @return 공원 혼잡도 통계 응답 DTO (statistics는 null 아님)
   */
  public ParkStatisticsResponse toParkStatisticsResponse(
          List<ParkStatistics> statisticsList
  ) {

    if (statisticsList == null || statisticsList.isEmpty()) {
      return ParkStatisticsResponse.builder()
              .statistics(Collections.emptyList())
              .build();
    }

    List<WeekdayAggregateResponse> aggregates =
            statisticsList.stream()
                    .map(this::toWeekdayAggregateResponse)
                    .collect(Collectors.toList());

    return ParkStatisticsResponse.builder()
            .statistics(aggregates)
            .build();
  }

  /**
   * ParkStatistics 엔티티를
   * WeekdayAggregateResponse DTO로 변환한다.
   */
  private WeekdayAggregateResponse toWeekdayAggregateResponse(
          ParkStatistics statistics
  ) {
    return WeekdayAggregateResponse.builder()
            .weekday(statistics.getWeekday())
            .time(statistics.getHour())
            .popMeanMin(statistics.getPopMeanMin())
            .popMeanMax(statistics.getPopMeanMax())
            .build();
  }
}

