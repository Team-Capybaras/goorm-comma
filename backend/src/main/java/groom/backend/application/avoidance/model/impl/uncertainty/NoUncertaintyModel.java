package groom.backend.application.avoidance.model.impl.uncertainty;

import groom.backend.application.avoidance.dto.response.CongestionStatistics;
import groom.backend.application.avoidance.model.context.PredictionFactors;
import groom.backend.application.avoidance.model.spec.uncertainty.UncertaintyModel;
import org.springframework.stereotype.Component;

@Component
public class NoUncertaintyModel implements UncertaintyModel {

  @Override
  public double apply(double value, CongestionStatistics stat, PredictionFactors factors) {
    return value;
  }
}
