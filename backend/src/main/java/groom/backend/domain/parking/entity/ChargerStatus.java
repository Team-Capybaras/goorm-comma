package groom.backend.domain.parking.entity;

import groom.backend.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 충전기 상태 엔티티
 * 테이블: charger_status
 * 
 * 충전기의 실시간 상태 정보를 저장합니다.
 * 충전기의 사용 가능 여부 및 상태를 추적합니다.
 */
@Entity
@Table(name = "charger_status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChargerStatus extends BaseEntity {
    
    /**
     * 충전기 상태 키 (PK)
     */
    @Id
    @Column(name = "charter_stat_key", nullable = false)
    private Integer chargerStatKey;

    /**
     * 충전기 상태 (사용가능, 사용중 등)
     */
    @Column(name = "charger_status", length = 20)
    private String chargerStatus;

    /**
     * 상태 업데이트 시간
     */
    @Column(name = "status_updated")
    private LocalDateTime statusUpdated;

    /**
     * 상태 타임스탬프
     */
    @Column(name = "status_timestamp")
    private LocalDateTime statusTimestamp;

    /**
     * 데이터 수집 시간
     */
    @Column(name = "data_get_time")
    private LocalDateTime dataGetTime;

    /**
     * 충전기 ID (FK -> charger_detail.charger_id)
     */
    @Column(name = "charger_id")
    private Integer chargerId;

    /**
     * 충전소 ID (FK -> charger_detail.station_id)
     */
    @Column(name = "station_id", length = 20)
    private String stationId;

    /**
     * 충전기 상세 정보 (FK)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "charger_id", referencedColumnName = "charger_id", insertable = false, updatable = false),
            @JoinColumn(name = "station_id", referencedColumnName = "station_id", insertable = false, updatable = false)
    })
    private ChargerDetail chargerDetail;
}

