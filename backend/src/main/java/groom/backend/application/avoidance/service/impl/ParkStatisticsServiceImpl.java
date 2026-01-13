package groom.backend.application.avoidance.service.impl;

import groom.backend.application.avoidance.dto.response.ParkStatisticsResponse;
import groom.backend.application.avoidance.mapper.ParkStatisticsMapper;
import groom.backend.application.avoidance.service.spec.ParkStatisticsService;
import groom.backend.domain.population.entity.LivePopStatus;
import groom.backend.domain.population.repository.LivePopStatusRepository;
import groom.backend.domain.avoidance.entity.ParkStatistics;
import groom.backend.domain.avoidance.entity.ParkStatisticsLog;
import groom.backend.domain.avoidance.enums.Weekday;
import groom.backend.domain.avoidance.repository.ParkStatisticsLogRepository;
import groom.backend.domain.avoidance.repository.ParkStatisticsRepository;
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

  private final LivePopStatusRepository livePopStatusRepository;
  private final ParkStatisticsRepository parkStatisticsRepository;
  private final ParkStatisticsLogRepository parkStatisticsLogRepository;

  private final ParkStatisticsMapper parkStatisticsMapper;

  /**
   * 전체 인구 데이터를 기반으로 공원 혼잡도 통계를 집계한다.
   */
  @Transactional
  @CacheEvict(
          cacheNames = "parkStatistics",
          allEntries = true
  )
  public void aggregateAll() {
    log.info("공원 혼잡도 전체 집계 시작");

    List<LivePopStatus> allStatuses = livePopStatusRepository.findAll();

    if (allStatuses.isEmpty()) {
      log.warn("집계 대상 인구 데이터가 없습니다.");
      return;
    }

    Map<GroupKey, List<LivePopStatus>> grouped =
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
  public ParkStatisticsResponse getParkStatistics(String areaCode) {
    // TODO : 집계 데이터 반환
    List<ParkStatistics> statistics = parkStatisticsRepository.findByAreaCode(areaCode);

    ParkStatisticsResponse response = parkStatisticsMapper.toParkStatisticsResponse(statistics);

    return response;
  }


//  private int uncrowdedTime(ParkStatisticsResponse data) {
//    data.getStatistics();
//  }



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

    // 1. 집계 테이블 UPSERT
    ParkStatistics statistics =
            ParkStatistics.builder()
                    .areaCode(key.areaCode())
                    .weekday(key.weekday())
                    .hour(key.hour())
                    .popMeanMin(avgMin)
                    .popMeanMax(avgMax)
                    .build();

    parkStatisticsRepository.save(statistics);

    // 2. 로그 테이블 INSERT
    ParkStatisticsLog logEntity =
            ParkStatisticsLog.builder()
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
   * 방문 시간 기반 최소 혼잡도 제공 기능
   * 현재 시간을 받는다.
   * FE는 한꺼번에 받는 것이 좋다.
   * 시간 입력은 UTC냐 integer냐 -> 일단 YYYY.MM.DD hh:mm 형식으로...
   * ParkStatistics의 내부 기능이 될까 분리가 될까. FE 에 따른다.
   */
  private void temp() {

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
