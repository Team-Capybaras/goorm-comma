package groom.backend.application.avoidance.model.spec.post;

import groom.backend.application.avoidance.model.context.PredictionFactors;

/**
 * 후처리기
 */
public interface PostProcessor {
  int finalizeToCongestionUnit(double value, PredictionFactors factors);
}

