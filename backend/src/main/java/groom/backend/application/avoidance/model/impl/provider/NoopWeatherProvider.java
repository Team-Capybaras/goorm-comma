package groom.backend.application.avoidance.model.impl.provider;

import groom.backend.application.avoidance.model.context.WeatherContext;
import groom.backend.application.avoidance.model.spec.provider.WeatherProvider;
import groom.backend.domain.avoidance.enums.Weekday;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Weather 정보 제공자
 * 정보 제공이 불가능한 경우 사용
 */
@Primary
@Component
public class NoopWeatherProvider implements WeatherProvider {

  @Override
  public Optional<WeatherContext> getWeather(String areaCode, int hour, Weekday weekday) {
    return Optional.empty();
  }
}
