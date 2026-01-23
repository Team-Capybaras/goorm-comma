package groom.backend.unit.park.list;

import groom.backend.application.park.controller.ParkApplicationController;
import groom.backend.application.park.dto.response.GetAllParksResponse;
import groom.backend.application.park.enums.ParkSortType;
import groom.backend.application.park.service.spec.ParkApplicationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ParkApplicationController.class)
class F100ContractTest {

  @Autowired
  MockMvc mockMvc;

//  @MockBean
  @MockitoBean
  ParkApplicationService parkApplicationService;

  private GetAllParksResponse sampleResponse(Double distance) {
    return GetAllParksResponse.builder()
            .parks(List.of(
                    GetAllParksResponse.ParkInfo.builder()
                            .areaCode("POI093")
                            .areaName("뚝섬한강공원")
                            .longitude(127.069903)
                            .latitude(37.529546)
                            .areaCongestLevel("보통")
                            .distance(distance)
                            .images(List.of("https://example.com/image.jpg"))
                            .build()
            ))
            .size(1)
            .totalCount(1)
            .hasNext(false)
            .nextCursor(null)
            .build();
  }

  @Test
  @DisplayName("F100-01 공원 목록 API가 리스트 형태로 정상 반환된다")
  void shouldReturnParkList() throws Exception {
    BDDMockito.given(
            parkApplicationService.getParks(
                    null, null, ParkSortType.DEFAULT, null, null, null
            )
    ).willReturn(sampleResponse(null));

    mockMvc.perform(get("/v1/parks"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.parks").isArray());
  }

  @Test
  @DisplayName("F100-02 각 공원 항목에 공원명이 포함된다")
  void shouldContainParkName() throws Exception {
    BDDMockito.given(parkApplicationService.getParks(
            null, null, ParkSortType.DEFAULT, null, null, null
    )).willReturn(sampleResponse(null));

    mockMvc.perform(get("/v1/parks"))
            .andExpect(jsonPath("$.data.parks[0].areaName").exists())
            .andExpect(jsonPath("$.data.parks[0].areaName").isNotEmpty());
  }

  @Test
  @DisplayName("F100-03 각 공원 항목에 대표 이미지 URL이 포함된다")
  void shouldContainImageUrls() throws Exception {
    BDDMockito.given(parkApplicationService.getParks(
            null, null, ParkSortType.DEFAULT, null, null, null
    )).willReturn(sampleResponse(null));

    mockMvc.perform(get("/v1/parks"))
            .andExpect(jsonPath("$.data.parks[0].images").exists())
            .andExpect(jsonPath("$.data.parks[0].images").isArray());
  }

  @Test
  @DisplayName("F100-04 각 공원 항목에 혼잡도 단계 값이 포함된다")
  void shouldContainCongestionLevel() throws Exception {
    BDDMockito.given(parkApplicationService.getParks(
            null, null, ParkSortType.DEFAULT, null, null, null
    )).willReturn(sampleResponse(null));

    mockMvc.perform(get("/v1/parks"))
            .andExpect(jsonPath("$.data.parks[0].areaCongestLevel").exists());
  }

  @Test
  @DisplayName("F100-07 사용자 위치 전달 시 거리 값이 반환된다")
  void shouldReturnDistanceWhenLocationProvided() throws Exception {
    BDDMockito.given(parkApplicationService.getParks(
            null, null, ParkSortType.BY_DISTANCE, null, 127.0, 37.0
    )).willReturn(sampleResponse(2.5));

    mockMvc.perform(
                    get("/v1/parks")
                            .param("sort", "BY_DISTANCE")
                            .param("longitude", "127.0")
                            .param("latitude", "37.0")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.parks[0].distance").isNumber());
  }

  @Test
  @DisplayName("F100-08 사용자 위치 미전달 시 거리 필드는 null이다")
  void shouldReturnNullDistanceWhenLocationMissing() throws Exception {
    BDDMockito.given(parkApplicationService.getParks(
            null, null, ParkSortType.DEFAULT, null, null, null
    )).willReturn(sampleResponse(null));

    mockMvc.perform(get("/v1/parks"))
            .andExpect(jsonPath("$.data.parks[0].distance").value(org.hamcrest.Matchers.nullValue()));
  }
}
