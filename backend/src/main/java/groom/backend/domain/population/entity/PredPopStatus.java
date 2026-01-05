package groom.backend.domain.population.entity;

import groom.backend.common.entity.BaseEntity;
import groom.backend.domain.park.entity.Park;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 인구 예보 엔티티
 * 테이블: pred_pop_status
 * 
 * 실시간 인구 현황의 미래 예보 데이터를 저장합니다.
 * 복합키(data_get_time, area_code)를 사용하여 시간별 예보 데이터를 추적합니다.
 */
@Entity
@Table(name = "pred_pop_status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(PredPopStatusId.class)
public class PredPopStatus extends BaseEntity {
    
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
     * 예보 시간
     */
    @Column(name = "forecast_time")
    private LocalDateTime forecastTime;

    /**
     * 예보 혼잡도 레벨
     */
    @Column(name = "forecase_congest_level", length = 50)
    private String forecastCongestLevel;

    /**
     * 예보 최소 인구 수
     */
    @Column(name = "forecast_pop_min")
    private Integer forecastPopMin;

    /**
     * 예보 최대 인구 수
     */
    @Column(name = "forecast_pop_max")
    private Integer forecastPopMax;

    /**
     * 공원 정보 (FK)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_code", insertable = false, updatable = false)
    private Park park;
}

