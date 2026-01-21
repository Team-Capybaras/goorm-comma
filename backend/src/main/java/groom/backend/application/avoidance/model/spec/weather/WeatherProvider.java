package groom.backend.application.avoidance.model.spec.weather;

import groom.backend.application.avoidance.model.context.WeatherContext;
import groom.backend.domain.avoidance.enums.Weekday;

import java.util.Optional;

public interface WeatherProvider {
  Optional<WeatherContext> getWeather(String areaCode, int hour, Weekday weekday);
}

