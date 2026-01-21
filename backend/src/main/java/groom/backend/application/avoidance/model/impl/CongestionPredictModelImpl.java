package groom.backend.application.avoidance.model.impl;

import groom.backend.application.avoidance.dto.response.CongestionPredResult;
import groom.backend.application.avoidance.dto.response.CongestionStatistics;
import groom.backend.application.avoidance.model.context.PredictionFactors;
import groom.backend.application.avoidance.model.context.WeatherContext;
import groom.backend.application.avoidance.model.spec.CongestionPredictModel;
import groom.backend.application.avoidance.model.spec.adjust.ExternalFactorAdjuster;
import groom.backend.application.avoidance.model.spec.baseline.BaselineEstimator;
import groom.backend.application.avoidance.model.spec.post.PostProcessor;
import groom.backend.application.avoidance.model.spec.uncertainty.UncertaintyModel;
import groom.backend.application.avoidance.model.spec.provider.WeatherProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * 각 요일별 0시부터 23시까지의 유동인구 혼잡도를
 * 통계 기반 파이프라인을 통해 추정하는 예측 컴포넌트.
 * 과거 혼잡도와 일기예보를 통한 강수확률을 기반으로 휴리스틱한 혼잡도를 예측한다.
 *
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CongestionPredictModelImpl implements CongestionPredictModel {

  private final BaselineEstimator baselineEstimator;
  private final ExternalFactorAdjuster externalFactorAdjuster;
  private final UncertaintyModel uncertaintyModel;
  private final PostProcessor postProcessor;
  private final WeatherProvider weatherProvider;

  @Override
  public List<CongestionPredResult> predictCongestion(List<CongestionStatistics> congestionStatistics, String areaCode) {

    if (congestionStatistics == null || congestionStatistics.isEmpty()) {
      log.warn("[CongestionPredict] input statistics is empty");
      return List.of();
    }

    return congestionStatistics.stream()
            .map(stat -> predictOne(stat, congestionStatistics, areaCode))
            .toList();
  }

  private CongestionPredResult predictOne(
          CongestionStatistics stat,
          List<CongestionStatistics> all,
          String areaCode
  ) {

    PredictionFactors factors = buildFactors(stat, areaCode);

    double baseline = baselineEstimator.estimate(stat, all);
    double adjusted = externalFactorAdjuster.adjust(baseline, stat, factors);
    double finalValue = uncertaintyModel.apply(adjusted, stat, factors);

    int predictedCongestion =
            postProcessor.finalizeToCongestionUnit(finalValue, factors);

    log.info(
            "[CongestionPredict] result weekday={}, hour={}, predicted={}, weatherAvailable={}",
            stat.weekday(),
            stat.hour(),
            predictedCongestion,
            factors.isWeatherAvailable()
    );

    return CongestionPredResult.builder()
            .weekday(stat.weekday())
            .hour(stat.hour())
            .predCongestions(predictedCongestion)
            .build();
  }

  private PredictionFactors buildFactors(CongestionStatistics stat, String areaCode) {

    Optional<WeatherContext> weatherOpt =
            weatherProvider.getWeather(areaCode, stat.hour(), stat.weekday());

    return PredictionFactors.builder()
            .weatherAvailable(weatherOpt.isPresent())
            .weather(weatherOpt.orElse(null))
            .holiday(false)
            .eventNearby(false)
            .build();
  }
}

