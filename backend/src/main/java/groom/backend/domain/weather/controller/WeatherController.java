package groom.backend.domain.weather.controller;

import groom.backend.domain.weather.dto.response.WeatherStatusResponse;
import groom.backend.domain.weather.service.spec.WeatherCurrentStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/weather")
@Tag(
        name = "Weather",
        description = "날씨 정보 조회 API"
)
public class WeatherController {

  private final WeatherCurrentStatusService weatherCurrentStatusService;

  @Operation(
          summary = "현재 날씨 조회",
          description = """
          지역 코드(area_code)를 기준으로 현재 날씨 및 대기 환경 정보를 조회한다.
          
          - 데이터 소스: 서울시 Open API
          - 조회 결과는 실시간 또는 최신 수집 기준 데이터이다.
          """
  )
  @ApiResponses({
          @ApiResponse(
                  responseCode = "200",
                  description = "현재 날씨 조회 성공",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = WeatherStatusResponse.class)
                  )
          ),
          @ApiResponse(
                  responseCode = "400",
                  description = "잘못된 요청 (area_code 누락 또는 형식 오류)"
          ),
//          @ApiResponse(
//                  responseCode = "404",
//                  description = "해당 지역의 날씨 정보를 찾을 수 없음"
//          ),
          @ApiResponse(
                  responseCode = "500",
                  description = "서버 내부 오류"
          )
  })
  @GetMapping("/current")
  public WeatherStatusResponse getCurrentWeatherStatus(
          @Parameter(
                  name = "area_code",
                  description = "지역 코드 (예: 서울시 행정구역 코드)",
                  example = "11110",
                  required = true
          )
          @RequestParam("area_code") String areaCode
  ) {
    return weatherCurrentStatusService.getWeatherCurrentStatus(areaCode);
  }
}
