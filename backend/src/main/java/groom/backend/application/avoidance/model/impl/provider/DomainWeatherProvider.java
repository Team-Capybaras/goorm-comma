package groom.backend.application.avoidance.model.impl.provider;

import groom.backend.application.avoidance.model.context.WeatherContext;
import groom.backend.application.avoidance.model.spec.provider.WeatherProvider;
import groom.backend.domain.avoidance.enums.Weekday;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Weather 정보 제공자
 * Weather 도메인으로부터 정보를 받아온다.
 * TODO : 미구현 강수확률 조회 기능 구현
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DomainWeatherProvider implements WeatherProvider {

//  private final WeatherQueryService weatherQueryService;

  @Override
  public Optional<WeatherContext> getWeather(String areaCode, int hour, Weekday weekday) {
    return Optional.empty();
//    try {
//      Integer rainProb = weatherQueryService.getRainProbability(areaCode, hour);
//
//      if (rainProb == null) {
//        return Optional.empty();
//      }
//
//      return Optional.of(
//              WeatherContext.builder()
//                      .rainProbability(rainProb)
//                      .build()
//      );
//
//    } catch (Exception e) {
//      log.warn(
//              "[WeatherProvider] weather unavailable, fallback to no-weather mode. areaCode={}",
//              areaCode, e
//      );
//      return Optional.empty();
//    }
  }
}
