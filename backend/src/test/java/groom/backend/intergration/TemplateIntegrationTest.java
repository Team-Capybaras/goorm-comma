package groom.backend.intergration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ParkBrowseFlowIT {

  @Autowired TestRestTemplate rest;

  @Test
  void userCanBrowseParkAndOpenDetail() {
    // 1. 리스트 조회
    var list = rest.getForObject("/api/v1/parks", ParkListResponse.class);

    String areaCode = list.getData().getParks().get(0).getAreaCode();

    // 2. 상세 진입
    var detail =
            rest.getForObject(
                    "/api/v1/parks/{areaCode}",
                    ParkDetailResponse.class,
                    areaCode
            );

    // 3. 일관성 검증
    assertThat(detail.getData().getPark().getAreaCode())
            .isEqualTo(areaCode);
  }
}
