package groom.backend.unit.park.list;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class F100RefreshTest {

  @Test
  @DisplayName("F100-09 기본 30분 주기 이후 혼잡도 값이 갱신된다")
  void shouldRefreshAfterDefaultInterval() {
    // given
    // 마지막 갱신 시각 = now - 31분

    // when
    boolean shouldRefresh = RefreshPolicy.shouldRefresh(lastUpdated, now);

    // then
    assertThat(shouldRefresh).isTrue();
  }

  @Test
  @DisplayName("F100-10 5분 주기 설정 시 실제 갱신 주기가 반영된다")
  void shouldRespectFiveMinuteInterval() {
    // given
    // 설정 주기 = 5분

    // then
    assertThat(RefreshPolicy.interval()).isEqualTo(5);
  }

  @Test
  @DisplayName("F100-11 갱신 실패 시 이전 데이터가 유지된다")
  void shouldKeepPreviousDataOnFailure() {
    // given
    // 갱신 실패 시나리오

    // then
    assertThat(resultData).isSameAs(previousData);
  }
}

