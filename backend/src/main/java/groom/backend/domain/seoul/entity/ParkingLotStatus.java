package groom.backend.domain.seoul.entity;

import groom.backend.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 주차장 현황 엔티티
 * 테이블: parking_lot_status
 */
@Entity
@Table(name = "parking_lot_status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(ParkingLotStatusId.class)
public class ParkingLotStatus extends BaseEntity {
    
    /**
     * 데이터 수집 시간 (PK)
     */
    @Id
    @Column(name = "data_get_time")
    private LocalDateTime dataGetTime;

    /**
     * 주차장 코드 (PK, FK -> parking_lot.prk_code)
     */
    @Id
    @Column(name = "prk_code")
    private Long prkCode;

    /**
     * 현재 주차 대수
     */
    @Column(name = "current_prk_cnt")
    private Integer currentPrkCnt;

    /**
     * 현재 주차 시간
     */
    @Column(name = "current_prk_time")
    private LocalDateTime currentPrkTime;

    /**
     * 주차장 정보 (FK)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prk_code", insertable = false, updatable = false)
    private ParkingLot parkingLot;
}

