package groom.backend.unit.parking;

import groom.backend.domain.parking.controller.ParkingStatusController;
import groom.backend.domain.parking.service.spec.ParkingStatusService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * F103 편의시설 정보 조회 API Contract Test
 *
 * - 스펙 상 nullable=false 필드만 검증
 * - 데이터 없음 → empty array 반환 정책 검증
 * - 값의 의미, 계산, 갱신 정책은 본 테스트 범위 아님
 */
@WebMvcTest(ParkingStatusController.class)
class F103ContractTest {

  @Autowired
  MockMvc mockMvc;

  @MockitoBean
  ParkingStatusService parkingStatusService;

  private static final String ENDPOINT = "/api/v1/parking";

  @Test
  @DisplayName("F103-01 공원 기준 주차장 정보 조회 API가 정상 동작한다")
  void shouldReturnParkingLots() throws Exception {
    mockMvc.perform(get(ENDPOINT).param("area_code", "POI093"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.parkingLots").isArray());
  }

  @Test
  @DisplayName("F103-02 공원 기준 전기차 충전소 정보 조회 API가 정상 동작한다")
  void shouldReturnChargerStations() throws Exception {
    mockMvc.perform(get(ENDPOINT).param("area_code", "POI093"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.chargerStations").isArray());
  }

  @Test
  @DisplayName("F103-03 편의시설 정보에 위치 좌표가 포함된다")
  void facilitiesShouldContainCoordinates() throws Exception {
    mockMvc.perform(get(ENDPOINT).param("area_code", "POI093"))
            .andExpect(jsonPath("$.data.parkingLots[*].prkX").exists())
            .andExpect(jsonPath("$.data.parkingLots[*].prkY").exists())
            .andExpect(jsonPath("$.data.chargerStations[*].stationX").exists())
            .andExpect(jsonPath("$.data.chargerStations[*].stationY").exists());
  }

  @Test
  @DisplayName("F103-04 주차장 필수 필드는 null 이 아니다")
  void parkingRequiredFieldsShouldNotBeNull() throws Exception {
    mockMvc.perform(get(ENDPOINT).param("area_code", "POI093"))
            .andExpect(jsonPath("$.data.parkingLots[*].prkCode").value(everyItem(notNullValue())))
            .andExpect(jsonPath("$.data.parkingLots[*].capacity").value(everyItem(notNullValue())))
            .andExpect(jsonPath("$.data.parkingLots[*].payYn").value(everyItem(notNullValue())))
            .andExpect(jsonPath("$.data.parkingLots[*].prkX").value(everyItem(notNullValue())))
            .andExpect(jsonPath("$.data.parkingLots[*].prkY").value(everyItem(notNullValue())));
  }

  @Test
  @DisplayName("F103-05 데이터가 없는 경우 빈 배열로 반환된다")
  void shouldReturnEmptyArraysWhenNoFacilities() throws Exception {
    mockMvc.perform(get(ENDPOINT).param("area_code", "POI999"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.parkingLots").isArray())
            .andExpect(jsonPath("$.data.parkingLots", hasSize(0)))
            .andExpect(jsonPath("$.data.chargerStations").isArray())
            .andExpect(jsonPath("$.data.chargerStations", hasSize(0)));
  }
}
