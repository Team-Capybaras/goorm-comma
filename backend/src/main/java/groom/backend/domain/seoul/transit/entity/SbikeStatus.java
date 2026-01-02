package groom.backend.domain.seoul.transit.entity;

import groom.backend.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 공유 자전거 현황 엔티티
 * 테이블: sbike_status
 * 
 * 공유 자전거의 실시간 현황 정보를 저장합니다.
 * 복합키(data_get_time, sbike_spot_id)를 사용하여 시간별 공유 자전거 현황을 추적합니다.
 */
@Entity
@Table(name = "sbike_status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(SbikeStatusId.class)
public class SbikeStatus extends BaseEntity {
    
    /**
     * 데이터 수집 시간 (PK)
     */
    @Id
    @Column(name = "data_get_time")
    private LocalDateTime dataGetTime;

    /**
     * 공유 자전거 스팟 ID (PK, FK -> sbike.sbike_spot_id)
     */
    @Id
    @Column(name = "sbike_spot_id", length = 20)
    private String sbikeSpotId;

    /**
     * 공유 자전거 주차 비율
     */
    @Column(name = "sbike_parking_per")
    private Integer sbikeParkingPer;

    /**
     * 공유 자전거 주차 대수
     */
    @Column(name = "sbike_parking_cnt")
    private Integer sbikeParkingCnt;

    /**
     * 공유 자전거 정보 (FK)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sbike_spot_id", insertable = false, updatable = false)
    private Sbike sbike;
}

