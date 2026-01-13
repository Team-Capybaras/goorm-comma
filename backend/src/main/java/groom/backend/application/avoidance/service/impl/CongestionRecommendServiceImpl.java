package groom.backend.application.avoidance.service.impl;

import groom.backend.application.avoidance.service.spec.CongestionRecommendService;
import groom.backend.domain.avoidance.entity.ParkStatistics;
import groom.backend.domain.avoidance.enums.Weekday;
import groom.backend.domain.avoidance.repository.ParkStatisticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CongestionRecommendServiceImpl implements CongestionRecommendService {
  private final ParkStatisticsRepository parkStatisticsRepository;


  /**
   * 09-22 시간대 사이 기준 공원 혼잡도에서 가장 여유로운 시간대 추출
   * @return
   */
  @Override
  public Integer congestionRecommend(String areaCode) {
    // 현재 서버 시각 기준 요일 산출
    LocalDateTime now = LocalDateTime.now();
    Weekday currentWeekday = localDateTimetoWeekday(now);

    // 23시 이후면 다음 날 기준으로 판단
    if (now.getHour() >= 23) {
      now = now.plusDays(1);
    }

    // 데이터 조회
    List<ParkStatistics> statistics =
            parkStatisticsRepository.findByAreaCode(areaCode);

    // 현재 요일 + 추천 가능 시간대(9~22) 필터링 후
    // 혼잡도(popMeanMax)가 가장 낮은 시간 반환
    return statistics.stream()
            .filter(stat -> stat.getWeekday() == currentWeekday)
            .filter(stat -> stat.getHour() >= 9 && stat.getHour() <= 22)
            .min((a, b) -> Integer.compare(a.getPopMeanMax(), b.getPopMeanMax()))
            .map(ParkStatistics::getHour)
            .orElse(0); // 데이터 없을 경우 기본값
  }

  /**
   * localDateTime을 WeekDay enum으로 변환
   * @param localDateTime
   * @return
   */
  private Weekday localDateTimetoWeekday(LocalDateTime localDateTime) {
    return switch (localDateTime.getDayOfWeek()) {
      case MONDAY -> Weekday.MON;
      case TUESDAY -> Weekday.TUE;
      case WEDNESDAY -> Weekday.WED;
      case THURSDAY -> Weekday.THU;
      case FRIDAY -> Weekday.FRI;
      case SATURDAY -> Weekday.SAT;
      case SUNDAY -> Weekday.SUN;
    };
  }


  // TODO : 24개 숫자를 통해 시간별 혼잡도 예측
  // 구체적 로직 구현 필요
  public List<Integer> predCongestion(String areaCode) {

    return List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
  }
}
