package groom.backend.domain.park.entity;

import groom.backend.domain.enums.FeatureType;
import jakarta.persistence.*;
import lombok.*;

/**
 * 각 공원별 유형
 */
@Entity
@Table(name = "park_features")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkFeature {

  /**
   * 식별자
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="feature_id")
  private Long featureId;

  /**
   * 특징 유형
   */
  @Enumerated(EnumType.STRING)
  @Column(name="type")
  private FeatureType type;

  /**
   * 공원 특징 설명
   */
  @Column(name = "description", columnDefinition = "TEXT") // column Definiton or @Lob
  private String description;

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
