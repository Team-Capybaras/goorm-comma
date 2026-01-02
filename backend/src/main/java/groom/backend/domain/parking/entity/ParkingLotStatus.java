package groom.backend.domain.parking.entity;

import groom.backend.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 주차장 현황 엔티티
 * 테이블: parking_lot_status
 * 
 * 주차장의 실시간 현황 정보를 저장합니다.
 * 복합키(data_get_time, prk_code)를 사용하여 시간별 주차장 현황을 추적합니다.
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

