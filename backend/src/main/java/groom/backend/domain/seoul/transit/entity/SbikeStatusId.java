package groom.backend.domain.seoul.transit.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * SbikeStatus 복합 키 클래스
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SbikeStatusId implements Serializable {
    private LocalDateTime dataGetTime;
    private String sbikeSpotId;
}

