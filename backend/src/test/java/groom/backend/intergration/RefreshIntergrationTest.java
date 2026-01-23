package groom.backend.intergration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 기술 부채
 * 30분단위 데이터 갱신 체크 테스트
 */
public class RefreshIntergrationTest {

  /**
   * 환경 정보 데이터 30분 단위 갱신 여부 유닛 테스트
   * TODO : 기상청 API가 기술 부채로 남아있으며, 갱신 여부 확인하는 기능 미구현
   */
//  @Test
//  @DisplayName("F102-08 환경 정보 데이터는 30분 단위로 갱신 대상이 된다")
//  void shouldRefreshAfterThirtyMinutes() {
//    LocalDateTime now = LocalDateTime.of(2026, 1, 22, 10, 0, 0);
//
//    Duration interval = Duration.ofMinutes(30);
//
//    assertThat(RefreshPolicy.shouldRefresh(now.minusMinutes(29), now, interval)).isFalse();
//    assertThat(RefreshPolicy.shouldRefresh(now.minusMinutes(30), now, interval)).isTrue();
//    assertThat(RefreshPolicy.shouldRefresh(now.minusMinutes(31), now, interval)).isTrue();
//  }


  /**
   * 기술 부채.
   * RefreshIntegrationTest에서 테스트하는 편이 좋을 듯?
   */
  class F103RefreshTest {

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

  package groom.backend.intergration;

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


}
