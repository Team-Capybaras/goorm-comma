package groom.backend.domain.seoul.controller;

import groom.backend.common.response.ApiResponse;
import groom.backend.domain.seoul.service.SeoulService;
import groom.backend.interfaces.seoul.dto.response.SeoulCityDataResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 서울시 공공 API 컨트롤러
 * 핫스팟 장소 정보 조회 API를 제공합니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/seoul")
@Tag(name = "Seoul", description = "서울시 공공 API 관리")
public class SeoulController {
    private final SeoulService seoulService;

    @GetMapping("/citydata")
    @Operation(
            summary = "핫스팟 장소 정보 조회",
            description = "서울시 공공 API를 통해 핫스팟 장소명으로 도시 데이터를 조회합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200", 
                    description = "조회 성공"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400", 
                    description = "잘못된 요청"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "500", 
                    description = "서버 오류"
            )
    })
    public ApiResponse<SeoulCityDataResponse> getCityData(
            @Parameter(description = "핫스팟 장소명 (AREA_NM)", required = true, example = "강남역")
            @RequestParam String areaNm,
            @Parameter(description = "시작 인덱스 (기본값: 1)", required = false)
            @RequestParam(required = false, defaultValue = "1") Integer startIndex,
            @Parameter(description = "종료 인덱스 (기본값: 5)", required = false)
            @RequestParam(required = false, defaultValue = "5") Integer endIndex
    ) {
        SeoulCityDataResponse response = seoulService.getCityDataByAreaNm(areaNm, startIndex, endIndex);
        return ApiResponse.success(200, "서울시 공공 API 조회 성공", response);
    }
}

