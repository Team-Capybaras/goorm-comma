package groom.backend.unit.park.search;

import groom.backend.application.park.controller.ParkApplicationController;
import groom.backend.application.park.service.spec.ParkApplicationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * F104-A 공원 리스트 조회 Contract Test
 *
 * - 필터/정렬 파라미터 수용 여부 검증
 * - 결과 정렬의 결정성은 Service 테스트 책임
 */
@WebMvcTest(ParkApplicationController.class)
class F104ParkListContractTest {

  @Autowired
  MockMvc mockMvc;

  @MockitoBean
  ParkApplicationService parkApplicationService;

  private static final String ENDPOINT = "/api/v1/parks";

  @Test
  @DisplayName("F104-05 혼잡도순 정렬 파라미터를 수용하여 정상 응답한다")
  void shouldAcceptCongestionSort() throws Exception {
    mockMvc.perform(get(ENDPOINT)
                    .param("sort", "LOW_CONGESTION"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.parks").isArray());
  }

  @Test
  @DisplayName("F104-06 거리순 정렬 시 좌표가 주어지면 정상 응답한다")
  void shouldAcceptDistanceSortWithCoordinates() throws Exception {
    mockMvc.perform(get(ENDPOINT)
                    .param("sort", "BY_DISTANCE")
                    .param("latitude", "37.5665")
                    .param("longitude", "126.9780"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.parks[*].distance").exists());
  }

  @Test
  @DisplayName("F104-07 태그 필터 적용 시 정상 응답한다")
  void shouldAcceptTagFilter() throws Exception {
    mockMvc.perform(get(ENDPOINT)
                    .param("tag_names", "한강뷰")
                    .param("tag_names", "평지"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.parks").isArray());
  }

  @Test
  @DisplayName("F104-08 필터 미적용 시 기본 리스트가 반환된다")
  void shouldReturnDefaultListWhenNoFilter() throws Exception {
    mockMvc.perform(get(ENDPOINT))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.parks").isArray());
  }
}
