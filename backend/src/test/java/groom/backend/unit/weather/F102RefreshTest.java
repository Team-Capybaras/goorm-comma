package groom.backend.unit.weather;

package groom.backend.environment.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

// 실제 정책 클래스명으로 교체
class EnvironmentRefreshPolicyTest {

  @Test
  @DisplayName("F102-08 환경 정보 데이터가 30분 단위로 갱신 대상이 된다")
  void shouldRefreshAfterThirtyMinutes() {
    LocalDateTime now = LocalDateTime.of(2026, 1, 22, 10, 0, 0);

    LocalDateTime lastUpdated29m = now.minusMinutes(29);
    LocalDateTime lastUpdated30m = now.minusMinutes(30);
    LocalDateTime lastUpdated31m = now.minusMinutes(31);

    Duration interval = Duration.ofMinutes(30);

    assertThat(RefreshPolicy.shouldRefresh(lastUpdated29m, now, interval)).isFalse();
    assertThat(RefreshPolicy.shouldRefresh(lastUpdated30m, now, interval)).isTrue();
    assertThat(RefreshPolicy.shouldRefresh(lastUpdated31m, now, interval)).isTrue();
  }
}

