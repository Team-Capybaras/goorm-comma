package groom.backend.application.avoidance.service.impl;

import groom.backend.application.avoidance.dto.response.CongestionStatistics;
import groom.backend.application.avoidance.service.spec.ParkStatisticsService;
import groom.backend.domain.avoidance.entity.ParkStatistics;
import groom.backend.domain.avoidance.entity.ParkStatisticsLog;
import groom.backend.domain.avoidance.enums.Weekday;
import groom.backend.domain.avoidance.repository.ParkStatisticsLogRepository;
import groom.backend.domain.avoidance.repository.ParkStatisticsRepository;
import groom.backend.domain.park.repository.ParkRepository;
import groom.backend.domain.population.entity.LivePopStatus;
import groom.backend.domain.population.repository.LivePopStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParkStatisticsServiceImpl implements ParkStatisticsService {
  private final ParkStatisticsRepository parkStatisticsRepository;
  private final ParkStatisticsLogRepository parkStatisticsLogRepository;

  private final LivePopStatusRepository livePopStatusRepository;

  private final ParkRepository parkRepository;

  /**
   * 전체 인구 데이터를 기반으로 공원 혼잡도 통계를 집계한다.
   * 집계가 수행되면 모든 캐시는 무효화된다.
   */
  @Transactional
  @CacheEvict(
          cacheNames = "parkStatistics",
          allEntries = true
  )
  @Override
  public void aggregateAll() {

    log.info("공원 혼잡도 전체 집계 시작");

    // TODO: 추후 UPSERT로 리팩토링
    log.info("기존 집계 데이터 제거");
    parkStatisticsRepository.deleteAll();

    List<LivePopStatus> allStatuses = livePopStatusRepository.findAll();

    if (allStatuses.isEmpty()) {
      log.warn("집계 대상 인구 데이터가 없습니다.");
      return;
    }

    Map<ParkStatisticsServiceImpl.GroupKey, List<LivePopStatus>> grouped =
            allStatuses.stream()
                    .collect(Collectors.groupingBy(this::groupKey));

    grouped.forEach(this::aggregateOne);

    log.info("공원 혼잡도 전체 집계 완료");
  }

  @Override
  @Cacheable(
          cacheNames = "parkStatistics",
          key = "#areaCode"
  )
  public List<CongestionStatistics> getCongestionStatistics(String areaCode) {

    List<ParkStatistics> statistics =
            parkStatisticsRepository.findByAreaCode(areaCode);

    return statistics.stream()
            .map(stat -> new CongestionStatistics(
                    stat.getWeekday(),
                    stat.getHour(),
                    stat.getPopMeanMin(),
                    stat.getPopMeanMax()
            ))
            .toList();
  }

  /**
   * 단일 그룹 집계 처리
   */
  private void aggregateOne(GroupKey key, List<LivePopStatus> statuses) {

    int avgMin =
            (int) statuses.stream()
                    .mapToInt(LivePopStatus::getAreaPopMin)
                    .average()
                    .orElse(0);

    int avgMax =
            (int) statuses.stream()
                    .mapToInt(LivePopStatus::getAreaPopMax)
                    .average()
                    .orElse(0);

    // UPSERT 연산은 JPA에서 지원하지 않는다. 개별 update만 지원
    // 1. 집계 테이블 저장
    ParkStatistics statistics =
            ParkStatistics.builder()
                    .areaCode(key.areaCode())
                    .weekday(key.weekday())
                    .hour(key.hour())
                    .popMeanMin(avgMin)
                    .popMeanMax(avgMax)
                    .build();

    parkStatisticsRepository.save(statistics);

    // 2. 로그 테이블 저장
    ParkStatisticsLog logEntity =
            ParkStatisticsLog.builder()
                    .park(parkRepository.findByAreaCode(key.areaCode()).orElse(null))
                    .weekday(key.weekday())
                    .hour(key.hour())
                    .popMeanMin(avgMin)
                    .popMeanMax(avgMax)
                    .startTime(minTime(statuses))
                    .endTime(maxTime(statuses))
                    .build();

    parkStatisticsLogRepository.save(logEntity);

    log.debug(
            "집계 완료 - areaCode={}, weekday={}, hour={}, min={}, max={}",
            key.areaCode(), key.weekday(), key.hour(), avgMin, avgMax
    );
  }

  /**
   * 집계 키 생성
   */
  private GroupKey groupKey(LivePopStatus status) {

    LocalDateTime time = status.getDataGetTime();

    return new GroupKey(
            status.getAreaCode(),
            Weekday.from(time.getDayOfWeek()),
            time.getHour()
    );
  }

  private LocalDateTime minTime(List<LivePopStatus> statuses) {
    return statuses.stream()
            .map(LivePopStatus::getDataGetTime)
            .min(LocalDateTime::compareTo)
            .orElse(null);
  }

  private LocalDateTime maxTime(List<LivePopStatus> statuses) {
    return statuses.stream()
            .map(LivePopStatus::getDataGetTime)
            .max(LocalDateTime::compareTo)
            .orElse(null);
  }

  /**
   * 집계용 내부 키
   */
  private record GroupKey(
          String areaCode,
          Weekday weekday,
          int hour
  ) {}
}
