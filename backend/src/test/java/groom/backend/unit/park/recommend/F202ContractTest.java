package groom.backend.unit.park.recommend;

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

/**
 * at the Detail page
 */
@WebMvcTest(ParkAlternativeController.class)
class ParkAlternativeF202DetailContractTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  ParkAlternativeService parkAlternativeService;

  private static final String ENDPOINT =
          "/api/v1/parks/{areaCode}/alternatives";

  @Test
  @DisplayName("F202-01 선택 공원이 약간붐빔 또는 붐빔일 때 추천이 활성화된다")
  void shouldEnableRecommendationWhenParkIsCrowded() throws Exception {
    mockMvc.perform(get(ENDPOINT, "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.enabled").value(true));
  }

  @Test
  @DisplayName("F202-02 추천 결과 개수가 3~5개 범위 내이다")
  void recommendationCountShouldBeBetweenThreeAndFive() throws Exception {
    mockMvc.perform(get(ENDPOINT, "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.parks", hasSize(greaterThanOrEqualTo(3))))
            .andExpect(jsonPath("$.data.parks", hasSize(lessThanOrEqualTo(5))));
  }

  @Test
  @DisplayName("F202-03 추천 공원의 혼잡도가 여유 또는 보통이다")
  void recommendedParksShouldHaveLowOrMediumCongestion() throws Exception {
    mockMvc.perform(get(ENDPOINT, "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.parks[*].congestionLevel",
                    everyItem(anyOf(is("LOW"), is("MEDIUM")))));
  }

  @Test
  @DisplayName("F202-04 추천 결과에 거리 정보가 포함된다")
  void recommendationShouldContainDistance() throws Exception {
    mockMvc.perform(get(ENDPOINT, "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.parks[*].distanceKm").exists())
            .andExpect(jsonPath("$.data.parks[*].distanceKm").isNumber());
  }

  @Test
  @DisplayName("F202-05 추천 이유 텍스트가 함께 반환된다")
  void recommendationShouldContainReasonText() throws Exception {
    mockMvc.perform(get(ENDPOINT, "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.parks[*].reason").exists())
            .andExpect(jsonPath("$.data.parks[*].reason").isString());
  }
}

/**
 * at the main page 5km filtering
 */

@WebMvcTest(ParkAlternativeController.class)
class ParkAlternativeF202ListContractTest {

  @Autowired MockMvc mockMvc;
  @MockBean ParkAlternativeService parkAlternativeService;

  @Test
  @DisplayName("F202-06 5km 이내 추천 대상이 없을 경우 빈 결과가 반환된다")
  void shouldReturnEmptyWhenNoParkWithinFiveKm() throws Exception {
    mockMvc.perform(get("/api/v1/parks/alternatives")
                    .param("latitude", "37.0")
                    .param("longitude", "126.0"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.parks").isArray())
            .andExpect(jsonPath("$.data.parks", hasSize(0)));
  }
}
