package groom.backend.unit.park.geodetail;

package groom.backend.park.interfaces;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ParkController.class)
class ParkDetailF203ContractTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  ParkQueryService parkQueryService;

  private static final String ENDPOINT =
          "/api/v1/parks/{areaCode}";

  @Test
  @DisplayName("F203-01 공원 상세 조회 API가 정상 동작한다")
  void shouldReturnParkDetail() throws Exception {
    mockMvc.perform(get(ENDPOINT, "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.park").exists());
  }

  @Test
  @DisplayName("F203-02 공원명이 응답에 포함된다")
  void shouldContainParkName() throws Exception {
    mockMvc.perform(get(ENDPOINT, "POI001"))
            .andExpect(jsonPath("$.data.park.parkName").exists())
            .andExpect(jsonPath("$.data.park.parkName").isString());
  }

  @Test
  @DisplayName("F203-03 사용자 기준 거리 정보가 포함된다")
  void shouldContainDistance() throws Exception {
    mockMvc.perform(get(ENDPOINT, "POI001")
                    .param("latitude", "37.5665")
                    .param("longitude", "126.9780"))
            .andExpect(jsonPath("$.data.park.distance").exists())
            .andExpect(jsonPath("$.data.park.distance").isNumber());
  }

  @Test
  @DisplayName("F203-04 공원 주소 정보가 포함된다")
  void shouldContainAddress() throws Exception {
    mockMvc.perform(get(ENDPOINT, "POI001"))
            .andExpect(jsonPath("$.data.park.address").exists())
            .andExpect(jsonPath("$.data.park.address").isString());
  }

  @Test
  @DisplayName("F203-05 혼잡도 단계 정보가 포함된다")
  void shouldContainCongestionLevel() throws Exception {
    mockMvc.perform(get(ENDPOINT, "POI001"))
            .andExpect(jsonPath("$.data.park.congestionLevel").exists());
  }

  @Test
  @DisplayName("F203-06 혼잡도 예측 문구가 포함된다")
  void shouldContainCongestionPredictionMessage() throws Exception {
    mockMvc.perform(get(ENDPOINT, "POI001"))
            .andExpect(jsonPath("$.data.park.congestionMessage").exists())
            .andExpect(jsonPath("$.data.park.congestionMessage").isString());
  }

  @Test
  @DisplayName("F203-07 공원 태그 정보가 포함된다")
  void shouldContainTags() throws Exception {
    mockMvc.perform(get(ENDPOINT, "POI001"))
            .andExpect(jsonPath("$.data.park.tags").exists())
            .andExpect(jsonPath("$.data.park.tags").isArray());
  }

  @Test
  @DisplayName("F203-08 공원 설명 정보가 포함된다")
  void shouldContainDescription() throws Exception {
    mockMvc.perform(get(ENDPOINT, "POI001"))
            .andExpect(jsonPath("$.data.park.description").exists())
            .andExpect(jsonPath("$.data.park.description").isString());
  }
}
