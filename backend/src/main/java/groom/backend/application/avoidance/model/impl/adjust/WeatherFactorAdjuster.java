package groom.backend.application.avoidance.model.impl.adjust;

import groom.backend.application.avoidance.dto.response.CongestionStatistics;
import groom.backend.application.avoidance.model.context.PredictionFactors;
import groom.backend.application.avoidance.model.spec.adjust.ExternalFactorAdjuster;
import org.springframework.stereotype.Component;

/**
 * 날씨 보정
 */

@Component
public class WeatherFactorAdjuster implements ExternalFactorAdjuster {

  @Override
  public double adjust(double baseline, CongestionStatistics stat, PredictionFactors factors) {

    if (!factors.isWeatherAvailable() || factors.getWeather() == null) {
      return baseline;
    }

    int rainProbability = factors.getWeather().getRainProbability();

    if (rainProbability > 50) {
      int excess = rainProbability - 50;
      double decreaseRate = excess * 0.005; // 0.5%
      return baseline * (1 - decreaseRate);
    }

    return baseline;
  }
}
