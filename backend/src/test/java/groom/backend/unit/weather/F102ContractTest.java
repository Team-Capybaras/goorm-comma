package groom.backend.unit.weather;

package groom.backend.environment.interfaces;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// 실제 컨트롤러명/서비스명으로 교체
@WebMvcTest(EnvironmentController.class)
class EnvironmentF102ContractTest {

  // 실제 엔드포인트로 치환
  private static final String ENV_ENDPOINT = "/api/v1/environments"; // 예: ?area_code=POI001

  @Autowired MockMvc mockMvc;

  @MockBean EnvironmentQueryService environmentQueryService;

  @Test
  @DisplayName("F102-01 공원 단위로 환경 정보 조회 API가 정상 동작한다")
  void shouldReturnEnvironmentDataByPark() throws Exception {
    mockMvc.perform(get(ENV_ENDPOINT).param("area_code", "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data").exists());
  }

  @Test
  @DisplayName("F102-02 날씨 정보 필드가 정상 반환된다")
  void shouldReturnWeatherField() throws Exception {
    mockMvc.perform(get(ENV_ENDPOINT).param("area_code", "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.weather").exists())
            .andExpect(jsonPath("$.data.weather").isString());
  }

  @Test
  @DisplayName("F102-03 기온 정보 필드가 정상 반환된다")
  void shouldReturnTemperatureField() throws Exception {
    mockMvc.perform(get(ENV_ENDPOINT).param("area_code", "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.tempC").exists())
            .andExpect(jsonPath("$.data.tempC").isNumber());
  }

  @Test
  @DisplayName("F102-04 습도 정보 필드가 정상 반환된다")
  void shouldReturnHumidityField() throws Exception {
    mockMvc.perform(get(ENV_ENDPOINT).param("area_code", "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.humidityPct").exists())
            .andExpect(jsonPath("$.data.humidityPct").isNumber())
            .andExpect(jsonPath("$.data.humidityPct", allOf(greaterThanOrEqualTo(0), lessThanOrEqualTo(100))));
  }

  @Test
  @DisplayName("F102-05 대기질 지수(AQI) 필드가 정상 반환된다")
  void shouldReturnAqiField() throws Exception {
    mockMvc.perform(get(ENV_ENDPOINT).param("area_code", "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.aqi").exists())
            .andExpect(jsonPath("$.data.aqi").isNumber());
  }

  @Test
  @DisplayName("F102-06 강수확률 필드가 정상 반환된다")
  void shouldReturnRainChanceField() throws Exception {
    mockMvc.perform(get(ENV_ENDPOINT).param("area_code", "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.rainChancePct").exists())
            .andExpect(jsonPath("$.data.rainChancePct").isNumber())
            .andExpect(jsonPath("$.data.rainChancePct", allOf(greaterThanOrEqualTo(0), lessThanOrEqualTo(100))));
  }

  @Test
  @DisplayName("F102-07 AQI 수치가 정의된 구간 범위 내(0 이상)이다")
  void aqiShouldBeNonNegative() throws Exception {
    mockMvc.perform(get(ENV_ENDPOINT).param("area_code", "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.aqi", greaterThanOrEqualTo(0)));
  }

  @Test
  @DisplayName("추가: 데이터 소스(source)가 응답에 포함된다")
  void shouldContainSourceField() throws Exception {
    mockMvc.perform(get(ENV_ENDPOINT).param("area_code", "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.source").exists())
            .andExpect(jsonPath("$.data.source").isString())
            .andExpect(jsonPath("$.data.source", not(isEmptyOrNullString())));
  }
}

