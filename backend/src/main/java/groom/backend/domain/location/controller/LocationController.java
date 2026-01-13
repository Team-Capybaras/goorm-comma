package groom.backend.domain.location.controller;

import groom.backend.common.response.ApiResponse;
import groom.backend.domain.location.dto.request.GetLocationRequest;
import groom.backend.domain.location.dto.response.GetLocationResponse;
import groom.backend.domain.location.service.spec.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 위치 정보 조회 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/locations")
@Tag(name = "Location", description = "위치 관리 API")
public class LocationController {
    private final LocationService locationService;

    @GetMapping("/current")
    @Operation(
            summary = "현재 위치 정보 수신",
            description = "FE로부터 현재 위치(경도, 위도)를 받아 처리합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "조회 성공"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청 (경도 또는 위도 누락)"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "500",
                    description = "서버 오류"
            )
    })
    public ApiResponse<GetLocationResponse> getCurrentLocation(
            @Parameter(
                    description = "경도",
                    required = true,
                    example = "127.069903"
            )
            @RequestParam("longitude") Double longitude,
            @Parameter(
                    description = "위도",
                    required = true,
                    example = "37.529546"
            )
            @RequestParam("latitude") Double latitude
    ) {
        GetLocationRequest request = GetLocationRequest.builder()
                .longitude(longitude)
                .latitude(latitude)
                .build();

        GetLocationResponse response = locationService.getCurrentLocation(request);
        return ApiResponse.success(200, "현재 위치 정보 조회 성공", response);
    }
}
