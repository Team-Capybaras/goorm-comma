package groom.backend.application.avoidance.controller;

import groom.backend.application.avoidance.dto.response.ParkStatisticsResponse;
import groom.backend.application.avoidance.dto.response.WeekdayAggregateResponse;
import groom.backend.application.avoidance.service.spec.ParkStatisticsService;
import groom.backend.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/avoidance")
public class CongestionAvoidanceController {
  private final ParkStatisticsService parkStatisticsService;

  // 혼잡도 통계 제공 및 추천 시간대 텍스트
  @GetMapping("/statistics")
  public ApiResponse<ParkStatisticsResponse> statistics(@RequestParam String areaCode) {
    ParkStatisticsResponse response = parkStatisticsService.getParkStatistics(areaCode);
    return ApiResponse.success(200, "공원 혼잡도 통계 조회 성공", response);
  }


  // TODO: 시간대 추천 기능

}
