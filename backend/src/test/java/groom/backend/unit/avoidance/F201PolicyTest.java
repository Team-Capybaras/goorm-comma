package groom.backend.unit.avoidance;

package groom.backend.avoidance.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 시간대가 오늘인 경우, 오늘이 아닐 경우
 * 오늘인 경우 현재 시간대부터 23시
 * 23시 넘어가면 오늘아님
 * 오늘이 아니면 09시부터 23시
 */
class BestVisitTimePolicyTest {

  @Test
  @DisplayName("F201 혼잡도가 가장 낮은 시간대가 최적 방문 시간으로 선택된다")
  void shouldPickLowestCongestionHour() {
    Map<Integer, Integer> hourlyCongestion =
            Map.of(
                    9, 3,
                    10, 1,
                    11, 4
            );

    int bestHour =
            BestVisitTimePolicy.select(hourlyCongestion);

    assertThat(bestHour).isEqualTo(10);
  }
}

/**
 * 포맷 테스트
 * 어디다 넣지
 */
package groom.backend.avoidance.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ChartDataFormatPolicyTest {

  @Test
  @DisplayName("F201 차트 데이터는 label-value 또는 hour-level 구조를 만족한다")
  void shouldSatisfyChartFormat() {
    ChartPoint p = new ChartPoint("월요일", 2);

    assertThat(p.getLabel()).isNotNull();
    assertThat(p.getValue()).isGreaterThanOrEqualTo(0);
  }
}
