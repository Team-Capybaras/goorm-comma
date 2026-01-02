package groom.backend.domain.seoul.population.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * PredPopStatus 복합 키 클래스
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PredPopStatusId implements Serializable {
    private LocalDateTime dataGetTime;
    private String areaCode;
}

