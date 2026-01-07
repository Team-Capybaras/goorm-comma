package groom.backend.domain.parking.entity;

import groom.backend.common.entity.BaseEntity;
import groom.backend.domain.park.entity.Park;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * 주차장 정보 엔티티
 * 테이블: parking_lot
 * 
 * 지역 주변 주차장의 기본 정보를 저장합니다.
 * 주차장의 정적 정보(위치, 요금, 수용 대수 등)를 관리합니다.
 */
@Entity
@Table(name = "parking_lot")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkingLot extends BaseEntity {
    
    /**
     * 주차장 코드 (PK)
     */
    @Id
    @Column(name = "prk_code")
    private Long prkCode;

    /**
     * 주차장명
     */
    @Column(name = "prk_name", length = 50)
    private String prkName;

    /**
     * 주차장 유형 (BP: 건물부설, NW: 노상)
     */
    @Column(name = "prk_type", length = 20)
    private String prkType;

    /**
     * 수용 가능 대수
     */
    @Column(name = "capacity")
    private Integer capacity;

    /**
     * 현재 정보 여부
     */
    @Column(name = "current_info_yn")
    private Boolean currentInfoYn;

    /**
     * 유료 여부
     */
    @Column(name = "pay_yn")
    private Boolean payYn;

    /**
     * 기본 요금
     */
    @Column(name = "rates")
    private Integer rates;

    /**
     * 기본 시간 (분)
     */
    @Column(name = "time_rates")
    private Integer timeRates;

    /**
     * 추가 요금
     */
    @Column(name = "add_rates")
    private Integer addRates;

    /**
     * 추가 시간 (분)
     */
    @Column(name = "add_time_rates")
    private Integer addTimeRates;

    /**
     * 주소
     */
    @Column(name = "addr", length = 255)
    private String addr;

    /**
     * 도로명 주소
     */
    @Column(name = "road_addr", length = 255)
    private String roadAddr;

    /**
     * 경도
     */
    @Column(name = "prk_x", precision = 13, scale = 10)
    private BigDecimal prkX;

    /**
     * 위도
     */
    @Column(name = "prk_y", precision = 13, scale = 10)
    private BigDecimal prkY;

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

