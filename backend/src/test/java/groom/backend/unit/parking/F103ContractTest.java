package groom.backend.unit.parking;

import groom.backend.domain.parking.controller.ParkingStatusController;
import groom.backend.domain.parking.dto.response.ChargerDetailResponse;
import groom.backend.domain.parking.dto.response.ChargerStationResponse;
import groom.backend.domain.parking.dto.response.ParkingLotResponse;
import groom.backend.domain.parking.dto.response.ParkingStatusResponse;
import groom.backend.domain.parking.service.spec.ParkingStatusService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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

  private ChargerDetailResponse sampleChargerDetail() {
    return ChargerDetailResponse.builder()
            .chargerId(1)
            .chargerType("DC콤보")
            .chargerUpdated(LocalDateTime.of(2026, 1, 6, 8, 30))
            .output(50)
            .method("단독")
            .chargerStatus("사용가능")
            .statusUpdated(LocalDateTime.of(2026, 1, 6, 9, 0))
            .build();
  }

  private ChargerStationResponse sampleChargerStation() {
    return ChargerStationResponse.builder()
            .stationId("PI000740")
            .stationName("서울시청 충전소")
            .stationAddr("서울특별시 중구 세종대로 110")
            .stationX(new BigDecimal("126.9784000000"))
            .stationY(new BigDecimal("37.5665000000"))
            .stationUsetime("평일 10:00-17:00")
            .stationParkpay(false)
            .stationKindDetail("공영주차장")
            .stationLimitDetail(null)
            .chargerDetails(List.of(sampleChargerDetail()))
            .build();
  }

  private ParkingLotResponse sampleParkingLot() {
    return ParkingLotResponse.builder()
            .prkCode(3206241L)
            .prkName("국립중앙박물관 주차장")
            .prkType("NW")
            .capacity(120)
            .currentInfoYn(false)
            .currentPrkCnt(null)
            .currentPrkTime(null)
            .payYn(true)
            .addr("서울특별시 중구 태평로1가")
            .roadAddr("서울특별시 중구 세종대로 110")
            .prkX(new BigDecimal("126.9779"))
            .prkY(new BigDecimal("37.5659"))
            .build();
  }

  // 정상 데이터
  private ParkingStatusResponse sampleParkingStatusResponse() {
    return ParkingStatusResponse.builder()
            .parkingLots(List.of(sampleParkingLot()))
            .chargerStations(List.of(sampleChargerStation()))
            .build();
  }

  // 데이터 없음 케이스
  private ParkingStatusResponse emptyParkingStatusResponse() {
    return ParkingStatusResponse.builder()
            .parkingLots(List.of())
            .chargerStations(List.of())
            .build();
  }

  private static final String ENDPOINT = "/v1/parking";

  @Test
  @DisplayName("F103-01 공원 기준 주차장 정보 조회 API가 정상 동작한다")
  void shouldReturnParkingLots() throws Exception {
    // given
    BDDMockito.given(
            parkingStatusService.getParkingStatus("POI093")
    ).willReturn(sampleParkingStatusResponse());

    // when&then
    mockMvc.perform(get(ENDPOINT).param("area_code", "POI093"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.parkingLots").isArray());
  }

  @Test
  @DisplayName("F103-02 공원 기준 전기차 충전소 정보 조회 API가 정상 동작한다")
  void shouldReturnChargerStations() throws Exception {
    // given
    BDDMockito.given(
            parkingStatusService.getParkingStatus("POI093")
    ).willReturn(sampleParkingStatusResponse());

    // when&then
    mockMvc.perform(get(ENDPOINT).param("area_code", "POI093"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.chargerStations").isArray());
  }

  @Test
  @DisplayName("F103-03 편의시설 정보에 위치 좌표가 포함된다")
  void facilitiesShouldContainCoordinates() throws Exception {
    // given
    BDDMockito.given(
            parkingStatusService.getParkingStatus("POI093")
    ).willReturn(sampleParkingStatusResponse());

    // when&then
    mockMvc.perform(get(ENDPOINT).param("area_code", "POI093"))
            .andExpect(jsonPath("$.data.parkingLots[*].prkX").exists())
            .andExpect(jsonPath("$.data.parkingLots[*].prkY").exists())
            .andExpect(jsonPath("$.data.chargerStations[*].stationX").exists())
            .andExpect(jsonPath("$.data.chargerStations[*].stationY").exists());
  }

  @Test
  @DisplayName("F103-04 주차장 필수 필드는 null 이 아니다")
  void parkingRequiredFieldsShouldNotBeNull() throws Exception {
    // given
    BDDMockito.given(
            parkingStatusService.getParkingStatus("POI093")
    ).willReturn(sampleParkingStatusResponse());

    // when&then
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
    // given
    BDDMockito.given(
            parkingStatusService.getParkingStatus("POI999")
    ).willReturn(emptyParkingStatusResponse());

    // when&then
    mockMvc.perform(get(ENDPOINT).param("area_code", "POI999"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.parkingLots").isArray())
            .andExpect(jsonPath("$.data.parkingLots", hasSize(0)))
            .andExpect(jsonPath("$.data.chargerStations").isArray())
            .andExpect(jsonPath("$.data.chargerStations", hasSize(0)));
  }
}
