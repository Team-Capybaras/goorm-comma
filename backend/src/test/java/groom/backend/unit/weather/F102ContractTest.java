package groom.backend.unit.weather;

import groom.backend.domain.weather.controller.WeatherController;
import groom.backend.domain.weather.service.spec.WeatherCurrentStatusService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * F102 환경 정보 조회 API Contract Test
 *
 * - API 정상 동작 여부
 * - 응답 필드 존재 여부
 * - 기본 유효성(하한)만 검증
 *
 * 정책적 판단(AQI 구간, 갱신 주기, 장애 대응)은 본 테스트 범위가 아니다.
 */
@WebMvcTest(WeatherController.class)
class F102ContractTest {

  @Autowired
  MockMvc mockMvc;

  @MockitoBean
  WeatherCurrentStatusService weatherCurrentStatusService;

  private static final String ENDPOINT = "/api/v1/weather/current";

  @Test
  @DisplayName("F102-01 공원(지역) 단위로 환경 정보 조회 API가 정상 동작한다")
  void shouldReturnWeatherStatus() throws Exception {
    mockMvc.perform(get(ENDPOINT).param("area_code", "11110"))
            .andExpect(status().isOk());
  }

  @Test
  @DisplayName("F102-02 날씨 및 대기환경 주요 필드가 정상 반환된다")
  void shouldContainWeatherFields() throws Exception {
    mockMvc.perform(get(ENDPOINT).param("area_code", "11110"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.temp").exists())
            .andExpect(jsonPath("$.humidity").exists())
            .andExpect(jsonPath("$.airIndex").exists())
            .andExpect(jsonPath("$.rainChance").exists());
  }

  @Test
  @DisplayName("F102-03 AQI 수치는 0 이상이다 (하한 검증)")
  void aqiShouldBeNonNegative() throws Exception {
    mockMvc.perform(get(ENDPOINT).param("area_code", "11110"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.airIndexLevel", greaterThanOrEqualTo(0.0)));
  }
}
