package groom.backend.application.avoidance.controller;

import groom.backend.application.avoidance.dto.response.CongestionRecommendResponse;
import groom.backend.application.avoidance.service.spec.CongestionAvoidanceService;
import groom.backend.application.avoidance.service.spec.ParkStatisticsService;
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
@RequestMapping("/v1/avoidance")
@Tag(
        name = "Congestion Avoidance",
        description = "공원 혼잡도 통계 및 혼잡 회피 추천 기능을 제공합니다."
)
public class CongestionAvoidanceController {

  private final CongestionAvoidanceService congestionAvoidanceService;
  private final ParkStatisticsService parkStatisticsService;

  /**
   * 혼잡도 통계 제공 및 추천 시간대 정보 조회
   */
  @Operation(
          summary = "공원 혼잡도 통계 조회",
          description = """
          특정 공원의 요일 및 시간(hour) 단위 혼잡도 통계 데이터를 조회합니다.
          
          - 통계 데이터는 차트 렌더링 용도로 사용됩니다.
          - 데이터가 충분하지 않은 경우 일부 요일 또는 시간대가 존재하지 않을 수 있습니다.
          - statistics 필드는 항상 반환되며, 데이터가 없을 경우 빈 배열([])입니다.
          """
  )
  @io.swagger.v3.oas.annotations.responses.ApiResponse(
          responseCode = "200",
          description = "공원 혼잡도 통계 조회 성공",
          content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CongestionRecommendResponse.class)
          )
  )
  @io.swagger.v3.oas.annotations.responses.ApiResponse(
          responseCode = "400",
          description = "잘못된 요청 (area_code 누락 또는 형식 오류)"
  )
  @io.swagger.v3.oas.annotations.responses.ApiResponse(
          responseCode = "404",
          description = "해당 지역 코드에 대한 공원 정보가 존재하지 않음"
  )
  @GetMapping("/statistics")
  public ApiResponse<CongestionRecommendResponse> statistics(
          @Parameter(
                  name = "area_code",
                  description = "공원을 식별하는 지역 코드",
                  required = true,
                  example = "A001"
          )
          @RequestParam(name = "area_code") String areaCode
  ) {
    CongestionRecommendResponse response = congestionAvoidanceService.getParkStatistics(areaCode);
    return ApiResponse.success(200, "공원 혼잡도 통계 조회 성공", response);
  }

  /**
   * test 코드.
   * 트리거 기능 테스트 api
   * @return
   */
  @GetMapping("/aggregate")
  public ApiResponse<String> aggregateTriger() {
    parkStatisticsService.aggregateAll();
    return ApiResponse.success(200, "triggered. aggregate start", "");
  }

  // TODO: 시간대 추천 기능
}