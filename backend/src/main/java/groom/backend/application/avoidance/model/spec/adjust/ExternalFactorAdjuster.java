package groom.backend.application.avoidance.model.spec.adjust;

import groom.backend.application.avoidance.dto.response.CongestionStatistics;
import groom.backend.application.avoidance.model.context.PredictionFactors;

public interface ExternalFactorAdjuster {
  double adjust(double baseline, CongestionStatistics stat, PredictionFactors factors);
}

