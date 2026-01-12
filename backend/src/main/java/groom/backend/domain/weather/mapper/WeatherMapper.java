package groom.backend.domain.weather.mapper;

import groom.backend.domain.weather.dto.response.WeatherStatusResponse;
import groom.backend.domain.weather.entity.WeatherStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WeatherMapper {

  public WeatherStatusResponse toWeatherDto(WeatherStatus weatherStatus) {
        if (weatherStatus == null) {
          return null;
        }

        return WeatherStatusResponse.builder()
                .dataGetTime(weatherStatus.getDataGetTime())
          .weatherTime(weatherStatus.getWeatherTime())
          .temp(weatherStatus.getTemp())
          .sensibleTemp(weatherStatus.getSensibleTemp())
          .humidity(weatherStatus.getHumidity())
          .windDirct(weatherStatus.getWindDirct())
          .windSpd(weatherStatus.getWindSpd())
          .rainChance(weatherStatus.getRainChance())
          .precipitation(weatherStatus.getPrecipitation())
          .precptType(weatherStatus.getPrecptType())
          .precptMsg(weatherStatus.getPrecptMsg())
          .uvIndexLevel(weatherStatus.getUvIndexLevel())
          .uvIndex(weatherStatus.getUvIndex())
          .pm25Index(weatherStatus.getPm25Index())
          .pm25(weatherStatus.getPm25())
          .pm10Index(weatherStatus.getPm10Index())
          .pm10(weatherStatus.getPm10())
          .airIndex(weatherStatus.getAirIndex())
          .airIndexLevel(weatherStatus.getAirIndexLevel())
          .airIndexMain(weatherStatus.getAirIndexMain())
          .airMsg(weatherStatus.getAirMsg())
          .build();
  }
}
