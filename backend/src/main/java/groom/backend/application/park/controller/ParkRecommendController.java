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
                  입력으로 받은 위치를 기준으로
                  혼잡도가 낮고, 거리가 가까운 공원을 종합적으로 판단하여
                  우선순위가 높은 공원을 최대 5개 추천합니다.
                  
                  대체지 추천과 실시간 사용자 주변 공원 추천 시 사용 가능합니다.
                  
                  대체지 추천 시 공원의 areacode가 필수이며, 사용자 주변 공원 추천 시 거리제한이 필수입니다.
                  두 인자가 없을 경우 여유 또는 보통인 공원을 거리순으로 정렬하며 갯수 제한이 없습니다.

                  - 반환되는 공원 수는 최대 5개로 고정됩니다.
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
          @RequestParam(name="longitude") Double longitude,

          @Parameter(
                  description = "현재 위치 위도 (추천을 위해 필수)",
                  required = true,
                  example = "37.529546"
          )
          @RequestParam(name="latitude") Double latitude,

          @Parameter(
                  description = "반경 n km 이내 공원만 보여줍니다. 메인페이지 실시간 추천 시 사용하십시오.",
                  required = false,
                  example = "5"
          )
          @RequestParam(name="limit_distance", required = false) Integer limitDistance,

          @Parameter(
                  description = "기준이 되는 공원입니다. 대체지 추천 시 사용하십시오.",
                  required = false,
                  example = "5"
          )
          @RequestParam(name="base_area_code", required = false) String baseAreaCode
  ) {
    if ((limitDistance == null) == (baseAreaCode == null)) {
      return ApiResponse.error(
              400,
              "limit_distance 또는 base_area_code 중 하나만 반드시 입력해야 합니다.",
              null
      );
    }

    GetAllParksResponse response;

    if (limitDistance == null) {
      response = parkRecommendService.recommendTop5Parks(longitude, latitude, baseAreaCode);
    } else {
      response = parkRecommendService.recommendTop5Parks(longitude, latitude, limitDistance);
    }

    return ApiResponse.success(
            200,
            "해당 위치 기준 추천 공원입니다.",
            response
    );
  }
}
