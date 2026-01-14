package groom.backend.application.avoidance.mapper;

import groom.backend.application.avoidance.dto.response.ParkStatisticsResponse;
import groom.backend.application.avoidance.dto.response.WeekdayAggregateResponse;
import groom.backend.application.avoidance.dto.response.WeekdayAggregateResponse.HourAggregateResponse;
import groom.backend.domain.avoidance.entity.ParkStatistics;
import groom.backend.domain.avoidance.enums.Weekday;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ParkStatisticsMapper {

  /**
   * ParkStatistics 엔티티 리스트를
   * ParkStatisticsResponse DTO로 변환한다.
   *
   * ParkStatistics가 빈 배열일 수 있기 때문에 areaCode를 입력으로 받는다.
   *
   * 책임:
   * - weekday 기준 그룹핑
   * - hour 리스트 구성
   * - 구조적 변환만 수행
   */
  public ParkStatisticsResponse toParkStatisticsResponse(
          String areaCode,
          LocalDateTime refreshTime,
          List<ParkStatistics> statisticsList
  ) {

    if (statisticsList == null || statisticsList.isEmpty()) {
      return ParkStatisticsResponse.builder()
              .areaCode(areaCode)
              .refreshTime(refreshTime)
              .weekdays(Collections.emptyList())
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
            .areaCode(areaCode)
            .refreshTime(refreshTime)
            .weekdays(weekdayAggregates)
            .build();
  }

  /**
   * 특정 요일에 대한 ParkStatistics 목록을
   * WeekdayAggregateResponse로 변환한다.
   *
   * 주의:
   * - today / recommendedVisitHour / uncrowdedHours 는
   *   Service 계층에서 채워 넣는 것을 전제로 한다.
   */
  private WeekdayAggregateResponse toWeekdayAggregateResponse(
          Weekday weekday,
          List<ParkStatistics> statisticsList
  ) {

    List<HourAggregateResponse> hourAggregates =
            statisticsList.stream()
                    .map(this::toHourAggregateResponse)
                    .sorted(Comparator.comparing(HourAggregateResponse::getHour))
                    .collect(Collectors.toList());

    return WeekdayAggregateResponse.builder()
            .weekday(weekday)
            .today(false) // Service에서 재설정
            .recommendedVisitHour(0) // Service에서 재설정
            .hours(hourAggregates)
            .build();
  }

  /**
   * ParkStatistics 엔티티를
   * HourAggregateResponse DTO로 변환한다.
   *
   * - past / now / future 중
   *   ParkStatistics는 "과거 통계" 역할만 수행
   */
  private HourAggregateResponse toHourAggregateResponse(
          ParkStatistics statistics
  ) {

    return HourAggregateResponse.builder()
            .hour(statistics.getHour())
            .past(statistics.getPopMeanMin())
            .now(null)
            .future(statistics.getPopMeanMax())
            .build();
  }
}