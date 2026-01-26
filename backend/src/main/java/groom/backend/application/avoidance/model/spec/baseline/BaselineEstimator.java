package groom.backend.application.avoidance.model.spec.baseline;

import groom.backend.application.avoidance.dto.response.CongestionStatistics;

import java.util.List;

public interface BaselineEstimator {
  double estimate(CongestionStatistics stat, List<CongestionStatistics> all);
}
