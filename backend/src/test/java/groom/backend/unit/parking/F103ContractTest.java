package groom.backend.unit.parking;

package groom.backend.facility.interfaces;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// 실제 컨트롤러명으로 교체
@WebMvcTest(FacilityController.class)
class FacilityF103ContractTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  FacilityQueryService facilityQueryService;

  private static final String ENDPOINT =
          "/api/v1/parks/{areaCode}/facilities";

  @Test
  @DisplayName("F103-01 공원 기준 주차장 정보 조회 API가 정상 동작한다")
  void shouldReturnParkingFacilities() throws Exception {
    mockMvc.perform(get(ENDPOINT, "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.parkings").exists())
            .andExpect(jsonPath("$.data.parkings").isArray());
  }

  @Test
  @DisplayName("F103-02 공원 기준 전기차 충전소 정보 조회 API가 정상 동작한다")
  void shouldReturnEvChargers() throws Exception {
    mockMvc.perform(get(ENDPOINT, "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.evChargers").exists())
            .andExpect(jsonPath("$.data.evChargers").isArray());
  }

  @Test
  @DisplayName("F103-03 편의시설 정보에 위치 좌표가 포함된다")
  void facilitiesShouldContainCoordinates() throws Exception {
    mockMvc.perform(get(ENDPOINT, "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.parkings[*].latitude").isNumber())
            .andExpect(jsonPath("$.data.parkings[*].longitude").isNumber())
            .andExpect(jsonPath("$.data.evChargers[*].latitude").isNumber())
            .andExpect(jsonPath("$.data.evChargers[*].longitude").isNumber());
  }

  @Test
  @DisplayName("F103-04 주차장 정보에 주차 가능 수가 포함된다")
  void parkingShouldContainAvailableCount() throws Exception {
    mockMvc.perform(get(ENDPOINT, "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.parkings[*].availableCount").exists())
            .andExpect(jsonPath("$.data.parkings[*].availableCount").isNumber());
  }

  @Test
  @DisplayName("F103-05 주차장 정보에 수용 가능 수가 포함된다")
  void parkingShouldContainCapacity() throws Exception {
    mockMvc.perform(get(ENDPOINT, "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.parkings[*].capacity").exists())
            .andExpect(jsonPath("$.data.parkings[*].capacity").isNumber());
  }

  @Test
  @DisplayName("F103-06 주차장 정보에 유/무료 여부가 포함된다")
  void parkingShouldContainFeeType() throws Exception {
    mockMvc.perform(get(ENDPOINT, "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.parkings[*].isPaid").exists())
            .andExpect(jsonPath("$.data.parkings[*].isPaid").isBoolean());
  }

  @Test
  @DisplayName("F103-07 데이터가 없는 경우 빈 배열로 반환된다")
  void shouldReturnEmptyArrayWhenNoFacilities() throws Exception {
    mockMvc.perform(get(ENDPOINT, "POI999"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.parkings").isArray())
            .andExpect(jsonPath("$.data.parkings", hasSize(0)))
            .andExpect(jsonPath("$.data.evChargers").isArray())
            .andExpect(jsonPath("$.data.evChargers", hasSize(0)));
  }

  @Test
  @DisplayName("F103-08 null 또는 임의 값이 반환되지 않는다")
  void shouldNotReturnNullOrGarbageValues() throws Exception {
    mockMvc.perform(get(ENDPOINT, "POI001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.parkings[*].availableCount").value(everyItem(notNullValue())))
            .andExpect(jsonPath("$.data.parkings[*].capacity").value(everyItem(notNullValue())))
            .andExpect(jsonPath("$.data.parkings[*].isPaid").value(everyItem(notNullValue())))
            .andExpect(jsonPath("$.data.parkings[*].latitude").value(everyItem(notNullValue())))
            .andExpect(jsonPath("$.data.parkings[*].longitude").value(everyItem(notNullValue())));
  }
}

