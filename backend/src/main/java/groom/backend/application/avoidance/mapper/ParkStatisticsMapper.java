package groom.backend.application.avoidance.mapper;

import groom.backend.application.avoidance.dto.response.ParkStatisticsResponse;
import groom.backend.application.avoidance.dto.response.WeekdayAggregateResponse;
import groom.backend.application.avoidance.dto.response.WeekdayAggregateResponse.HourAggregateResponse;
import groom.backend.application.avoidance.entity.ParkStatistics;
import groom.backend.application.avoidance.enums.Weekday;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ParkStatisticsMapper {

  /**
   * ParkStatistics 엔티티 리스트를
   * ParkStatisticsResponse DTO로 변환한다.
   *
   * - weekday 기준으로 그룹핑
   * - 각 weekday 내부에 hour 리스트 구성
   * - statistics 필드는 항상 non-null
   */
  public ParkStatisticsResponse toParkStatisticsResponse(
          List<ParkStatistics> statisticsList
  ) {

    if (statisticsList == null || statisticsList.isEmpty()) {
      return ParkStatisticsResponse.builder()
              .statistics(Collections.emptyList())
              .build();
    }

    // 1. weekday 기준 그룹핑
    Map<Weekday, List<ParkStatistics>> groupedByWeekday =
            statisticsList.stream()
                    .collect(Collectors.groupingBy(ParkStatistics::getWeekday));

    // 2. WeekdayAggregateResponse 생성
    List<WeekdayAggregateResponse> weekdayAggregates =
            groupedByWeekday.entrySet().stream()
                    .map(entry -> toWeekdayAggregateResponse(entry.getKey(), entry.getValue()))
                    .sorted(Comparator.comparing(WeekdayAggregateResponse::getWeekday))
                    .collect(Collectors.toList());

    return ParkStatisticsResponse.builder()
            .statistics(weekdayAggregates)
            .build();
  }

  /**
   * 특정 요일에 대한 ParkStatistics 목록을
   * WeekdayAggregateResponse로 변환한다.
   * TODO : uncrowdedTime 계산 및 message 작성
   */
  private WeekdayAggregateResponse toWeekdayAggregateResponse(
          Weekday weekday,
          List<ParkStatistics> statisticsList
  ) {

    List<HourAggregateResponse> hourAggregates =
            statisticsList.stream()
                    .map(this::toHourAggregateResponse)
                    .sorted(Comparator.comparing(HourAggregateResponse::getTime))
                    .collect(Collectors.toList());

    return WeekdayAggregateResponse.builder()
            .weekday(weekday)
            .hour(hourAggregates)
            .message(null)
            .uncrowdedTime(0)
            .build();
  }

  /**
   * ParkStatistics 엔티티를
   * HourAggregateResponse DTO로 변환한다.
   */
  private HourAggregateResponse toHourAggregateResponse(
          ParkStatistics statistics
  ) {
    return new HourAggregateResponse(
            statistics.getHour(),
            statistics.getPopMeanMin(),
            statistics.getPopMeanMax()
    );
  }
}
