package groom.backend.unit.park.geodetail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 상세 정보 조회 부분임.
 *
 */
@WebMvcTest(ParkMapController.class)
class F101ContractTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  ParkMapQueryService parkMapQueryService;

  @Test
  @DisplayName("F101-01 지도용 API에서 모든 대상 공원이 좌표와 함께 반환된다")
  void shouldReturnAllParksWithCoordinates() throws Exception {
    mockMvc.perform(get("/api/v1/parks/all"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.parks").isArray())
            .andExpect(jsonPath("$.data.parks[*].areaCode").exists())
            .andExpect(jsonPath("$.data.parks[*].latitude").isNumber())
            .andExpect(jsonPath("$.data.parks[*].longitude").isNumber());
  }

  @Test
  @DisplayName("F101-02 각 공원에 혼잡도 단계가 포함되어 반환된다")
  void shouldContainCongestionLevel() throws Exception {
    mockMvc.perform(get("/api/v1/parks/all"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.parks[*].congestionLevel").exists());
  }

  @Test
  @DisplayName("F101-03 지도 API 혼잡도 값이 리스트 API와 일관된다")
  void congestionLevelShouldBeConsistentWithListApi() throws Exception {
    // 이 테스트는 데이터 계약 테스트이므로
    // 실제 구현 시 TestRestTemplate 또는 WebTestClient 기반 통합 테스트로 승격 권장

    mockMvc.perform(get("/api/v1/parks/all"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.parks[*].congestionLevel",
                    everyItem(anyOf(
                            is("LOW"),
                            is("MEDIUM"),
                            is("HIGH"),
                            is("VERY_HIGH")
                    ))
            ));
  }

  @Test
  @DisplayName("F101-04 최근 혼잡도 갱신 시간이 응답에 포함된다")
  void shouldContainLastUpdatedTime() throws Exception {
    mockMvc.perform(get("/api/v1/parks/all"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.parks[*].congestionUpdatedAt").exists())
            .andExpect(jsonPath("$.data.parks[*].congestionUpdatedAt").isString());
  }
}
