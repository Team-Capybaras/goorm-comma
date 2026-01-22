package groom.backend.unit.weather;

package groom.backend.environment.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

// 실제 enum/mapper 클래스명으로 교체
class AqiGradePolicyTest {

  @ParameterizedTest(name = "AQI={0} -> {1}")
  @CsvSource({
          "0, GOOD",
          "50, GOOD",
          "51, MODERATE",
          "100, MODERATE",
          "101, BAD",
          "250, BAD",
          "251, VERY_BAD",
          "999, VERY_BAD"
  })
  @DisplayName("F102 AQI 지수 구간: 좋음(0~50) 보통(51~100) 나쁨(101~250) 매우나쁨(251~)")
  void shouldMapAqiToGrade(int aqi, AqiGrade expected) {
    AqiGrade actual = AqiGradePolicy.from(aqi);
    assertThat(actual).isEqualTo(expected);
  }
}

