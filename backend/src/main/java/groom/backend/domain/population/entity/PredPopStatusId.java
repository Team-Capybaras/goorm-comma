package groom.backend.domain.population.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * PredPopStatus 복합 키 클래스
 * dataGetTime, areaCode, forecastTime을 조합하여 각 예보 시간대를 구분합니다.
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PredPopStatusId implements Serializable {
    private LocalDateTime dataGetTime;
    private String areaCode;
    private LocalDateTime forecastTime;
}

