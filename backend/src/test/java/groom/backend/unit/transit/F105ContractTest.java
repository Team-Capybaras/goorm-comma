package groom.backend.unit.transit;

import groom.backend.domain.transit.controller.TransitController;
import groom.backend.domain.transit.service.spec.TransitService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(TransitController.class)
class F105ContractTest {

  @Autowired
  MockMvc mockMvc;

  @MockitoBean
  TransitService transitService;

  private static final String ENDPOINT = "/v1/transits";

  @Test
  @DisplayName("F105-01 지역 코드 기준 대중교통 정보 조회 API가 정상 동작한다")
  void shouldReturnTransitInfo() throws Exception {
    mockMvc.perform(get(ENDPOINT).param("area_code", "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data").exists());
  }

  @Test
  @DisplayName("F105-02 지하철역 정보 리스트가 반환된다")
  void shouldReturnSubwayStations() throws Exception {
    mockMvc.perform(get(ENDPOINT).param("area_code", "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.subwayStations").isArray());
  }

  @Test
  @DisplayName("F105-03 버스 정류장 정보 리스트가 반환된다")
  void shouldReturnBusStations() throws Exception {
    mockMvc.perform(get(ENDPOINT).param("area_code", "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.busStations").isArray());
  }

  @Test
  @DisplayName("F105-04 공유 자전거 정보 리스트가 반환된다")
  void shouldReturnSbikes() throws Exception {
    mockMvc.perform(get(ENDPOINT).param("area_code", "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.sbikes").isArray());
  }

  @Test
  @DisplayName("F105-05 모든 대중교통 항목에 좌표 정보가 포함된다")
  void shouldContainCoordinates() throws Exception {
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
    mockMvc.perform(get(ENDPOINT).param("area_code", "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.subwayStations[*].subId").exists())
            .andExpect(jsonPath("$.data.busStations[*].busStnId").exists())
            .andExpect(jsonPath("$.data.sbikes[*].sbikeSpotId").exists());
  }

  @Test
  @DisplayName("F105-07 대중교통 정보가 없는 경우 빈 배열로 반환된다")
  void shouldReturnEmptyArrays() throws Exception {
    mockMvc.perform(get(ENDPOINT).param("area_code", "POI999"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.subwayStations").isArray())
            .andExpect(jsonPath("$.data.busStations").isArray())
            .andExpect(jsonPath("$.data.sbikes").isArray());
  }

  @Test
  @DisplayName("F105-08 도착버스/도착시간 정보가 응답에 포함되지 않는다")
  void shouldNotContainArrivalInfo() throws Exception {
    mockMvc.perform(get(ENDPOINT).param("area_code", "POI001"))
            .andExpect(jsonPath("$.data.busStations[*].arrivalTime").doesNotExist())
            .andExpect(jsonPath("$.data.busStations[*].arrivalBus").doesNotExist());
  }
}
