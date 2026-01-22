package groom.backend.unit.avoidance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AvoidanceController.class)
class F201ContractTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  CongestionAvoidanceService congestionAvoidanceService;

  private static final String ENDPOINT =
          "/api/v1/parks/{areaCode}/avoidance";

  @Test
  @DisplayName("F201-01 요일별 혼잡도 집계 데이터가 조회 가능하다")
  void shouldReturnWeeklyAggregation() throws Exception {
    mockMvc.perform(get(ENDPOINT, "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.chart.weekdays").exists())
            .andExpect(jsonPath("$.data.chart.weekdays").isArray());
  }

  @Test
  @DisplayName("F201-02 시간대별 혼잡도 집계 데이터가 조회 가능하다")
  void shouldReturnHourlyAggregation() throws Exception {
    mockMvc.perform(get(ENDPOINT, "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.chart.hours").exists())
            .andExpect(jsonPath("$.data.chart.hours").isArray());
  }

  @Test
  @DisplayName("F201-03 차트 렌더링 가능한 포맷으로 데이터가 반환된다")
  void shouldReturnChartRenderableFormat() throws Exception {
    mockMvc.perform(get(ENDPOINT, "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.chart.weekdays[*].label").exists())
            .andExpect(jsonPath("$.data.chart.weekdays[*].value").exists())
            .andExpect(jsonPath("$.data.chart.hours[*].hour").exists())
            .andExpect(jsonPath("$.data.chart.hours[*].level").exists());
  }

  @Test
  @DisplayName("F201-04 데이터가 없는 경우 빈 데이터로 반환된다")
  void shouldReturnEmptyDataWhenNoHistory() throws Exception {
    mockMvc.perform(get(ENDPOINT, "POI999"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.chart.weekdays").isArray())
            .andExpect(jsonPath("$.data.chart.weekdays", hasSize(0)))
            .andExpect(jsonPath("$.data.chart.hours").isArray())
            .andExpect(jsonPath("$.data.chart.hours", hasSize(0)));
  }

  @Test
  @DisplayName("F201-05 최적 방문 시간 안내 문구가 조건에 따라 생성된다")
  void shouldGenerateRecommendationMessage() throws Exception {
    mockMvc.perform(get(ENDPOINT, "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.recommendation.message").exists())
            .andExpect(jsonPath("$.data.recommendation.message",
                    anyOf(
                            containsString("추천"),
                            containsString("방문")
                    )
            ));
  }

  @Test
  @DisplayName("F201-06 예측 미구현 시 대체 문구가 명확히 반환된다")
  void shouldReturnFallbackMessageWhenPredictionDisabled() throws Exception {
    mockMvc.perform(get(ENDPOINT, "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.prediction.enabled").isBoolean())
            .andExpect(jsonPath("$.data.prediction.enabled").value(false))
            .andExpect(jsonPath("$.data.recommendation.message",
                    containsString("예측")
            ));
  }


}
