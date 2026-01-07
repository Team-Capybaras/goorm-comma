package groom.backend.domain.population.controller;

import groom.backend.common.response.ApiResponse;
import groom.backend.domain.population.dto.response.GetPopulationResponse;
import groom.backend.domain.population.service.spec.PopulationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 인구 정보 조회 컨트롤러
 * 실시간 인구 현황 및 예보 정보 조회 API를 제공합니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/populations")
@Tag(name = "Population", description = "인구 정보 조회 관리")
public class PopulationController {
    private final PopulationService populationService;

    @GetMapping
    @Operation(
            summary = "인구 정보 조회",
            description = "지역 코드로 최신 실시간 인구 현황 및 예보 정보를 조회합니다."
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
    public ApiResponse<GetPopulationResponse> getPopulation(
            @Parameter(description = "지역 코드 (AREA_CODE)", required = true, example = "POI093")
            @RequestParam String areaCode
    ) {
        GetPopulationResponse response = populationService.getPopulationByAreaCode(areaCode);
        return ApiResponse.success(200, "인구 정보 조회 성공", response);
    }
}

