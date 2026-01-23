package groom.backend.unit.weather;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * F102 정책 테스트
 */
class F102PolicyTest {

  /**
   * - AQI 수치 → 등급 매핑 정책 검증
   * - API, 외부 데이터와 무관한 순수 도메인 테스트
   *
   * TODO : 외부 API로부터 데이터를 가져오기 때문에 안정적인 검증을 위해선 독자적인 정책 설정 필요
   */
//  @ParameterizedTest(name = "AQI={0} -> {1}")
//  @CsvSource({
//          "0, GOOD",
//          "50, GOOD",
//          "51, MODERATE",
//          "100, MODERATE",
//          "101, BAD",
//          "250, BAD",
//          "251, VERY_BAD",
//          "999, VERY_BAD"
//  })
//  @DisplayName("F102 AQI 지수 구간 매핑 정책")
//  void shouldMapAqiToGrade(int aqi, AqiGrade expected) {
//    AqiGrade actual = AqiGradePolicy.from(aqi);
//    assertThat(actual).isEqualTo(expected);
//  }
}
