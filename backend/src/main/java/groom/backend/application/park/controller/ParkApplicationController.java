package groom.backend.application.park.controller;

import groom.backend.application.park.dto.response.GetAllParksResponse;
import groom.backend.application.park.dto.response.GetParkResponse;
import groom.backend.application.park.service.spec.ParkApplicationService;
import groom.backend.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 공원 정보 조회 애플리케이션 컨트롤러
 * 여러 도메인을 조합하여 공원 정보를 제공합니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/parks")
@Tag(name = "Park", description = "공원 정보 조회 관리")
public class ParkApplicationController {
    private final ParkApplicationService parkApplicationService;

    @GetMapping
    @Operation(
            summary = "공원 리스트 조회 (커서 기반 페이지네이션)",
            description = "커서 기반 페이지네이션으로 공원 리스트를 조회합니다. " +
                    "첫 페이지는 cursor를 생략하고, 다음 페이지는 이전 응답의 nextCursor 값을 사용합니다."
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
    public ApiResponse<GetAllParksResponse> getParks(
            @Parameter(
                    description = "커서 (areaCode), 첫 페이지는 생략 가능",
                    required = false,
                    example = "POI093"
            )
            @RequestParam(required = false) String cursor,
            @Parameter(
                    description = "페이지 크기 (기본값: 10, 최대값: 100)",
                    required = false,
                    example = "10"
            )
            @RequestParam(required = false) Integer size,
            @Parameter(
                    description = "현재 위치 경도",
                    required = false,
                    example = "127.069903"
            )
            @RequestParam(required = false) Double longitude,
            @Parameter(
                    description = "현재 위치 위도",
                    required = false,
                    example = "37.529546"
            )
            @RequestParam(required = false) Double latitude
    ) {
        GetAllParksResponse response = parkApplicationService.getParks(cursor, size, longitude, latitude);
        return ApiResponse.success(200, "공원 리스트 조회 성공", response);
    }

    @GetMapping("/{areaCode}")
    @Operation(
            summary = "특정 공원 조회 (공원 상세 정보 카드, 상세 페이지가 아닌 지도뷰에서 사용되는 API)",
            description = "areaCode로 특정 공원의 상세 정보를 조회합니다. 지도뷰에서 사용되는 API입니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "조회 성공"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "공원 정보를 찾을 수 없음"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "500",
                    description = "서버 오류"
            )
    })
    public ApiResponse<GetParkResponse> getParkByAreaCode(
            @Parameter(
                    description = "지역 코드 (AREA_CODE)",
                    required = true,
                    example = "POI093"
            )
            @PathVariable String areaCode,
            @Parameter(
                    description = "현재 위치 경도",
                    required = false,
                    example = "127.069903"
            )
            @RequestParam(required = false) Double longitude,
            @Parameter(
                    description = "현재 위치 위도",
                    required = false,
                    example = "37.529546"
            )
            @RequestParam(required = false) Double latitude
    ) {
        GetParkResponse response = parkApplicationService.getParkByAreaCode(areaCode, longitude, latitude);
        return ApiResponse.success(200, "공원 조회 성공", response);
    }
}

