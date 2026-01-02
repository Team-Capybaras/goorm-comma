package groom.backend.domain.seoul.transit.entity;

import groom.backend.common.entity.BaseEntity;
import groom.backend.domain.seoul.park.entity.Park;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * 공유 자전거 정보 엔티티
 * 테이블: sbike
 * 
 * 지역 주변 공유 자전거의 기본 정보를 저장합니다.
 * 공유 자전거의 정적 정보(위치, 수용 대수)를 관리합니다.
 */
@Entity
@Table(name = "sbike")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sbike extends BaseEntity {
    
    /**
     * 공유 자전거 스팟 ID (PK)
     */
    @Id
    @Column(name = "sbike_spot_id", length = 20)
    private String sbikeSpotId;

    /**
     * 공유 자전거 스팟명
     */
    @Column(name = "sbike_spot_name", length = 50)
    private String sbikeSpotName;

    /**
     * 공유 자전거 수용 대수
     */
    @Column(name = "sbike_capacity")
    private Integer sbikeCapacity;

    /**
     * 경도
     */
    @Column(name = "sbike_x", precision = 13, scale = 10)
    private BigDecimal sbikeX;

    /**
     * 위도
     */
    @Column(name = "sbike_y", precision = 13, scale = 10)
    private BigDecimal sbikeY;

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

