package groom.backend.domain.avoidance.entity;

import groom.backend.domain.park.entity.Park;
import groom.backend.domain.avoidance.enums.Weekday;
import jakarta.persistence.*;
import lombok.*;


/**
 * 인구 혼잡도 집계 테이블
 * 요일 및 시간별 집계
 */
@Entity
@Table(name = "park_statistics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(ParkStatisticsId.class)
public class ParkStatistics {
  /**
   * 요일
   * MON, TUE, WED...
   */
  @Id
  @Enumerated(EnumType.STRING)
  @Column(name = "weekday", length = 3, nullable = false)
  private Weekday weekday;

  /**
   * 시간
   * 집계 데이터 중 9 - 22 만 추천 시 사용.
   */
  @Id
  @Column(name = "hour", nullable = false)
  private int hour;

  /**
   * 지역 코드 (PK, FK -> park.area_code)
   */
  @Id
  @Column(name = "area_code", length = 20, nullable = false)
  private String areaCode;


  /**
   * 인구 지표 최소값
   */
  @Column(name = "pop_mean_min")
  private int popMeanMin;

  /**
   * 인구 지표 최소값
   */
  @Column(name = "pop_mean_max")
  private int popMeanMax;


  /**
   * 공원 정보 (FK)
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "area_code", insertable = false, updatable = false)
  private Park park;
}