package groom.backend.application.avoidance.scheduler;

import groom.backend.application.avoidance.service.spec.ParkStatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 인구 혼잡도 통계 집계 스케줄러
 * 매일 00시 업데이트 (전체 데이터)
 * TODO: MVP 개발 완료 후 업데이트 주기를 늘릴 필요 존재
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ParkStatisticsScheduler {
  private final ParkStatisticsService parkStatisticsService;

  @EventListener(ApplicationReadyEvent.class)
  public void onApplicationReady() {
    log.info("=== 애플리케이션 시작 시 혼잡도 데이터 집계 시작 ===");

    aggregate();
  }


  /**
   * 매일 00시마다 전체 집계
   */
  @Scheduled(cron = "0 0 0 * * *")
  public void aggregate() {
    log.info("=== 공원별 요일에 대한 시간별 혼잡도 집계 시작 ===");

    parkStatisticsService.aggregateAll();
  }
}
