package groom.backend.application.avoidance.model.impl;

import groom.backend.application.avoidance.dto.response.CongestionPredResult;
import groom.backend.application.avoidance.model.spec.CongestionPredictModel;
import groom.backend.domain.avoidance.enums.Weekday;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CongestionPredictModelImpl implements CongestionPredictModel {
  @Override
  public CongestionPredResult predictCongestion(Weekday weekday) {
    return null;
  }
}
