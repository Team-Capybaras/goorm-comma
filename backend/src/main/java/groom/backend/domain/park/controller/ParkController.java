package groom.backend.domain.park.controller;

import groom.backend.common.response.ApiResponse;
import groom.backend.domain.park.dto.response.GetAllParksResponse;
import groom.backend.domain.park.service.spec.ParkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 공원 정보 조회 컨트롤러
 * 커서 기반 페이지네이션으로 공원 리스트를 조회합니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/parks")
@Tag(name = "Park", description = "공원 정보 조회 관리")
public class ParkController {
    private final ParkService parkService;

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
            @RequestParam(required = false) Integer size
    ) {
        GetAllParksResponse response = parkService.getParks(cursor, size);
        return ApiResponse.success(200, "공원 리스트 조회 성공", response);
    }
}

