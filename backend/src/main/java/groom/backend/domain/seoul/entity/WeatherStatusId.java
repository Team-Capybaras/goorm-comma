package groom.backend.domain.seoul.entity;

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
    private LocalDateTime dataGetTime;
    private String areaCode;
}

