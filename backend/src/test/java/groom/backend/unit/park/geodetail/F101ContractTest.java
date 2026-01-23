package groom.backend.unit.park.geodetail;

import groom.backend.domain.park.controller.ParkController;
import groom.backend.domain.park.dto.response.GetAllParksBasicResponse;
import groom.backend.domain.park.service.spec.ParkService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * F101 지도용 공원 조회 API Contract Test
 *
 * 본 테스트는 지도 화면에서 사용하는 공원 마커용 API의
 * 응답 구조와 필수 필드 존재 여부만을 검증한다.
 *
 * 계산 로직, 갱신 정책, 상세 페이지 연계는
 * 본 테스트의 책임 범위가 아니다.
 */
@WebMvcTest(ParkController.class)
class F101ContractTest {

  @Autowired
  MockMvc mockMvc;

  @MockitoBean
  ParkService parkService;

  private GetAllParksBasicResponse sampleBasicResponse() {
    return GetAllParksBasicResponse.builder()
            .parks(List.of(
                    GetAllParksBasicResponse.ParkBasicInfo.builder()
                            .areaCode("POI093")
                            .areaName("뚝섬한강공원")
                            .longitude(127.069903)
                            .latitude(37.529546)
                            .build(),
                    GetAllParksBasicResponse.ParkBasicInfo.builder()
                            .areaCode("POI102")
                            .areaName("여의도한강공원")
                            .longitude(126.934889)
                            .latitude(37.528411)
                            .build()
            ))
            .build();
  }

  @Test
  @DisplayName("F101-01 지도용 API에서 모든 대상 공원이 좌표와 함께 반환된다")
  void shouldReturnAllParksWithCoordinates() throws Exception {
    /*
     * [검증 목적]
     * - 지도 마커 표시를 위해 모든 공원이 반환되는지 확인
     * - 각 공원에 좌표(latitude, longitude)가 포함되는지 확인
     *
     * [검증 범위]
     * - 응답 구조 및 필드 존재 여부
     * - 좌표 값의 계산 정확성은 검증하지 않음
     */

    // given
    BDDMockito.given(parkService.getAllParksBasic())
            .willReturn(sampleBasicResponse());

    mockMvc.perform(get("/v1/parks/all"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.parks").isArray())
            .andExpect(jsonPath("$.data.parks[*].areaCode").exists())
            .andExpect(jsonPath("$.data.parks[*].latitude").exists())
            .andExpect(jsonPath("$.data.parks[*].longitude").exists());
  }
  // 아래 테스트들은 F203에서 확인해야한다.
  // 단순 지도뷰에서 보여주지 않으며, FE에서 areaCode 기반으로 검색하게 되기 때문에 F203 상세 페이지 조회에서 테스트하도록 수정

//
//  @Test
//  @DisplayName("F101-02 각 공원에 혼잡도 단계가 포함되어 반환된다")
//  void shouldContainCongestionLevel() throws Exception {
//    /*
//     * [검증 목적]
//     * - 지도 화면에서 공원별 혼잡도 시각화를 위해
//     *   혼잡도 단계 필드가 응답에 포함되는지 확인
//     *
//     * [검증 범위]
//     * - 혼잡도 값의 의미/정확성은 검증하지 않음
//     * - 필드 존재 여부만 검증
//     */
//
//    // given
//    BDDMockito.given(parkService.getAllParksBasic())
//            .willReturn(sampleBasicResponse());
//
//    mockMvc.perform(get("/v1/parks/all"))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.data.parks[*].areaCongestLevel").exists());
//  }
//
//  @Test
//  @DisplayName("F101-04 최근 갱신 시간이 응답에 포함된다")
//  void shouldContainLastUpdatedTime() throws Exception {
//    /*
//     * [검증 목적]
//     * - 지도 데이터가 최신 정보 기준임을
//     *   클라이언트에서 판단할 수 있도록
//     *   최근 갱신 시각 필드가 포함되는지 확인
//     *
//     * [검증 범위]
//     * - 시간 포맷, 타임존, 기준 시점은 검증하지 않음
//     * - 문자열 형태의 필드 존재 여부만 검증
//     */
//
//    // given
//    BDDMockito.given(parkService.getAllParksBasic())
//            .willReturn(sampleBasicResponse());
//
//    mockMvc.perform(get("/v1/parks/all"))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.data.parks[*].updatedAt").exists())
//            .andExpect(jsonPath("$.data.parks[*].updatedAt").isString());
//  }
}
