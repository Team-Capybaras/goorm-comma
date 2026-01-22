package groom.backend.unit.transit;

/**
 * 정책에 대한 테스트
 * 지도 표기를 위한 최소 정보?
 *
 */
public class F105PolicyTest {
}

package groom.backend.transit.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TransitMapMinimumInfoPolicyTest {

  @Test
  @DisplayName("F105 지도 표시를 위한 최소 정보는 id + 좌표이다")
  void shouldSatisfyMinimumMapInfo() {
    Transit t = new Transit(
            "ST123",
            TransitType.SUBWAY,
            37.5665,
            126.9780
    );

    assertThat(t.getId()).isNotNull();
    assertThat(t.getLatitude()).isNotNull();
    assertThat(t.getLongitude()).isNotNull();
  }
}
