package groom.backend.unit.avoidance;

import groom.backend.application.avoidance.dto.response.CongestionRecommendResponse;
import groom.backend.application.avoidance.dto.response.WeekdayAggregateResponse;
import groom.backend.domain.avoidance.enums.Weekday;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import groom.backend.application.avoidance.controller.CongestionAvoidanceController;
import groom.backend.application.avoidance.service.spec.CongestionAvoidanceService;
import groom.backend.application.avoidance.service.spec.ParkStatisticsService;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CongestionAvoidanceController.class)
class F201ContractTest {

  @Autowired
  MockMvc mockMvc;

  @MockitoBean
  CongestionAvoidanceService congestionAvoidanceService;

  @MockitoBean
  ParkStatisticsService parkStatisticsService;

  private static final String ENDPOINT = "/v1/avoidance/statistics";

  private WeekdayAggregateResponse.HourAggregateResponse sampleHourAggregate(
          int hour, Integer past, Integer now, Integer future
  ) {
    return WeekdayAggregateResponse.HourAggregateResponse.builder()
            .hour(hour)
            .past(past)
            .now(now)
            .future(future)
            .build();
  }

  private WeekdayAggregateResponse sampleWeekdayAggregate() {
    return WeekdayAggregateResponse.builder()
            .weekday(Weekday.MON)
            .today(false)
            .recommendedVisitHour(14)
            .hours(List.of(
                    sampleHourAggregate(10, 120, null, 200),
                    sampleHourAggregate(11, 130, null, 210)
            ))
            .build();
  }

  private CongestionRecommendResponse sampleCongestionResponse() {
    return CongestionRecommendResponse.builder()
            .areaCode("POI001")
            .refreshTime(LocalDateTime.of(2026, 1, 13, 13, 30))
            .weekdays(List.of(
                    sampleWeekdayAggregate()
            ))
            .build();
  }

  private CongestionRecommendResponse emptyCongestionResponse(String areaCode) {
    return CongestionRecommendResponse.builder()
            .areaCode(areaCode)
            .refreshTime(LocalDateTime.of(2026, 1, 13, 13, 30))
            .weekdays(List.of())
            .build();
  }

  @Test
  @DisplayName("F201-01 요일별 혼잡도 집계 데이터가 조회 가능하다")
  void shouldReturnWeekdayAggregates() throws Exception {
    //given
    BDDMockito.given(
            congestionAvoidanceService.getParkStatistics("POI001")
    ).willReturn(sampleCongestionResponse());

    mockMvc.perform(get(ENDPOINT)
                    .param("area_code", "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.weekdays").exists())
            .andExpect(jsonPath("$.data.weekdays").isArray());
  }

  @Test
  @DisplayName("F201-02 각 요일 항목은 요일 식별자와 통계 값을 포함한다")
  void weekdayAggregateShouldContainRequiredFields() throws Exception {
    //given
    BDDMockito.given(
            congestionAvoidanceService.getParkStatistics("POI001")
    ).willReturn(sampleCongestionResponse());

    mockMvc.perform(get(ENDPOINT)
                    .param("area_code", "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.weekdays[*].weekday").exists())
            .andExpect(jsonPath("$.data.weekdays[*].hours").exists());
  }

  @Test
  @DisplayName("F201-03 응답에는 기준 시각(refreshTime)이 포함된다")
  void shouldContainRefreshTime() throws Exception {
    //given
    BDDMockito.given(
            congestionAvoidanceService.getParkStatistics("POI001")
    ).willReturn(sampleCongestionResponse());

    mockMvc.perform(get(ENDPOINT)
                    .param("area_code", "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.refreshTime").exists())
            .andExpect(jsonPath("$.data.refreshTime").isString());
  }

  @Test
  @DisplayName("F201-04 데이터가 없는 경우 요일 목록은 빈 배열로 반환된다")
  void shouldReturnEmptyWeekdaysWhenNoStatistics() throws Exception {
    //given
    BDDMockito.given(
            congestionAvoidanceService.getParkStatistics("POI999")
    ).willReturn(emptyCongestionResponse("POI999"));

    mockMvc.perform(get(ENDPOINT)
                    .param("area_code", "POI999"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.weekdays").isArray())
            .andExpect(jsonPath("$.data.weekdays").isEmpty());
  }

  @Test
  @DisplayName("F201-05 응답은 요청한 area_code를 자기 식별 정보로 포함한다")
  void shouldEchoAreaCode() throws Exception {
    //given
    BDDMockito.given(
            congestionAvoidanceService.getParkStatistics("POI001")
    ).willReturn(sampleCongestionResponse());

    mockMvc.perform(get(ENDPOINT)
                    .param("area_code", "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.areaCode").value("POI001"));
  }
}
