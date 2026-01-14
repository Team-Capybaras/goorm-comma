package groom.backend.application.avoidance.service.impl;

import groom.backend.application.avoidance.dto.response.CongestionPredResult;
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
import java.util.List;

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
  @Override
  @Cacheable(
          cacheNames = "parkStatistics",
          key = "#areaCode"
  )
  public CongestionRecommendResponse getParkStatistics(String areaCode) {

    List<CongestionStatistics> stats = parkStatisticsService.getCongestionStatistics(areaCode);

    // 1. 기본 구조 매핑
    CongestionRecommendResponse response =
            congestionAvoidanceMapper.toCongestionRecommendResponse(
                    areaCode,
                    LocalDateTime.now(),
                    stats
            );

    // 2. today / uncrowdedTime / uncrowdedHours 후처리
    applyDerivedFields(response, areaCode);

    return response;
  }

  /**
   * 파생 필드 계산 (정책 영역)
   *
   * - today 판별
   * - uncrowdedHours / uncrowdedTime 계산
   * - recommendedVisitHour 계산
   *
   * ※ 현재는 TODO 형태로 두고 기본값만 세팅
   */
  private void applyDerivedFields(CongestionRecommendResponse response, String areaCode) {

    LocalDateTime now = LocalDateTime.now();
    Weekday todayWeekday = Weekday.from(now.getDayOfWeek());

    response.getWeekdays().forEach(weekdayAggregate -> {

      // today 여부 설정
      boolean isToday = weekdayAggregate.getWeekday() == todayWeekday;
      weekdayAggregate.setToday(isToday);

      // TODO: 혼잡도 기준 계산 로직
      // - past / now / future 우선순위 판단
      // - 혼잡도 오름차순 정렬
      // - 상위 N개 시간 추출
      // 특정 요일에 대한 혼잡도 회피 추천 시간 검색

      CongestionPredResult recommendResult = congestionRecommendService.recommend(areaCode, null);


      weekdayAggregate.setRecommendedVisitHour(recommendResult.getRecommendedHour());

      // TODO: today 기준 추천 방문 시간 계산
      // - now 존재 시 now 기준
      // - 없으면 future
      // - 그래도 없으면 uncrowdedTime

    });

  }

}
