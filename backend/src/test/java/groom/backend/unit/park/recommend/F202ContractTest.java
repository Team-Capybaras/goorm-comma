package groom.backend.unit.park.recommend;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import groom.backend.application.park.controller.ParkRecommendController;
import groom.backend.application.park.service.spec.ParkRecommendService;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ParkRecommendController.class)
class F202ContractTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  ParkRecommendService parkRecommendService;

  private static final String ENDPOINT = "/api/v1/parks/recommend";

  @Test
  @DisplayName("F202-01 기준 공원이 붐빌 경우 대체지 추천 API가 정상 동작한다")
  void shouldReturnAlternativeRecommendationsByBaseArea() throws Exception {
    mockMvc.perform(
                    get(ENDPOINT)
                            .param("longitude", "127.0")
                            .param("latitude", "37.5")
                            .param("base_area_code", "POI001")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.parks").isArray());
  }

  @Test
  @DisplayName("F202-02 추천 결과 개수는 최대 5개를 초과하지 않는다")
  void recommendationSizeShouldNotExceedFive() throws Exception {
    mockMvc.perform(
                    get(ENDPOINT)
                            .param("longitude", "127.0")
                            .param("latitude", "37.5")
                            .param("base_area_code", "POI001")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.parks", hasSize(lessThanOrEqualTo(5))));
  }

  @Test
  @DisplayName("F202-03 추천 결과에 공원 식별자(areaCode)가 포함된다")
  void recommendationShouldContainAreaCode() throws Exception {
    mockMvc.perform(
                    get(ENDPOINT)
                            .param("longitude", "127.0")
                            .param("latitude", "37.5")
                            .param("base_area_code", "POI001")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.parks[*].areaCode").exists());
  }

  @Test
  @DisplayName("F202-04 추천 결과에 거리 정보(distance)가 포함된다")
  void recommendationShouldContainDistance() throws Exception {
    mockMvc.perform(
                    get(ENDPOINT)
                            .param("longitude", "127.0")
                            .param("latitude", "37.5")
                            .param("base_area_code", "POI001")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.parks[*].distance").exists())
            .andExpect(jsonPath("$.data.parks[*].distance").isNumber());
  }

  @Test
  @DisplayName("F202-05 반경 기준 추천 시 5km 이내 공원이 없으면 빈 배열을 반환한다")
  void shouldReturnEmptyWhenNoParkWithinLimitDistance() throws Exception {
    mockMvc.perform(
                    get(ENDPOINT)
                            .param("longitude", "127.0")
                            .param("latitude", "37.5")
                            .param("limit_distance", "5")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.parks").isArray())
            .andExpect(jsonPath("$.data.parks", hasSize(0)));
  }

  @Test
  @DisplayName("F202-06 limit_distance와 base_area_code를 동시에 전달하면 400 오류가 발생한다")
  void shouldReturnBadRequestWhenBothParametersProvided() throws Exception {
    mockMvc.perform(
                    get(ENDPOINT)
                            .param("longitude", "127.0")
                            .param("latitude", "37.5")
                            .param("limit_distance", "5")
                            .param("base_area_code", "POI001")
            )
            .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("F202-07 limit_distance와 base_area_code가 모두 없으면 400 오류가 발생한다")
  void shouldReturnBadRequestWhenNeitherParameterProvided() throws Exception {
    mockMvc.perform(
                    get(ENDPOINT)
                            .param("longitude", "127.0")
                            .param("latitude", "37.5")
            )
            .andExpect(status().isBadRequest());
  }
}
