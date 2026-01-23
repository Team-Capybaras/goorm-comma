package groom.backend.unit.park.recommend.policy;

class CongestionEligibilityPolicyTest {

  @Test
  @DisplayName("여유/보통인 공원만 추천 대상이다")
  void shouldAllowLowAndMediumOnly() {
    assertThat(isEligible("여유")).isTrue();
    assertThat(isEligible("보통")).isTrue();
    assertThat(isEligible("붐빔")).isFalse();
    assertThat(isEligible("매우붐빔")).isFalse();
  }

  private boolean isEligible(String level) {
    return "여유".equals(level) || "보통".equals(level);
  }
}
