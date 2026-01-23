package groom.backend.unit.park.detail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import groom.backend.application.park.controller.ParkController;
import groom.backend.application.park.service.spec.ParkApplicationService;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ParkController.class)
class F203ContractTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  ParkApplicationService parkApplicationService;

  private static final String ENDPOINT = "/v1/parks/{areaCode}";

  @Test
  @DisplayName("F203-01 공원 상세 조회 API가 정상 동작한다")
  void shouldReturnParkDetail() throws Exception {
    mockMvc.perform(get(ENDPOINT, "POI093"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.park").exists());
  }

  @Test
  @DisplayName("F203-02 공원 식별 정보(areaCode, areaName)가 포함된다")
  void shouldContainParkIdentity() throws Exception {
    mockMvc.perform(get(ENDPOINT, "POI093"))
            .andExpect(jsonPath("$.data.park.areaCode").exists())
            .andExpect(jsonPath("$.data.park.areaName").exists())
            .andExpect(jsonPath("$.data.park.areaName").isString());
  }

  @Test
  @DisplayName("F203-03 공원 위치 정보(위도, 경도)가 포함된다")
  void shouldContainCoordinates() throws Exception {
    mockMvc.perform(get(ENDPOINT, "POI093"))
            .andExpect(jsonPath("$.data.park.latitude").exists())
            .andExpect(jsonPath("$.data.park.longitude").exists());
  }

  @Test
  @DisplayName("F203-04 사용자 위치 제공 시 거리 정보가 포함된다")
  void shouldContainDistanceWhenLocationProvided() throws Exception {
    mockMvc.perform(
                    get(ENDPOINT, "POI093")
                            .param("latitude", "37.529546")
                            .param("longitude", "127.069903")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.park.distance").exists())
            .andExpect(jsonPath("$.data.park.distance").isNumber());
  }

  @Test
  @DisplayName("F203-05 공원 주소 정보가 포함된다")
  void shouldContainAddress() throws Exception {
    mockMvc.perform(get(ENDPOINT, "POI093"))
            .andExpect(jsonPath("$.data.park.address").exists())
            .andExpect(jsonPath("$.data.park.address").isString());
  }

  @Test
  @DisplayName("F203-06 혼잡도 단계 정보가 포함된다")
  void shouldContainCongestionLevel() throws Exception {
    mockMvc.perform(get(ENDPOINT, "POI093"))
            .andExpect(jsonPath("$.data.park.areaCongestLevel").exists());
  }

  @Test
  @DisplayName("F203-07 공원 태그 정보가 배열 형태로 포함된다")
  void shouldContainTags() throws Exception {
    mockMvc.perform(get(ENDPOINT, "POI093"))
            .andExpect(jsonPath("$.data.park.tags").exists())
            .andExpect(jsonPath("$.data.park.tags").isArray());
  }

  @Test
  @DisplayName("F203-08 공원 이미지 리스트가 포함된다")
  void shouldContainImages() throws Exception {
    mockMvc.perform(get(ENDPOINT, "POI093"))
            .andExpect(jsonPath("$.data.park.images").exists())
            .andExpect(jsonPath("$.data.park.images").isArray());
  }

  @Test
  @DisplayName("F203-09 공원 설명(feature 목록)이 포함된다")
  void shouldContainFeatures() throws Exception {
    mockMvc.perform(get(ENDPOINT, "POI093"))
            .andExpect(jsonPath("$.data.park.features").exists())
            .andExpect(jsonPath("$.data.park.features").isArray())
            .andExpect(jsonPath("$.data.park.features[*].type").exists())
            .andExpect(jsonPath("$.data.park.features[*].description").exists());
  }

  @Test
  @DisplayName("F203-10 여유 예상 시간 정보는 nullable 필드로 반환된다")
  void recommendedVisitHourIsNullable() throws Exception {
    mockMvc.perform(get(ENDPOINT, "POI093"))
            .andExpect(jsonPath("$.data.park.recommendedVisitHour").exists());
  }
}
