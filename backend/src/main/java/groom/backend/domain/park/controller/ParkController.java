package groom.backend.domain.park.controller;

import groom.backend.common.response.ApiResponse;
import groom.backend.domain.park.dto.response.GetAllParksBasicResponse;
import groom.backend.domain.park.dto.response.GetParkSearchResponse;
import groom.backend.domain.park.service.spec.ParkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/search")
    @Operation(
            summary = "공원명으로 공원 검색",
            description = "검색어를 입력하여 공원명에 검색어가 포함된 공원 중 첫 번째 공원을 조회합니다. " +
                    "areaCode와 공원명만 반환하여 상세 페이지로 이동할 수 있도록 합니다. " +
                    "예: '뚝섬'을 입력하면 '뚝섬한강공원' 등이 검색되어 첫 번째 결과가 반환됩니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "조회 성공"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "공원을 찾을 수 없음"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청 (search_keyword 누락)"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "500",
                    description = "서버 오류"
            )
    })
    public ApiResponse<GetParkSearchResponse> searchParkByAreaName(
            @Parameter(
                    description = "검색어 (공원명에 포함된 공원 조회)",
                    required = true,
                    example = "뚝섬"
            )
            @RequestParam(name = "search_keyword") String searchKeyword
    ) {
        GetParkSearchResponse response = parkService.searchParkByAreaName(searchKeyword);
        
        if (response == null) {
            return ApiResponse.success(404, "공원을 찾을 수 없습니다", null);
        }
        
        return ApiResponse.success(200, "공원 검색 성공", response);
    }
}
