package groom.backend.application.avoidance.model.context;

import lombok.Builder;
import lombok.Getter;

/**
 * model 내부에서 쓰이는 컨텍스트 객체
 * 예측을 위한 각 요인을 저장한다.
 *
 */
@Getter
@Builder
public class PredictionFactors {
  private final boolean weatherAvailable;
  private final WeatherContext weather;

  // 확장 여지
  // 공휴일
  private final boolean holiday;
  // 주변 행사, 이벤트 여부
  private final boolean eventNearby;
}

