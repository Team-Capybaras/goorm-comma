package groom.backend.domain.parking.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * ParkingLotStatus 복합 키 클래스
 *
 * TODO: 성능 개선을 위한 DB 인덱스 튜닝
 * CREATE INDEX idx_parking_lot_status_latest
 * ON parking_lot_status (prk_code, data_get_time DESC);
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ParkingLotStatusId implements Serializable {
    private LocalDateTime dataGetTime;
    private Long prkCode;
}

