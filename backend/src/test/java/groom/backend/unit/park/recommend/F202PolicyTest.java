package groom.backend.unit.park.recommend;

/**
 * Congestion Filtering and distance&congestion priority rule policy check
 *
 */
public class F202PolicyTest {
}

package groom.backend.park.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AlternativeCongestionFilterTest {

  @Test
  @DisplayName("F202 추천 대상은 혼잡도가 여유/보통인 공원만 포함된다")
  void shouldFilterLowAndMediumCongestionOnly() {
    Park low = ParkFixture.withCongestion("LOW");
    Park medium = ParkFixture.withCongestion("MEDIUM");
    Park high = ParkFixture.withCongestion("HIGH");

    List<Park> result =
            AlternativeFilter.byCongestion(List.of(low, medium, high));

    assertThat(result).containsExactly(low, medium);
  }
}

class AlternativePriorityPolicyTest {

  @Test
  @DisplayName("F202 상세페이지 추천은 혼잡도 차이가 클수록 우선된다")
  void shouldPrioritizeByCongestionGap() {
    Park base = ParkFixture.withCongestion("HIGH");

    Park nearMedium = ParkFixture.withDistanceAndCongestion(1.0, "MEDIUM");
    Park farLow = ParkFixture.withDistanceAndCongestion(3.0, "LOW");

    List<Park> result =
            AlternativeSorter.sortForDetail(base, List.of(nearMedium, farLow));

    assertThat(result.get(0)).isEqualTo(farLow);
  }
}


class AlternativeRadiusPolicyTest {

  @Test
  @DisplayName("F202 기본 추천 반경은 5km이다")
  void defaultRadiusShouldBeFiveKm() {
    assertThat(AlternativeRadiusPolicy.defaultRadiusKm())
            .isEqualTo(5.0);
  }
}

/**
 * if using cache
 */

package groom.backend.park.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ParkAlternativeCacheF202IntegrationTest {

  @Autowired
  TestRestTemplate restTemplate;

  @Test
  @DisplayName("F202 동일 조건 재조회 시 추천 결과가 일관된다")
  void shouldReturnConsistentResultFromCache() {
    var r1 =
            restTemplate.getForObject(
                    "/api/v1/parks/POI001/alternatives",
                    AlternativeResponse.class
            );

    var r2 =
            restTemplate.getForObject(
                    "/api/v1/parks/POI001/alternatives",
                    AlternativeResponse.class
            );

    assertThat(r1.getData().getParks())
            .isEqualTo(r2.getData().getParks());
  }
}
