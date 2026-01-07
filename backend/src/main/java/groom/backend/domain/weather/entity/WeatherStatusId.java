package groom.backend.domain.weather.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * WeatherStatus 복합 키 클래스
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class WeatherStatusId implements Serializable {
    private String areaCode;
    private LocalDateTime dataGetTime;
}

