package groom.backend.domain.parking.entity;

import groom.backend.common.entity.BaseEntity;
import groom.backend.domain.park.entity.Park;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * 충전소 정보 엔티티
 * 테이블: charger_station
 * 
 * 지역 주변 전기차 충전소의 기본 정보를 저장합니다.
 * 충전소의 정적 정보(위치, 사용 시간 등)를 관리합니다.
 */
@Entity
@Table(name = "charger_station")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChargerStation extends BaseEntity {
    
    /**
     * 충전소 ID (PK)
     */
    @Id
    @Column(name = "station_id", length = 20)
    private String stationId;

    /**
     * 충전소명
     */
    @Column(name = "station_name", length = 50)
    private String stationName;

    /**
     * 충전소 주소
     */
    @Column(name = "station_addr", length = 255)
    private String stationAddr;

    /**
     * 경도
     */
    @Column(name = "station_x", precision = 13, scale = 10)
    private BigDecimal stationX;

    /**
     * 위도
     */
    @Column(name = "station_y", precision = 13, scale = 10)
    private BigDecimal stationY;

    /**
     * 사용 시간
     */
    @Column(name = "station_usetime", length = 50)
    private String stationUsetime;

    /**
     * 주차 요금 여부
     */
    @Column(name = "station_parkpay")
    private Boolean stationParkpay;

    /**
     * 제한 상세
     */
    @Column(name = "station_limit_detail", length = 50)
    private String stationLimitDetail;

    /**
     * 종류 상세
     */
    @Column(name = "station_kind_detail", length = 20)
    private String stationKindDetail;

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

