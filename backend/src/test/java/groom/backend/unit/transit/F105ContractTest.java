package groom.backend.unit.transit;

package groom.backend.transit.interfaces;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ParkTransitController.class)
class TransitF105ContractTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  ParkTransitQueryService parkTransitQueryService;

  private static final String ENDPOINT =
          "/api/v1/parks/{areaCode}/transits";

  @Test
  @DisplayName("F105-01 공원 상세 페이지 기준 대중교통 정보 조회 API가 정상 동작한다")
  void shouldReturnTransitInfoByPark() throws Exception {
    mockMvc.perform(get(ENDPOINT, "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.transits").exists())
            .andExpect(jsonPath("$.data.transits").isArray());
  }

  @Test
  @DisplayName("F105-02 공원 기준 인접 버스정류장 정보가 조회된다")
  void shouldReturnBusStops() throws Exception {
    mockMvc.perform(get(ENDPOINT, "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.transits[?(@.type=='BUS')]").exists());
  }

  @Test
  @DisplayName("F105-03 공원 기준 인접 지하철역 정보가 조회된다")
  void shouldReturnSubwayStations() throws Exception {
    mockMvc.perform(get(ENDPOINT, "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.transits[?(@.type=='SUBWAY')]").exists());
  }

  @Test
  @DisplayName("F105-04 공원 기준 인접 따릉이 거치소 정보가 조회된다")
  void shouldReturnBikeStations() throws Exception {
    mockMvc.perform(get(ENDPOINT, "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.transits[?(@.type=='BIKE')]").exists());
  }

  @Test
  @DisplayName("F105-06 각 대중교통 항목에 위치 좌표 정보가 포함된다")
  void transitShouldContainCoordinates() throws Exception {
    mockMvc.perform(get(ENDPOINT, "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.transits[*].latitude").isNumber())
            .andExpect(jsonPath("$.data.transits[*].longitude").isNumber());
  }

  @Test
  @DisplayName("F105-07 대중교통 유형이 구분 가능한 값으로 반환된다")
  void transitTypeShouldBeExplicit() throws Exception {
    mockMvc.perform(get(ENDPOINT, "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.transits[*].type",
                    everyItem(anyOf(is("BUS"), is("SUBWAY"), is("BIKE")))));
  }

  @Test
  @DisplayName("F105-08 지도 표시가 가능한 최소 정보(식별자, 좌표)가 반환된다")
  void shouldContainMinimumMapInfo() throws Exception {
    mockMvc.perform(get(ENDPOINT, "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.transits[*].id").exists())
            .andExpect(jsonPath("$.data.transits[*].latitude").exists())
            .andExpect(jsonPath("$.data.transits[*].longitude").exists());
  }

  @Test
  @DisplayName("F105-09 도착버스/도착시간 정보가 백엔드 응답에 포함되지 않는다")
  void shouldNotContainArrivalInfo() throws Exception {
    mockMvc.perform(get(ENDPOINT, "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.transits[*].arrivalTime").doesNotExist())
            .andExpect(jsonPath("$.data.transits[*].arrivalBus").doesNotExist());
  }

  @Test
  @DisplayName("F105-10 외부 지도앱 연동을 위한 식별자 또는 링크 정보가 포함된다")
  void shouldContainExternalLinkOrId() throws Exception {
    mockMvc.perform(get(ENDPOINT, "POI001"))
            .andExpect(status().isOk())
            .andExpect(
                    jsonPath("$.data.transits[*].externalLink").exists()
            );
  }

  @Test
  @DisplayName("F105-11 대중교통 정보가 없는 경우 빈 배열로 반환된다")
  void shouldReturnEmptyArrayWhenNoTransit() throws Exception {
    mockMvc.perform(get(ENDPOINT, "POI999"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.transits").isArray())
            .andExpect(jsonPath("$.data.transits", hasSize(0)));
  }
}
