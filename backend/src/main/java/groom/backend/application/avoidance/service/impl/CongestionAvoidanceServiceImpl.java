package groom.backend.application.avoidance.service.impl;

import groom.backend.application.avoidance.dto.response.CongestionPredResult;
import groom.backend.application.avoidance.dto.response.CongestionRecommendResult;
import groom.backend.application.avoidance.dto.response.CongestionRecommendResponse;
import groom.backend.application.avoidance.dto.response.CongestionStatistics;
import groom.backend.application.avoidance.mapper.CongestionAvoidanceMapper;
import groom.backend.application.avoidance.service.spec.CongestionRecommendService;
import groom.backend.application.avoidance.service.spec.CongestionAvoidanceService;
import groom.backend.application.avoidance.service.spec.ParkStatisticsService;
import groom.backend.domain.avoidance.enums.Weekday;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CongestionAvoidanceServiceImpl implements CongestionAvoidanceService {
  private final ParkStatisticsService parkStatisticsService;

  private final CongestionAvoidanceMapper congestionAvoidanceMapper;

  private final CongestionRecommendService congestionRecommendService;


  /**
   * 공원 혼잡도 통계 조회
   */
  // TODO : 각 요일별 캐싱 및 today에 대한 캐시만 갱신하도록 변경. 매일 00시 집계시 전체 캐시 변경
  @Override
  @Cacheable(
          cacheNames = "parkStatistics",
          key = "#areaCode + ':' + T(java.time.LocalDateTime).now().getHour()", // 1시간 단위로 캐시키 구분
          cacheManager = "avoidanceCacheManager"
  )
  public CongestionRecommendResponse getParkStatistics(String areaCode) {

    log.info("[CongestionAvoidance] getParkStatistics start. areaCode={}", areaCode);

    List<CongestionStatistics> stats = parkStatisticsService.getCongestionStatistics(areaCode);

    log.debug("[CongestionAvoidance] statistics fetched. areaCode={}, count={}",
            areaCode, stats.size());

    // 1. 기본 구조 매핑
    CongestionRecommendResponse response =
            congestionAvoidanceMapper.toCongestionRecommendResponse(
                    areaCode,
                    LocalDateTime.now(),
                    stats
            );

    log.debug("[CongestionAvoidance] base response mapped. areaCode={}, weekdayCount={}",
            areaCode, response.getWeekdays().size());


    // 2. today / uncrowdedTime / uncrowdedHours 후처리
    applyDerivedFields(response, areaCode);

    log.debug("[CongestionAvoidance] base response mapped. areaCode={}, weekdayCount={}",
            areaCode, response.getWeekdays().size());


    return response;
  }

  /**
   * 파생 필드 계산 (정책 영역)
   *
   * - today 판별
   * - uncrowdedHours / uncrowdedTime 계산
   * - recommendedVisitHour 계산
   *
   */
  private void applyDerivedFields(CongestionRecommendResponse response, String areaCode) {

    LocalDateTime now = LocalDateTime.now();
    Weekday todayWeekday = Weekday.from(now.getDayOfWeek());

    log.debug("[CongestionAvoidance] applyDerivedFields start. areaCode={}, today={}, hour={}",
            areaCode, todayWeekday, now.getHour());

    // 오늘 기준 시간대별 실시간 통계 조회 (1회)
    List<CongestionStatistics> todayStats = parkStatisticsService.getTodayCongestion(areaCode);

    Map<Integer, CongestionStatistics> todayStatMap =
            todayStats.stream()
                    .collect(Collectors.toMap(
                            CongestionStatistics::hour,
                            stat -> stat
                    ));

    response.getWeekdays().forEach(weekdayAggregate -> {

      boolean isToday =
              weekdayAggregate.getWeekday() == todayWeekday && now.getHour() < 23;
      weekdayAggregate.setToday(isToday);

      log.trace("[CongestionAvoidance] weekday processing. areaCode={}, weekday={}, isToday={}",
              areaCode, weekdayAggregate.getWeekday(), isToday);

      CongestionRecommendResult recommendResult =
              congestionRecommendService.recommend(areaCode, weekdayAggregate.getWeekday());

      int recommendedHour = recommendResult.getRecommendedHour();

      if (isToday) {
        List<CongestionPredResult> predResults =
                recommendResult.getPredCongestions();

        int currentHour = now.getHour();

        recommendedHour = predResults.stream()
                .filter(stat ->
                        stat.getHour() >= Math.max(currentHour, 9)
                                && stat.getHour() <= 22)
                .min(Comparator.comparingInt(
                        CongestionPredResult::getPredCongestions))
                .map(CongestionPredResult::getHour)
                .orElse(0);

        log.debug(
                "[CongestionAvoidance] today recommendation calculated. areaCode={}, weekday={}, currentHour={}, recommendedHour={}",
                areaCode,
                weekdayAggregate.getWeekday(),
                currentHour,
                recommendedHour
        );
      }

      weekdayAggregate.setRecommendedVisitHour(recommendedHour);

      // ===== today 인 경우 hour.now 채움 =====
      if (isToday && weekdayAggregate.getHours() != null) {

        weekdayAggregate.getHours().forEach(hourAggregate -> {

          CongestionStatistics stat =
                  todayStatMap.get(hourAggregate.getHour());

          if (stat != null) {
            hourAggregate.setNow(stat.popMeanMin());
          }
        });

        log.debug(
                "[CongestionAvoidance] today hourly now-field populated. areaCode={}, weekday={}",
                areaCode, weekdayAggregate.getWeekday()
        );
      }
    });

    log.debug(
            "[CongestionAvoidance] applyDerivedFields end. areaCode={}",
            areaCode
    );
  }


}
