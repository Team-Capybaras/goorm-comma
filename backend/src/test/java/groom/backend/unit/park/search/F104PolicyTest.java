package groom.backend.unit.park.search;

/**
 * policy라고 하긴 했는데 서비스 레이어 검색 및 필터링 테스트 하는 거임
 *
 */
public class F104PolicyTest {
}

package groom.backend.park.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ParkSearchSortByCongestionTest {

  @Test
  @DisplayName("F104 혼잡도순 정렬은 여유 → 혼잡 순으로 결정된다")
  void shouldSortByCongestionLevel() {
    Park a = ParkFixture.withCongestion("LOW");
    Park b = ParkFixture.withCongestion("HIGH");
    Park c = ParkFixture.withCongestion("MEDIUM");

    List<Park> result =
            ParkSearchSorter.sortByCongestion(List.of(b, c, a));

    assertThat(result)
            .extracting(Park::getCongestionLevel)
            .containsExactly("LOW", "MEDIUM", "HIGH");
  }
}


package groom.backend.park.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ParkSearchSortByDistanceTest {

  @Test
  @DisplayName("F104 거리순 정렬은 사용자 위치 기준 오름차순이다")
  void shouldSortByDistance() {
    Park near = ParkFixture.withDistance(0.5);
    Park far = ParkFixture.withDistance(2.0);

    List<Park> result =
            ParkSearchSorter.sortByDistance(List.of(far, near));

    assertThat(result)
            .extracting(Park::getDistance)
            .containsExactly(0.5, 2.0);
  }
}

package groom.backend.park.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ParkSearchTagFilterTest {

  @Test
  @DisplayName("F104 태그 필터 적용 시 해당 태그를 가진 공원만 반환된다")
  void shouldFilterByTags() {
    Park familyPark = ParkFixture.withTags(Set.of("FAMILY", "PLAYGROUND"));
    Park quietPark = ParkFixture.withTags(Set.of("QUIET"));

    List<Park> result =
            ParkSearchFilter.filterByTags(
                    List.of(familyPark, quietPark),
                    Set.of("FAMILY")
            );

    assertThat(result).containsExactly(familyPark);
  }

  @Test
  @DisplayName("F104 태그 필터 미적용 시 전체 결과가 유지된다")
  void shouldReturnAllWhenNoTagFilter() {
    Park p1 = ParkFixture.sample();
    Park p2 = ParkFixture.sample();

    List<Park> result =
            ParkSearchFilter.filterByTags(List.of(p1, p2), Set.of());

    assertThat(result).containsExactly(p1, p2);
  }
}
