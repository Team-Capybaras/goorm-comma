package groom.backend.unit.weather;

import groom.backend.domain.weather.controller.WeatherController;
import groom.backend.domain.weather.dto.response.WeatherStatusResponse;
import groom.backend.domain.weather.service.spec.WeatherCurrentStatusService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

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

  private WeatherStatusResponse sampleWeatherStatusResponse() {
    return WeatherStatusResponse.builder()
            .dataGetTime(LocalDateTime.of(2026, 1, 6, 9, 0))
            .weatherTime(LocalDateTime.of(2026, 1, 6, 9, 0))
            .temp(2.5f)
            .sensibleTemp(-1.3f)
            .humidity(65)
            .windDirct("NW")
            .windSpd(3.2f)
            .precipitation("-")
            .rainChance(80)
            .precptType("눈")
            .precptMsg("눈이 내리고 있습니다")
            .uvIndexLevel(1)
            .uvIndex("낮음")
            .pm25Index("보통")
            .pm25(18)
            .pm10Index("좋음")
            .pm10(32)
            .airIndex("보통")
            .airIndexLevel(85.5f)   // 중요: 0 이상
            .airIndexMain("PM2.5")
            .airMsg("외출 시 마스크 착용을 권장합니다")
            .build();
  }

  private static final String ENDPOINT = "/v1/weather/current";

  @Test
  @DisplayName("F102-01 공원(지역) 단위로 환경 정보 조회 API가 정상 동작한다")
  void shouldReturnWeatherStatus() throws Exception {
    // given
    BDDMockito.given(
            weatherCurrentStatusService.getWeatherCurrentStatus("11110")
    ).willReturn(sampleWeatherStatusResponse());

    mockMvc.perform(get(ENDPOINT).param("area_code", "11110"))
            .andExpect(status().isOk());
  }

  @Test
  @DisplayName("F102-02 날씨 및 대기환경 주요 필드가 정상 반환된다")
  void shouldContainWeatherFields() throws Exception {
    // given
    BDDMockito.given(
            weatherCurrentStatusService.getWeatherCurrentStatus("11110")
    ).willReturn(sampleWeatherStatusResponse());

    // when & then
    mockMvc.perform(get(ENDPOINT).param("area_code", "11110"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.temp").exists())
            .andExpect(jsonPath("$.data.humidity").exists())
            .andExpect(jsonPath("$.data.airIndex").exists())
            .andExpect(jsonPath("$.data.rainChance").exists());
  }

  @Test
  @DisplayName("F102-03 AQI 수치는 0 이상이다 (하한 검증)")
  void aqiShouldBeNonNegative() throws Exception {
    // given
    BDDMockito.given(
            weatherCurrentStatusService.getWeatherCurrentStatus("11110")
    ).willReturn(sampleWeatherStatusResponse());

    // when & then
    mockMvc.perform(get(ENDPOINT).param("area_code", "11110"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.airIndexLevel", greaterThanOrEqualTo(0.0)));
  }
}
