package groom.backend.unit.park.search;

import groom.backend.domain.park.controller.ParkController;
import groom.backend.domain.park.dto.response.GetParkSearchResponse;
import groom.backend.domain.park.service.spec.ParkService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * F104-B 공원명 검색 Contract Test
 *
 * 단건 검색 API 계약 검증
 * 검색 정책(결정성)은 Service 테스트 책임
 * 검색에 대한 계약 검증이기 때문에 실제로 동작하는지 테스트하지 않음.
 */
@WebMvcTest(ParkController.class)
class F104ParkSearchContractTest {

  @Autowired
  MockMvc mockMvc;

  @MockitoBean
  ParkService parkService;

  private static final String ENDPOINT = "/v1/parks/search";

  private GetParkSearchResponse sampleParkSearchResponse() {
    return GetParkSearchResponse.builder()
            .areaCode("POI093")
            .areaName("뚝섬한강공원")
            .build();
  }

  @Test
  @DisplayName("F104-01 공원명으로 검색 시 결과가 정상 반환된다")
  void shouldSearchByParkName() throws Exception {
    // given
    BDDMockito.given(
            parkService.searchParkByAreaName(anyString())
    ).willReturn(sampleParkSearchResponse());

    mockMvc.perform(get(ENDPOINT)
                    .param("search_keyword", "뚝섬"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.areaCode").exists())
            .andExpect(jsonPath("$.data.areaName").exists());
  }

  @Test
  @DisplayName("F104-03 검색어 부분 일치 시 결과가 반환된다")
  void shouldReturnOnPartialMatch() throws Exception {
    // given
    BDDMockito.given(
            parkService.searchParkByAreaName(anyString())
    ).willReturn(sampleParkSearchResponse());

    mockMvc.perform(get(ENDPOINT)
                    .param("search_keyword", "섬"))
            .andExpect(status().isOk());
  }

  @Test
  @DisplayName("F104-04 검색 결과에 상세 페이지 조회 가능한 식별자가 포함된다")
  void shouldContainIdentifier() throws Exception {
    // given
    BDDMockito.given(
            parkService.searchParkByAreaName(anyString())
    ).willReturn(sampleParkSearchResponse());

    mockMvc.perform(get(ENDPOINT)
                    .param("search_keyword", "뚝섬"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.areaCode").exists());
  }
}
