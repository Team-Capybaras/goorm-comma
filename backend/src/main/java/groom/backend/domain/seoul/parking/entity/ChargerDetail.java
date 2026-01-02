package groom.backend.domain.seoul.parking.entity;

import groom.backend.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 충전기 상세 정보 엔티티
 * 테이블: charger_detail
 * 
 * 충전소의 충전기 상세 정보를 저장합니다.
 * 복합키(charger_id, station_id)를 사용하여 충전소별 여러 충전기 정보를 저장합니다.
 */
@Entity
@Table(name = "charger_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(ChargerDetailId.class)
public class ChargerDetail extends BaseEntity {
    
    /**
     * 충전기 ID (PK)
     */
    @Id
    @Column(name = "charger_id")
    private Integer chargerId;

    /**
     * 충전소 ID (PK, FK -> charger_station.station_id)
     */
    @Id
    @Column(name = "station_id", length = 20)
    private String stationId;

    /**
     * 충전기 유형 (AC완속, DC급속 등)
     */
    @Column(name = "charger_type", length = 20)
    private String chargerType;

    /**
     * 충전기 업데이트 시간
     */
    @Column(name = "charger_updated")
    private LocalDateTime chargerUpdated;

    /**
     * 충전기 타임스탬프
     */
    @Column(name = "charger_timestmap")
    private LocalDateTime chargerTimestamp;

    /**
     * 출력 (kW)
     */
    @Column(name = "output")
    private Integer output;

    /**
     * 충전 방식 (단독, 동시 등)
     */
    @Column(name = "method", length = 20)
    private String method;

    /**
     * 충전소 정보 (FK)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id", insertable = false, updatable = false)
    private ChargerStation chargerStation;
}

