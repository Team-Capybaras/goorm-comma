package groom.backend.application.avoidance.model.context;

import lombok.Builder;
import lombok.Getter;

/**
 * model 내부에서 쓰이는 컨텍스트 객체
 */
@Getter
@Builder
public class WeatherContext {
  private final int rainProbability; // 0 ~ 100
}
