package groom.backend.domain.parking.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * ParkingLotStatus 복합 키 클래스
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ParkingLotStatusId implements Serializable {
    private LocalDateTime dataGetTime;
    private Long prkCode;
}

