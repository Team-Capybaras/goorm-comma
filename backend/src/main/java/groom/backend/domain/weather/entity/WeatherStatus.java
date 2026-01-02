package groom.backend.domain.weather.entity;

import groom.backend.common.entity.BaseEntity;
import groom.backend.domain.park.entity.Park;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 날씨 현황 엔티티
 * 테이블: weather_status
 * 
 * 지역의 날씨 정보를 저장합니다.
 * 복합키(data_get_time, area_code)를 사용하여 시간별 날씨 정보를 추적합니다.
 * 온도, 습도, 대기질 등 종합적인 날씨 정보를 포함합니다.
 */
@Entity
@Table(name = "weather_status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(WeatherStatusId.class)
public class WeatherStatus extends BaseEntity {
    
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
     * 날씨 시간
     */
    @Column(name = "weather_time")
    private LocalDateTime weatherTime;

    /**
     * 온도
     */
    @Column(name = "temp", precision = 5, scale = 2)
    private BigDecimal temp;

    /**
     * 체감 온도
     */
    @Column(name = "sensible_temp", precision = 5, scale = 2)
    private BigDecimal sensibleTemp;

    /**
     * 습도
     */
    @Column(name = "humidity")
    private Integer humidity;

    /**
     * 풍향
     */
    @Column(name = "wind_dirct", length = 10)
    private String windDirct;

    /**
     * 풍속
     */
    @Column(name = "wind_spd", precision = 5, scale = 2)
    private BigDecimal windSpd;

    /**
     * 강수량
     */
    @Column(name = "precipitation", length = 50)
    private String precipitation;

    /**
     * 강수 유형
     */
    @Column(name = "precpt_type", length = 50)
    private String precptType;

    /**
     * 강수 메시지
     */
    @Column(name = "precpt_msg", columnDefinition = "TEXT")
    private String precptMsg;

    /**
     * 자외선 지수 레벨
     */
    @Column(name = "uv_index_level")
    private Integer uvIndexLevel;

    /**
     * 자외선 지수
     */
    @Column(name = "uv_index", length = 50)
    private String uvIndex;

    /**
     * 미세먼지(PM2.5) 지수
     */
    @Column(name = "pm25_index", length = 50)
    private String pm25Index;

    /**
     * 미세먼지(PM2.5) 수치
     */
    @Column(name = "pm25")
    private Integer pm25;

    /**
     * 초미세먼지(PM10) 지수
     */
    @Column(name = "pm10_index", length = 50)
    private String pm10Index;

    /**
     * 초미세먼지(PM10) 수치
     */
    @Column(name = "pm10")
    private Integer pm10;

    /**
     * 대기질 지수
     */
    @Column(name = "air_index", length = 50)
    private String airIndex;

    /**
     * 대기질 지수 수치
     */
    @Column(name = "air_index_level", precision = 5, scale = 2)
    private BigDecimal airIndexLevel;

    /**
     * 대기질 지수 주요 원인
     */
    @Column(name = "air_index_main", length = 50)
    private String airIndexMain;

    /**
     * 대기질 메시지
     */
    @Column(name = "air_msg", columnDefinition = "TEXT")
    private String airMsg;

    /**
     * 데이터 소스
     */
    @Column(name = "data_source", length = 50)
    private String dataSource;

    /**
     * 공원 정보 (FK)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_code", insertable = false, updatable = false)
    private Park park;
}

