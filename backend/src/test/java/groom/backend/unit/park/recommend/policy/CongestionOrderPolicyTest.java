package groom.backend.unit.park.recommend.policy;

class CongestionOrderPolicyTest {

  @Test
  @DisplayName("혼잡도 레벨은 정렬용 숫자로 변환된다")
  void shouldConvertCongestionLevelToOrder() {
    assertThat(orderOf("여유")).isEqualTo(1);
    assertThat(orderOf("보통")).isEqualTo(2);
    assertThat(orderOf("붐빔")).isEqualTo(3);
    assertThat(orderOf("매우붐빔")).isEqualTo(4);
    assertThat(orderOf(null)).isEqualTo(999);
  }

  private int orderOf(String level) {
    return switch (level) {
      case "여유" -> 1;
      case "보통" -> 2;
      case "붐빔" -> 3;
      case "매우붐빔" -> 4;
      default -> 999;
    };
  }
}
