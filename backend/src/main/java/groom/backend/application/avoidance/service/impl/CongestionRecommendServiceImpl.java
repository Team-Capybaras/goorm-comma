package groom.backend.application.avoidance.service.impl;

import groom.backend.application.avoidance.dto.response.CongestionPredResult;
import groom.backend.application.avoidance.dto.response.CongestionRecommendResult;
import groom.backend.application.avoidance.dto.response.CongestionStatistics;
import groom.backend.application.avoidance.model.spec.CongestionPredictModel;
import groom.backend.application.avoidance.service.spec.CongestionRecommendService;
import groom.backend.application.avoidance.service.spec.ParkStatisticsService;
import groom.backend.domain.avoidance.enums.Weekday;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

/**
 * 당일에 대한 혼잡도 예측 기반 시간대 추천 서비스 기능
 *
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CongestionRecommendServiceImpl implements CongestionRecommendService {
  private final ParkStatisticsService parkStatisticsService;
  private final CongestionPredictModel congestionPredictModel;


  /**
   * 특정 요일에 대해, 09-22 시간대 사이 기준 공원 혼잡도에서 가장 여유로울 것으로 예측되는 시간대 추천 및 텍스트 생성
   * 또한 예측 모델을 이용해 해당 요일의 혼잡도를 예측한다.
   * @return
   */
  @Override
  public CongestionRecommendResult recommend(String areaCode, Weekday weekday) {

    log.info(
            "[CongestionRecommend] recommend start. areaCode={}, weekday={}",
            areaCode, weekday
    );

    List<CongestionStatistics> filtered =
            parkStatisticsService.getCongestionStatistics(areaCode).stream()
                    .filter(s -> s.weekday() == weekday)
                    .toList();

    log.debug(
            "[CongestionRecommend] statistics filtered. areaCode={}, weekday={}, count={}",
            areaCode, weekday, filtered.size()
    );

    List<CongestionPredResult> predResults =
            congestionPredictModel.predictCongestion(filtered, areaCode);

    log.debug(
            "[CongestionRecommend] prediction completed. areaCode={}, weekday={}, predCount={}",
            areaCode, weekday, predResults.size()
    );

    int recommendedHour = predResults.stream()
            .filter(stat -> stat.getHour() >= 9 && stat.getHour() <= 22)
            .min(Comparator.comparingInt(CongestionPredResult::getPredCongestions))
            .map(CongestionPredResult::getHour)
            .orElse(0);

    if (recommendedHour == 0) {
      log.warn(
              "[CongestionRecommend] no valid recommendation found. areaCode={}, weekday={}",
              areaCode, weekday
      );
    } else {
      log.info(
              "[CongestionRecommend] recommendedHour determined. areaCode={}, weekday={}, hour={}",
              areaCode, weekday, recommendedHour
      );
    }

    String message = recommendedHour + "시가 가장 여유로울 것으로 예측되요.";
    if (recommendedHour == 0) message = "오늘은 사람이 붐빌수도 있어요";

    log.debug(
            "[CongestionRecommend] message generated. areaCode={}, weekday={}, message={}",
            areaCode, weekday, message
    );

    return CongestionRecommendResult.builder()
            .recommendedHour(recommendedHour)
            .message(message)
            .predCongestions(predResults)
            .build();
  }
}

