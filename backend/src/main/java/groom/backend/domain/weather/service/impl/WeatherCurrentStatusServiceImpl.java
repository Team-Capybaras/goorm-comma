package groom.backend.domain.weather.service.impl;

import groom.backend.common.exception.BusinessException;
import groom.backend.common.exception.ErrorCode;
import groom.backend.domain.park.repository.ParkRepository;
import groom.backend.domain.weather.dto.response.WeatherStatusResponse;
import groom.backend.domain.weather.entity.WeatherStatus;
import groom.backend.domain.weather.mapper.WeatherMapper;
import groom.backend.domain.weather.repository.WeatherStatusRepository;
import groom.backend.domain.weather.service.spec.WeatherCurrentStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherCurrentStatusServiceImpl implements WeatherCurrentStatusService {

  private final WeatherStatusRepository weatherStatusRepository;
  private final WeatherMapper weatherMapper;

  @Override
  public WeatherStatusResponse getWeatherCurrentStatus(String areaCode) {

    log.debug("현재 날씨 조회 요청: areaCode={}", areaCode);

    /**
     * 입력 자체에 대한 AREA CODE의 검증...
     */
    WeatherStatus weatherStatus =
            weatherStatusRepository
                    .findTopByAreaCodeOrderByDataGetTimeDesc(areaCode)
                    .orElseThrow(() -> new BusinessException(
                            ErrorCode.WEATHER_NOT_FOUND
                    ));

    log.debug(
            "최신 날씨 데이터 조회 성공: areaCode={}, dataGetTime={}",
            areaCode,
            weatherStatus.getDataGetTime()
    );

    return weatherMapper.toWeatherDto(weatherStatus);
  }
}
