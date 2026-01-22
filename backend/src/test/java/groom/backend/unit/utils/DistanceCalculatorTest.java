package groom.backend.unit.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DistanceCalculatorTest {

  @Test
  @DisplayName("F100-07 사용자 위치 기준 거리 계산이 정상 수행된다")
  void shouldCalculateDistance() {
    double distance = DistanceCalculator.calculate(
            37.5665, 126.9780,
            37.5700, 126.9820
    );

    assertThat(distance).isGreaterThan(0.5); // 0.53km
  }
}
