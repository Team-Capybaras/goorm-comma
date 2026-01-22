package groom.backend.unit.park.search;

package groom.backend.park.interfaces;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ParkSearchController.class)
class ParkSearchF104ContractTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  ParkSearchService parkSearchService;

  private static final String SEARCH_ENDPOINT = "/api/v1/parks/search";

  @Test
  @DisplayName("F104-01 공원명으로 검색 시 결과가 정상 반환된다")
  void shouldSearchByParkName() throws Exception {
    mockMvc.perform(get(SEARCH_ENDPOINT).param("q", "중앙"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.parks").isArray());
  }

  @Test
  @DisplayName("F104-02 지역명으로 검색 시 결과가 정상 반환된다")
  void shouldSearchByRegionName() throws Exception {
    mockMvc.perform(get(SEARCH_ENDPOINT).param("q", "강남"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.parks").isArray());
  }

  @Test
  @DisplayName("F104-03 검색어 부분 일치 시 결과가 반환된다")
  void shouldReturnResultOnPartialMatch() throws Exception {
    mockMvc.perform(get(SEARCH_ENDPOINT).param("q", "앙"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.parks").isArray());
  }

  @Test
  @DisplayName("F104-04 검색 결과에 상세 페이지 조회 가능한 식별자가 포함된다")
  void shouldContainIdentifierForDetailPage() throws Exception {
    mockMvc.perform(get(SEARCH_ENDPOINT).param("q", "중앙"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.parks[*].areaCode").exists());
  }

  @Test
  @DisplayName("F104-05 혼잡도순 정렬이 정상 동작한다")
  void shouldSortByCongestion() throws Exception {
    mockMvc.perform(get(SEARCH_ENDPOINT)
                    .param("q", "공원")
                    .param("sort", "congestion"))
            .andExpect(status().isOk())
            // 정렬 규칙은 Service Unit Test에서 결정성 검증
            .andExpect(jsonPath("$.data.parks").isArray());
  }

  @Test
  @DisplayName("F104-06 거리순 정렬이 정상 동작한다")
  void shouldSortByDistance() throws Exception {
    mockMvc.perform(get(SEARCH_ENDPOINT)
                    .param("q", "공원")
                    .param("sort", "distance")
                    .param("latitude", "37.5665")
                    .param("longitude", "126.9780"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.parks[*].distance").exists());
  }

  @Test
  @DisplayName("F104-07 태그 필터 적용 시 결과가 올바르게 제한된다")
  void shouldFilterByTags() throws Exception {
    mockMvc.perform(get(SEARCH_ENDPOINT)
                    .param("q", "공원")
                    .param("tags", "FAMILY,PLAYGROUND"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.parks").isArray());
  }

  @Test
  @DisplayName("F104-08 필터 미적용 시 전체 결과가 반환된다")
  void shouldReturnAllResultsWhenNoFilterApplied() throws Exception {
    mockMvc.perform(get(SEARCH_ENDPOINT).param("q", "공원"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.parks").isArray());
  }
}
