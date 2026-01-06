package groom.backend.domain.weather.service.impl;

import groom.backend.domain.weather.repository.WeatherStatusRepository;
import groom.backend.domain.weather.service.spec.WeatherCurrentStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class WeatherCurrentStatusServiceImpl implements WeatherCurrentStatusService {
  private final WeatherStatusRepository weatherStatusRepository;

  @Override
  public void getWeatherCurrentStatus() {
    // 가장 최근 수집한 데이터 반환
  }
}
