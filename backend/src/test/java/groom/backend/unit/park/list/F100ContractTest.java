package groom.backend.unit.park.list;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 전체 경도 및 위도 조회
 * 카드 조회하는 것도 추가할 것
 */
@WebMvcTest(ParkController.class)
class F100ContractTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  ParkQueryService parkQueryService;

  @Test
  @DisplayName("F100-01 공원 목록 API가 리스트 형태로 정상 반환된다")
  void shouldReturnParkList() throws Exception {
    // given
    // ParkListResponse fixture 반환 설정

    // when & then
    mockMvc.perform(get("/api/v1/parks"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.parks").isArray());
  }

  @Test
  @DisplayName("F100-02 각 공원 항목에 공원명이 포함된다")
  void shouldContainParkName() throws Exception {
    mockMvc.perform(get("/api/v1/parks"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.parks[*].parkName").exists())
            .andExpect(jsonPath("$.data.parks[*].parkName").isNotEmpty());
  }

  @Test
  @DisplayName("F100-03 각 공원 항목에 대표 이미지 URL이 포함된다")
  void shouldContainMainImageUrl() throws Exception {
    mockMvc.perform(get("/api/v1/parks"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.parks[*].imageUrl").exists())
            .andExpect(jsonPath("$.data.parks[*].imageUrl").isString());
  }

  @Test
  @DisplayName("F100-04 각 공원 항목에 혼잡도 단계 값이 포함된다")
  void shouldContainCongestionLevel() throws Exception {
    mockMvc.perform(get("/api/v1/parks"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.parks[*].congestionLevel").exists());
  }

  @Test
  @DisplayName("F100-05 혼잡도 단계 값이 ENUM 범위 내 값이다")
  void congestionLevelShouldBeValidEnum() throws Exception {
    mockMvc.perform(get("/api/v1/parks"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.parks[*].congestionLevel",
                    everyItem(anyOf(
                            is("LOW"),
                            is("MEDIUM"),
                            is("HIGH"),
                            is("VERY_HIGH")
                    ))
            ));
  }

  @Test
  @DisplayName("F100-06 혼잡도 단계 값이 색상 매핑 가능한 코드이다")
  void congestionLevelShouldBeColorMappable() throws Exception {
    mockMvc.perform(get("/api/v1/parks"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.parks[*].congestionColorCode").exists())
            .andExpect(jsonPath("$.data.parks[*].congestionColorCode",
                    everyItem(matchesPattern("^#[0-9a-fA-F]{6}$"))
            ));
  }

  @Test
  @DisplayName("F100-07 사용자 위치 전달 시 거리 값이 정상 계산되어 반환된다")
  void shouldReturnDistanceWhenLocationProvided() throws Exception {
    mockMvc.perform(get("/api/v1/parks")
                    .param("latitude", "37.5665")
                    .param("longitude", "126.9780"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.parks[*].distance").exists())
            .andExpect(jsonPath("$.data.parks[*].distance").isNumber());
  }

  @Test
  @DisplayName("F100-08 사용자 위치 미전달 시 거리 필드 처리 방식이 일관된다")
  void shouldHandleDistanceConsistentlyWhenLocationMissing() throws Exception {
    mockMvc.perform(get("/api/v1/parks"))
            .andExpect(status().isOk())
            // 정책 예시: null 고정
            .andExpect(jsonPath("$.data.parks[*].distance").value(everyItem(nullValue())));
  }
}
