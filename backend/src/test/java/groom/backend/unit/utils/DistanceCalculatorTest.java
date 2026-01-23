package groom.backend.unit.utils;

import groom.backend.common.utils.DistanceCalculator;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 위,경도 기반 하버사인 공식을 이용한 직선거리 계산 유틸리티 클래스 유닛 테스트
 */
class DistanceCalculatorTest {

  @Test
  @DisplayName("F100-07 사용자 위치 기준 거리 계산이 정상 수행된다")
  void shouldCalculateDistance() {
    double distance = DistanceCalculator.calculateDistance(
            37.5665, 126.9780,
            37.5700, 126.9820
    );
    // 0.53km과 0.01 범위 이내로 근사한지 근사치 테스트
    assertThat(distance).isCloseTo(0.53, Offset.offset(0.01));
  }
}
