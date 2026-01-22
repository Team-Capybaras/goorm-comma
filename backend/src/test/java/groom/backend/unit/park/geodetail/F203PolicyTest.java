package groom.backend.unit.park.geodetail;

package groom.backend.park.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CongestionLevelF203PolicyTest {

  @Test
  @DisplayName("F203 혼잡도 단계는 여유/보통/약간붐빔/붐빔 중 하나이다")
  void congestionLevelShouldFollowSpec() {
    assertThat(CongestionLevel.values())
            .extracting(Enum::name)
            .containsExactlyInAnyOrder(
                    "LOW",
                    "MEDIUM",
                    "SLIGHTLY_CROWDED",
                    "CROWDED"
            );
  }
}

class ParkTagPolicyTest {

  @Test
  @DisplayName("F203 공원 태그는 TAG_LIST 정의 범위 내 값이다")
  void tagsShouldFollowTagList() {
    for (ParkTag tag : ParkTag.values()) {
      assertThat(TagDefinition.TAG_LIST)
              .contains(tag.name());
    }
  }
}

class ParkFeaturePolicyTest {

  @Test
  @DisplayName("F203 공원 설명은 FEATURE 정의서에 따라 제공된다")
  void descriptionShouldFollowFeatureDefinition() {
    ParkFeature feature = ParkFeature.REST_AREA;

    assertThat(feature.getDescription()).isNotBlank();
  }
}
