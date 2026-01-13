package groom.backend.domain.avoidance.entity;


import groom.backend.domain.park.entity.Park;
import groom.backend.domain.avoidance.enums.Weekday;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 인구 혼잡도 집계 로그 테이블
 * 집계 시간 기록
 */
@Entity
@Table(name = "park_statistics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkStatisticsLog {

  /**
   * 로그 키
   * PK
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="log_id")
  private Long logId;


  /**
   * 요일
   * MON, TUE, WED...
   */
  @Enumerated(EnumType.STRING)
  @Column(name = "weekday", length = 3, nullable = false)
  private Weekday weekday;

  /**
   * 시간
   * 집계 데이터 중 9 - 22 만 추천 시 사용.
   */
  @Column(name = "hour", nullable = false)
  private int hour;

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
   * 집계 데이터 시작일
   */
  @Column(name = "start_time")
  private LocalDateTime startTime;


  /**
   * 집계 데이터 종료일
   */
  @Column(name = "end_time")
  private LocalDateTime endTime;

  /**
   * 공원 정보 (FK)
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "area_code", insertable = false, updatable = false)
  private Park park;
}
