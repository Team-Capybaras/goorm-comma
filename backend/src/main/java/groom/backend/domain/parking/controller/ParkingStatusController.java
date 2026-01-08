package groom.backend.domain.parking.controller;

import groom.backend.common.response.ApiResponse;
import groom.backend.domain.parking.dto.response.ParkingStatusResponse;
import groom.backend.domain.parking.service.spec.ParkingStatusService;
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
 * 편의시설 정보 조회 컨트롤러
 * 주차장, 전기차 충전소 API를 제공합니다.
 */
@RestController
@Tag(name = "parking", description = "편의시설 정보 조회 관리")
@RequiredArgsConstructor
@RequestMapping("/v1/parking")
public class ParkingStatusController {
  private final ParkingStatusService parkingStatusService;

  @GetMapping
  @Operation(
          summary = "대중교통 정보 조회",
          description = "지역 코드로 지하철역, 버스 정류장, 공유 자전거 정보를 조회합니다."
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
  public ApiResponse<ParkingStatusResponse> getParkingStatus(
          @Parameter(description = "지역 코드 (AREA_CODE)", required = true, example = "POI093")
          @RequestParam(name = "area_code") String areaCode) {
    ParkingStatusResponse response = parkingStatusService.getParkingStatus(areaCode);
    return ApiResponse.success(200, "편의 시설 조회 성공", response);
  }
}
