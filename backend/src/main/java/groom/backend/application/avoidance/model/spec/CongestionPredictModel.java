package groom.backend.application.avoidance.model.spec;

import groom.backend.application.avoidance.dto.response.CongestionPredResult;
import groom.backend.application.avoidance.dto.response.CongestionStatistics;

import java.util.List;

/**
 * 유동인구 혼잡도 예측 모델

 * 본 모델은 학습 기반의 머신러닝 모델을 칭하지 않으나, 파이프라인의 일부는 ML 모델로 대체될 수 있음.
 *
 * 과거 집계 통계 데이터를 기준선(baseline)으로 삼고
 * 외생 변수(exogenous factors)를 단계적으로 보정하여
 * 최종 혼잡도 값을 산출하는 규칙 기반 추정 파이프라인으로 작동한다.
 *
 * 예측 파이프라인은 다음과 같은 단계로 구성된다.
 *
 * 1. Baseline Estimator
 *    - 과거 통계 데이터로부터 기준 혼잡도 값을 산출한다.
 *    - 이동 평균, 지수 이동 평균, 최대/평균 값 등 다양한 전략으로 교체 가능하며, ML 모델로도 대체할 수 있다.
 *
 * 2. Adjuster (External Factor Adjuster)
 *    - 날씨, 이벤트 여부 등 외생 변인을 기준 혼잡도에 반영하여 값을 보정한다.
 *    - 외생 변인이 없는 경우에도 파이프라인은 정상 동작해야 한다.
 *
 * 3. Uncertainty Model (Optional)
 *    - 예측 값의 불확실성을 표현하거나 변동 폭을 제어하기 위한 단계이다.
 *    - 현재는 비활성화되거나 단순 통과(pass-through) 형태로 사용될 수 있다.
 *
 * 4. Post Processor
 *    - 최종 결과를 단위 절삭, 범위 제한 등의 규칙에 따라 정제한다.
 *
 * Adjuster 단계에서 사용되는 외생 변인을 수집하기 위해
 * Provider가 사용될 수 있으며,
 * Provider는 다른 도메인, 서비스 또는 외부 API로부터 데이터를 조회하여
 * 예측 계산에 필요한 조건(Context)으로 가공될 수 있는 원천 데이터를 제공한다.
 */
public interface CongestionPredictModel {
  /**
   * 혼잡도에 강수 확률을 가중치로 한 나이브하게 혼잡도 추정하는 기능
   * 주의 : 검증되지 않음.
   * @return
   */
  public List<CongestionPredResult> predictCongestion(List<CongestionStatistics> congestionStatistics, String areaCode);
}
