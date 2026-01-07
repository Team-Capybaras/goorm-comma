package groom.backend.domain.weather.controller;

import groom.backend.domain.weather.dto.response.WeatherStatusResponse;
import groom.backend.domain.weather.service.spec.WeatherCurrentStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/weather")
public class WeatherController {
  private final WeatherCurrentStatusService weatherCurrentStatusService;

  @GetMapping("/current")
  public WeatherStatusResponse getCurrentWeatherStatus(@RequestParam("area_code") String areaCode) {
    return weatherCurrentStatusService.getWeatherCurrentStatus(areaCode);
  }
}
