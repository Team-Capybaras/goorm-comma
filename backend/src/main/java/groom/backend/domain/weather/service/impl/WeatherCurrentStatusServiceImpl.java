package groom.backend.domain.weather.service.impl;

import groom.backend.domain.weather.dto.response.WeatherStatusResponse;
import groom.backend.domain.weather.entity.WeatherStatus;
import groom.backend.domain.weather.mapper.WeatherMapper;
import groom.backend.domain.weather.repository.WeatherStatusRepository;
import groom.backend.domain.weather.service.spec.WeatherCurrentStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class WeatherCurrentStatusServiceImpl implements WeatherCurrentStatusService {
  private final WeatherStatusRepository weatherStatusRepository;
  private final WeatherMapper weatherMapper;

  @Override
  public WeatherStatusResponse getWeatherCurrentStatus(String areaCode) {
    // 특정 areaCode에 대해, dataGetTime이 가장 최신인 데이터 반환
    // TODO: 데이터가 없을 경우 NoDataException 출력
    WeatherStatus weatherStatus = weatherStatusRepository.findTopByAreaCodeOrderByDataGetTimeDesc(areaCode).orElse(null);

    return weatherMapper.toWeatherDto(weatherStatus);
  }
}
