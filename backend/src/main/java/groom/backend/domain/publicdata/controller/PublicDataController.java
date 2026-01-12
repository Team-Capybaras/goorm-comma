package groom.backend.domain.publicdata.controller;

import groom.backend.common.response.ApiResponse;
import groom.backend.domain.publicdata.dto.SavePublicDataResponse;
import groom.backend.domain.publicdata.service.PublicDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 공공 데이터 저장 컨트롤러
 * 서울시 공공 API 결과를 DB에 저장하는 API를 제공합니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/publicdata")
@Tag(name = "PublicData", description = "공공 데이터 저장 관리")
public class PublicDataController {
    private final PublicDataService publicDataService;

    @PostMapping("/save")
    @Operation(
            summary = "공공 데이터 저장",
            description = "서울시 공공 API를 호출하여 결과를 DB에 저장합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "저장 성공"
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
    public ApiResponse<SavePublicDataResponse> saveCityData(
            @Parameter(description = "핫스팟 장소명 (AREA_NM)", required = true, example = "강남역")
            @RequestParam String areaNm,
            @Parameter(description = "시작 인덱스 (기본값: 1)", required = false)
            @RequestParam(required = false, defaultValue = "1") Integer startIndex,
            @Parameter(description = "종료 인덱스 (기본값: 5)", required = false)
            @RequestParam(required = false, defaultValue = "5") Integer endIndex
    ) {
        SavePublicDataResponse result = publicDataService.saveCityData(areaNm, startIndex, endIndex);
        return ApiResponse.success(200, "공공 데이터 저장 성공", result);
    }
}

