package groom.backend.unit.parking;

package groom.backend.facility.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 주차장 데이터 무결성 정책 테스트
 */
class F103PolicyTest {

  @Test
  @DisplayName("F103 주차 가능 수는 수용 가능 수를 초과하지 않는다")
  void availableCountShouldNotExceedCapacity() {
    ParkingLot parking =
            new ParkingLot(30, 50, false);

    assertThat(parking.getAvailableCount())
            .isLessThanOrEqualTo(parking.getCapacity());
  }
}
