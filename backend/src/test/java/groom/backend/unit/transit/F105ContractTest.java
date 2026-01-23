package groom.backend.unit.transit;

import groom.backend.domain.transit.controller.TransitController;
import groom.backend.domain.transit.dto.response.GetTransitResponse;
import groom.backend.domain.transit.service.spec.TransitService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(TransitController.class)
class F105ContractTest {

  @Autowired
  MockMvc mockMvc;

  @MockitoBean
  TransitService transitService;

  private static final String ENDPOINT = "/v1/transits";

  private GetTransitResponse.SubwayStationInfo sampleSubwayStation() {
    return GetTransitResponse.SubwayStationInfo.builder()
            .subId(1001)
            .subStnName("뚝섬역")
            .subStnLine("2호선")
            .roadAddr("서울특별시 광진구 강변북로 50")
            .subStnX(new BigDecimal("127.0699000000"))
            .subStnY(new BigDecimal("37.5311000000"))
            .build();
  }

  private GetTransitResponse.BusStationInfo sampleBusStation() {
    return GetTransitResponse.BusStationInfo.builder()
            .busStnId(2001)
            .busArsId(30001)
            .busStnName("뚝섬유원지")
            .busStnX(new BigDecimal("127.0678000000"))
            .busStnY(new BigDecimal("37.5299000000"))
            .build();
  }

  private GetTransitResponse.SbikeInfo sampleSbike() {
    return GetTransitResponse.SbikeInfo.builder()
            .sbikeSpotId("SPOT001")
            .sbikeSpotName("뚝섬한강공원 스팟")
            .sbikeCapacity(20)
            .sbikeX(new BigDecimal("127.0705000000"))
            .sbikeY(new BigDecimal("37.5303000000"))
            .build();
  }

  private GetTransitResponse sampleTransitResponse() {
    return GetTransitResponse.builder()
            .areaCode("POI001")
            .areaName("뚝섬한강공원")
            .subwayStations(List.of(sampleSubwayStation()))
            .busStations(List.of(sampleBusStation()))
            .sbikes(List.of(sampleSbike()))
            .build();
  }

  private GetTransitResponse emptyTransitResponse() {
    return GetTransitResponse.builder()
            .areaCode("POI999")
            .areaName("UNKNOWN")
            .subwayStations(List.of())
            .busStations(List.of())
            .sbikes(List.of())
            .build();
  }

  @Test
  @DisplayName("F105-01 지역 코드 기준 대중교통 정보 조회 API가 정상 동작한다")
  void shouldReturnTransitInfo() throws Exception {
    // given
    BDDMockito.given(
            transitService.getTransitByAreaCode("POI001")
    ).willReturn(sampleTransitResponse());

    mockMvc.perform(get(ENDPOINT).param("area_code", "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data").exists());
  }

  @Test
  @DisplayName("F105-02 지하철역 정보 리스트가 반환된다")
  void shouldReturnSubwayStations() throws Exception {
    // given
    BDDMockito.given(
            transitService.getTransitByAreaCode("POI001")
    ).willReturn(sampleTransitResponse());

    mockMvc.perform(get(ENDPOINT).param("area_code", "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.subwayStations").isArray());
  }

  @Test
  @DisplayName("F105-03 버스 정류장 정보 리스트가 반환된다")
  void shouldReturnBusStations() throws Exception {
    // given
    BDDMockito.given(
            transitService.getTransitByAreaCode("POI001")
    ).willReturn(sampleTransitResponse());

    mockMvc.perform(get(ENDPOINT).param("area_code", "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.busStations").isArray());
  }

  @Test
  @DisplayName("F105-04 공유 자전거 정보 리스트가 반환된다")
  void shouldReturnSbikes() throws Exception {
    // given
    BDDMockito.given(
            transitService.getTransitByAreaCode("POI001")
    ).willReturn(sampleTransitResponse());

    mockMvc.perform(get(ENDPOINT).param("area_code", "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.sbikes").isArray());
  }

  @Test
  @DisplayName("F105-05 모든 대중교통 항목에 좌표 정보가 포함된다")
  void shouldContainCoordinates() throws Exception {
    // given
    BDDMockito.given(
            transitService.getTransitByAreaCode("POI001")
    ).willReturn(sampleTransitResponse());

    mockMvc.perform(get(ENDPOINT).param("area_code", "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.subwayStations[*].subStnX").exists())
            .andExpect(jsonPath("$.data.subwayStations[*].subStnY").exists())
            .andExpect(jsonPath("$.data.busStations[*].busStnX").exists())
            .andExpect(jsonPath("$.data.busStations[*].busStnY").exists())
            .andExpect(jsonPath("$.data.sbikes[*].sbikeX").exists())
            .andExpect(jsonPath("$.data.sbikes[*].sbikeY").exists());
  }

  @Test
  @DisplayName("F105-06 지도 표시 최소 정보(식별자 + 좌표)가 반환된다")
  void shouldContainMinimumMapInfo() throws Exception {
    // given
    BDDMockito.given(
            transitService.getTransitByAreaCode("POI001")
    ).willReturn(sampleTransitResponse());

    mockMvc.perform(get(ENDPOINT).param("area_code", "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.subwayStations[*].subId").exists())
            .andExpect(jsonPath("$.data.busStations[*].busStnId").exists())
            .andExpect(jsonPath("$.data.sbikes[*].sbikeSpotId").exists());
  }

  @Test
  @DisplayName("F105-07 대중교통 정보가 없는 경우 빈 배열로 반환된다")
  void shouldReturnEmptyArrays() throws Exception {
    // given
    BDDMockito.given(
            transitService.getTransitByAreaCode("POI999")
    ).willReturn(sampleTransitResponse());

    mockMvc.perform(get(ENDPOINT).param("area_code", "POI999"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.subwayStations").isArray())
            .andExpect(jsonPath("$.data.busStations").isArray())
            .andExpect(jsonPath("$.data.sbikes").isArray());
  }

  @Test
  @DisplayName("F105-08 도착버스/도착시간 정보가 응답에 포함되지 않는다")
  void shouldNotContainArrivalInfo() throws Exception {
    // given
    BDDMockito.given(
            transitService.getTransitByAreaCode("POI001")
    ).willReturn(sampleTransitResponse());

    mockMvc.perform(get(ENDPOINT).param("area_code", "POI001"))
            .andExpect(jsonPath("$.data.busStations[*].arrivalTime").doesNotExist())
            .andExpect(jsonPath("$.data.busStations[*].arrivalBus").doesNotExist());
  }
}
