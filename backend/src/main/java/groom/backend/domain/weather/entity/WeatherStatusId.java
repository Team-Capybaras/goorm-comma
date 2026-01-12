package groom.backend.domain.weather.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * WeatherStatus 복합 키 클래스
 *
 * TODO: 성능 개선을 위한 DB 인덱스 튜닝
 * CREATE INDEX idx_weather_status_latest
 * ON weather_status (area_code, data_get_time DESC);
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class WeatherStatusId implements Serializable {
    private String areaCode;
    private LocalDateTime dataGetTime;
}

