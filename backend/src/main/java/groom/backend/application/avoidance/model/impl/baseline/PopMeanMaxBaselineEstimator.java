package groom.backend.application.avoidance.model.impl.baseline;

import groom.backend.application.avoidance.dto.response.CongestionStatistics;
import groom.backend.application.avoidance.model.spec.baseline.BaselineEstimator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PopMeanMaxBaselineEstimator implements BaselineEstimator {

  @Override
  public double estimate(CongestionStatistics stat, List<CongestionStatistics> all) {
    return stat.popMeanMax();
  }
}
