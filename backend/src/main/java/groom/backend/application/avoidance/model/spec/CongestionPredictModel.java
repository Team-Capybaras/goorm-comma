package groom.backend.application.avoidance.model.spec;

import groom.backend.application.avoidance.dto.response.CongestionPredResult;
import groom.backend.domain.avoidance.enums.Weekday;

/**
 * 각 요일별 0시부터 23시까지의 유동인구 혼잡도 예측 모델
 */
public interface CongestionPredictModel {
  /**
   * 혼잡도에 강수 확률을 가중치로 한 나이브하게 혼잡도 추정하는 기능
   * 주의 : 검증되지 않음.
   * @return
   */
  public CongestionPredResult predictCongestion(Weekday weekday);
}
