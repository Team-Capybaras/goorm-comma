package groom.backend.domain.seoul.transit.entity;

import groom.backend.common.entity.BaseEntity;
import groom.backend.domain.seoul.park.entity.Park;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * 지하철역 정보 엔티티
 * 테이블: subway_station
 * 
 * 지역 주변 지하철역의 기본 정보를 저장합니다.
 * 지하철역의 정적 정보(위치, 노선 등)를 관리합니다.
 */
@Entity
@Table(name = "subway_station")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubwayStation extends BaseEntity {
    
    /**
     * 지하철역 ID (PK)
     */
    @Id
    @Column(name = "sub_id", nullable = false)
    private Integer subId;

    /**
     * 지하철역명
     */
    @Column(name = "sub_stn_name", length = 20)
    private String subStnName;

    /**
     * 지하철 노선
     */
    @Column(name = "sub_stn_line", length = 20)
    private String subStnLine;

    /**
     * 주소
     */
    @Column(name = "addr", length = 50)
    private String addr;

    /**
     * 도로명 주소
     */
    @Column(name = "road_addr", length = 50)
    private String roadAddr;

    /**
     * 경도
     */
    @Column(name = "sub_stn_x", precision = 13, scale = 10)
    private BigDecimal subStnX;

    /**
     * 위도
     */
    @Column(name = "sub_stn_y", precision = 13, scale = 10)
    private BigDecimal subStnY;

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

