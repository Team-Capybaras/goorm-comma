package groom.backend.application.avoidance.model.spec.uncertainty;

import groom.backend.application.avoidance.dto.response.CongestionStatistics;
import groom.backend.application.avoidance.model.context.PredictionFactors;

public interface UncertaintyModel {
  double apply(double value, CongestionStatistics stat, PredictionFactors factors);
}

