package groom.backend.domain.park.controller;

import groom.backend.common.response.ApiResponse;
import groom.backend.domain.park.dto.response.GetAllParksResponse;
import groom.backend.domain.park.service.spec.ParkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 공원 정보 조회 컨트롤러
 * 전체 공원 리스트를 조회합니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/parks")
@Tag(name = "Park", description = "공원 정보 조회 관리")
public class ParkController {
    private final ParkService parkService;

    @GetMapping
    @Operation(
            summary = "전체 공원 리스트 조회",
            description = "모든 공원 리스트를 한 번에 조회합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "조회 성공"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "500",
                    description = "서버 오류"
            )
    })
    public ApiResponse<GetAllParksResponse> getParks() {
        GetAllParksResponse response = parkService.getAllParks();
        return ApiResponse.success(200, "전체 공원 리스트 조회 성공", response);
    }
}

