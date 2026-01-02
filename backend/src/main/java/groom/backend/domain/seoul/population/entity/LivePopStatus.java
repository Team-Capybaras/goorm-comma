package groom.backend.domain.seoul.population.entity;

import groom.backend.common.entity.BaseEntity;
import groom.backend.domain.seoul.park.entity.Park;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 실시간 인구 현황 엔티티
 * 테이블: live_pop_status
 * 
 * 지역의 실시간 인구 정보를 저장합니다.
 * 복합키(data_get_time, area_code)를 사용하여 시간별 인구 현황을 추적합니다.
 */
@Entity
@Table(name = "live_pop_status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(LivePopStatusId.class)
public class LivePopStatus extends BaseEntity {
    
    /**
     * 데이터 수집 시간 (PK)
     */
    @Id
    @Column(name = "data_get_time")
    private LocalDateTime dataGetTime;

    /**
     * 지역 코드 (PK, FK -> park.area_code)
     */
    @Id
    @Column(name = "area_code", length = 20, nullable = false)
    private String areaCode;

    /**
     * 최소 인구 수
     */
    @Column(name = "area_pop_min")
    private Integer areaPopMin;

    /**
     * 최대 인구 수
     */
    @Column(name = "area_pop_max")
    private Integer areaPopMax;

    /**
     * 혼잡도 레벨 (여유, 보통, 붐빔, 매우붐빔)
     */
    @Column(name = "area_congest_level", length = 50)
    private String areaCongestLevel;

    /**
     * 혼잡도 메시지
     */
    @Column(name = "area_congest_msg", columnDefinition = "TEXT")
    private String areaCongestMsg;

    /**
     * 교체 여부
     */
    @Column(name = "replace_yn")
    private Boolean replaceYn;

    /**
     * 인구 수집 시간
     */
    @Column(name = "pop_time")
    private LocalDateTime popTime;

    /**
     * 공원 정보 (FK)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_code", insertable = false, updatable = false)
    private Park park;
}

