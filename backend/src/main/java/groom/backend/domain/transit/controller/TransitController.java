package groom.backend.domain.transit.controller;

import groom.backend.common.response.ApiResponse;
import groom.backend.domain.transit.dto.response.GetTransitResponse;
import groom.backend.domain.transit.service.spec.TransitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 대중교통 정보 조회 컨트롤러
 * 지하철역, 버스 정류장, 공유 자전거 정보 조회 API를 제공합니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/transits")
@Tag(name = "Transit", description = "대중교통 정보 조회 관리")
public class TransitController {
    private final TransitService transitService;

    @GetMapping
    @Operation(
            summary = "대중교통 정보 조회",
            description = "지역 코드로 지하철역, 버스 정류장, 공유 자전거 정보를 조회합니다."
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
    public ApiResponse<GetTransitResponse> getTransit(
            @Parameter(description = "지역 코드 (AREA_CODE)", required = true, example = "POI093")
            @RequestParam(name = "area_code") String areaCode
    ) {
        GetTransitResponse response = transitService.getTransitByAreaCode(areaCode);
        return ApiResponse.success(200, "대중교통 정보 조회 성공", response);
    }
}

