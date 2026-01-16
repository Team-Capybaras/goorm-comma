package groom.backend.application.park.controller;

import groom.backend.application.park.dto.response.GetAllParksResponse;
import groom.backend.application.park.service.spec.ParkRecommendService;
import groom.backend.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/parks")
@Tag(
        name = "Park",
        description = "공원 정보 조회 및 추천 API"
)
public class ParkRecommendController {

  private final ParkRecommendService parkRecommendService;

  @Operation(
          summary = "혼잡도·거리 기반 공원 추천",
          description =
                  """
                  사용자의 현재 위치를 기준으로
                  혼잡도가 낮고, 거리가 가까운 공원을 종합적으로 판단하여
                  우선순위가 높은 공원을 최대 5개 추천합니다.

                  - 본 API는 목록 조회가 아닌 추천 API입니다.
                  - 반환되는 공원 수는 최대 5개로 고정됩니다.
                  - 커서 기반 페이지네이션을 사용하지 않습니다.
                  """
  )
  @io.swagger.v3.oas.annotations.responses.ApiResponse(
          responseCode = "200",
          description = "추천 공원 조회 성공",
          content = @Content(
                  schema = @Schema(implementation = GetAllParksResponse.class)
          )
  )
  @io.swagger.v3.oas.annotations.responses.ApiResponse(
          responseCode = "400",
          description = "필수 위치 정보 누락",
          content = @Content
  )
  @GetMapping("/recommend")
  public ApiResponse<GetAllParksResponse> getRecommendedParks(
          @Parameter(
                  description = "현재 위치 경도 (추천을 위해 필수)",
                  required = true,
                  example = "127.069903"
          )
          @RequestParam Double longitude,

          @Parameter(
                  description = "현재 위치 위도 (추천을 위해 필수)",
                  required = true,
                  example = "37.529546"
          )
          @RequestParam Double latitude
  ) {
    GetAllParksResponse response =
            parkRecommendService.recommendTop5Parks(longitude, latitude);

    return ApiResponse.success(
            200,
            "해당 위치 기준 추천 공원입니다.",
            response
    );
  }
}
