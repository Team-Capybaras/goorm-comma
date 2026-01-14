package groom.backend.domain.park.controller;

import groom.backend.common.response.ApiResponse;
import groom.backend.domain.park.dto.response.GetAllParksBasicResponse;
import groom.backend.domain.park.service.spec.ParkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 공원 정보 조회 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/parks")
@Tag(name = "Park", description = "공원 정보 조회 관리")
public class ParkController {
    private final ParkService parkService;

    @GetMapping("/all")
    @Operation(
            summary = "전체 공원 기본 정보 조회(지도에 마커 표시용)",
            description = "전체 공원의 이름, 지역코드, 경도, 위도 정보를 조회합니다."
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
    public ApiResponse<GetAllParksBasicResponse> getAllParksBasic() {
        GetAllParksBasicResponse response = parkService.getAllParksBasic();
        return ApiResponse.success(200, "전체 공원 기본 정보 조회 성공", response);
    }
}
