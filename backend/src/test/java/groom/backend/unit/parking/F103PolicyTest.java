package groom.backend.unit.parking;

package groom.backend.facility.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ParkingCapacityPolicyTest {

  @Test
  @DisplayName("F103 주차 가능 수는 수용 가능 수를 초과하지 않는다")
  void availableCountShouldNotExceedCapacity() {
    ParkingLot parking =
            new ParkingLot(
                    /* availableCount */ 30,
                    /* capacity */ 50,
                    /* isPaid */ false
            );

    assertThat(parking.getAvailableCount())
            .isLessThanOrEqualTo(parking.getCapacity());
  }
}
