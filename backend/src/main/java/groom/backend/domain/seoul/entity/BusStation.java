package groom.backend.domain.seoul.entity;

import groom.backend.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * 버스 정류장 정보 엔티티
 * 테이블: bus_station
 */
@Entity
@Table(name = "bus_station")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusStation extends BaseEntity {
    
    /**
     * 버스 정류장 ID (PK)
     */
    @Id
    @Column(name = "bus_stn_id")
    private Integer busStnId;

    /**
     * 버스 ARS ID
     */
    @Column(name = "bus_ars_id")
    private Integer busArsId;

    /**
     * 버스 정류장명
     */
    @Column(name = "bus_stn_name")
    private Integer busStnName;

    /**
     * 경도
     */
    @Column(name = "bus_stn_x", precision = 13, scale = 10)
    private BigDecimal busStnX;

    /**
     * 위도
     */
    @Column(name = "bus_stn_y", precision = 13, scale = 10)
    private BigDecimal busStnY;

    /**
     * 지역 코드 (FK -> park.area_code)
     */
    @Column(name = "area_code", length = 20, nullable = false)
    private String areaCode;

    /**
     * 공원 정보 (FK)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_code", insertable = false, updatable = false)
    private Park park;
}

