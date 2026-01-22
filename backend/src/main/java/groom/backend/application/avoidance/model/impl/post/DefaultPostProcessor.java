package groom.backend.application.avoidance.model.impl.post;

import groom.backend.application.avoidance.model.context.PredictionFactors;
import groom.backend.application.avoidance.model.spec.post.PostProcessor;
import org.springframework.stereotype.Component;

/**
 * 후처리기
 * 혼잡도 100단위 절삭
 */
@Component
public class DefaultPostProcessor implements PostProcessor {

  @Override
  public int finalizeToCongestionUnit(double value, PredictionFactors factors) {
    int v = (int) value;
    return (v / 100) * 100;
  }
}

