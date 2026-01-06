package groom.backend.domain.weather.service.spec;

import groom.backend.domain.weather.dto.response.WeatherStatusResponse;

/**
 * 현재 시간 기준 일기예보 및 날씨를 제공하는 서비스
 */
public interface WeatherCurrentStatusService {
  public WeatherStatusResponse getWeatherCurrentStatus();
}
