package groom.backend.application.avoidance.mapper;

import groom.backend.application.avoidance.dto.response.CongestionRecommendResponse;
import groom.backend.application.avoidance.dto.response.CongestionStatistics;
import groom.backend.application.avoidance.dto.response.WeekdayAggregateResponse;
import groom.backend.application.avoidance.dto.response.WeekdayAggregateResponse.HourAggregateResponse;
import groom.backend.domain.avoidance.entity.ParkStatistics;
import groom.backend.domain.avoidance.enums.Weekday;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class CongestionAvoidanceMapper {

  /**
   * CongestionStatistics 와 갱신시간, areaCode를
   * ParkStatisticsResponse DTO로 변환한다.
   *
   * CongestionStatistics가 빈 배열일 수 있기 때문에 areaCode를 입력으로 받는다.
   *
   * 책임:
   * - weekday 기준 그룹핑
   * - hour 리스트 구성
   * - 구조적 변환만 수행
   */
  public CongestionRecommendResponse toCongestionRecommendResponse(
          String areaCode,
          LocalDateTime refreshTime,
          List<CongestionStatistics> statisticsList
  ) {

    if (statisticsList == null || statisticsList.isEmpty()) {
      return CongestionRecommendResponse.builder()
              .areaCode(areaCode)
              .refreshTime(refreshTime)
              .weekdays(Collections.emptyList())
              .build();
    }

    // 1. weekday 기준 그룹핑
    Map<Weekday, List<CongestionStatistics>> groupedByWeekday =
            statisticsList.stream()
                    .collect(Collectors.groupingBy(CongestionStatistics::weekday));

    // 2. WeekdayAggregateResponse 생성
    List<WeekdayAggregateResponse> weekdayAggregates =
            groupedByWeekday.entrySet().stream()
                    .map(entry -> toWeekdayAggregateResponse(entry.getKey(), entry.getValue()))
                    .sorted(Comparator.comparing(WeekdayAggregateResponse::getWeekday))
                    .collect(Collectors.toList());

    return CongestionRecommendResponse.builder()
            .areaCode(areaCode)
            .refreshTime(refreshTime)
            .weekdays(weekdayAggregates)
            .build();
  }

  /**
   * 특정 요일에 대한 CongestionStatistics 목록을
   * WeekdayAggregateResponse로 변환한다.
   *
   * 주의:
   * - today / recommendedVisitHour / uncrowdedHours 는
   *   Service 계층에서 채워 넣는 것을 전제로 한다.
   */
  private WeekdayAggregateResponse toWeekdayAggregateResponse(
          Weekday weekday,
          List<CongestionStatistics> statisticsList
  ) {

    List<HourAggregateResponse> hourAggregates =
            statisticsList.stream()
                    .map(this::toHourAggregateResponse)
                    .sorted(Comparator.comparing(HourAggregateResponse::getHour))
                    .collect(Collectors.toList());

    return WeekdayAggregateResponse.builder()
            .weekday(weekday)
            .today(false)               // Service에서 재설정
            .recommendedVisitHour(0)    // Service에서 재설정
            .hours(hourAggregates)
            .build();
  }

  /**
   * CongestionStatistics DTO를
   * HourAggregateResponse DTO로 변환한다.
   */
  private HourAggregateResponse toHourAggregateResponse(
          CongestionStatistics statistics
  ) {

    return HourAggregateResponse.builder()
            .hour(statistics.hour())
            .past(statistics.popMeanMin())
            .now(null)
            .future(statistics.popMeanMax())
            .build();
  }
}