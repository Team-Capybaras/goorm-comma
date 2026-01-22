package groom.backend.unit.parking;

package groom.backend.facility.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class FacilityRefreshPolicyTest {

  @Test
  @DisplayName("F103 편의시설 데이터는 30분 단위로 갱신 대상이 된다")
  void shouldRefreshAfterThirtyMinutes() {
    LocalDateTime now = LocalDateTime.of(2026, 1, 22, 11, 0);
    LocalDateTime lastUpdated = now.minusMinutes(31);

    boolean shouldRefresh =
            RefreshPolicy.shouldRefresh(
                    lastUpdated,
                    now,
                    Duration.ofMinutes(30)
            );

    assertThat(shouldRefresh).isTrue();
  }
}
