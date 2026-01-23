package groom.backend.unit.park.recommend.policy;

class TagSimilarityPolicyTest {

  @Test
  @DisplayName("태그 유사도는 Jaccard Similarity로 계산된다")
  void shouldCalculateJaccardSimilarity() {
    List<String> base = List.of("한강뷰", "평지");
    List<String> target = List.of("한강뷰", "자전거");

    double similarity = calculate(base, target);

    assertThat(similarity).isEqualTo(1.0 / 3.0);
  }

  private double calculate(List<String> a, List<String> b) {
    long intersection = a.stream().filter(b::contains).count();
    long union =
            a.stream().distinct().count()
                    + b.stream().filter(t -> !a.contains(t)).count();
    return (double) intersection / union;
  }
}

