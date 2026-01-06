package groom.backend.domain.weather.service.impl;

import groom.backend.domain.weather.dto.response.WeatherStatusResponse;
import groom.backend.domain.weather.repository.WeatherStatusRepository;
import groom.backend.domain.weather.service.spec.WeatherCurrentStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class WeatherCurrentStatusServiceImpl implements WeatherCurrentStatusService {
  private final WeatherStatusRepository weatherStatusRepository;

  @Override
  public WeatherStatusResponse getWeatherCurrentStatus() {
    // 특정 areaCode에 대해, dataGetTime이 가장 최신인 데이터 반환
    return null;
  }
}
