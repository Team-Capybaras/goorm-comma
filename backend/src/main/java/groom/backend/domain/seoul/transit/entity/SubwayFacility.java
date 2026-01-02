package groom.backend.domain.seoul.transit.entity;

import groom.backend.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

/**
 * 지하철 시설 정보 엔티티
 * 테이블: subway_facility
 * 
 * 지하철역의 엘리베이터, 에스컬레이터 등 시설 정보를 저장합니다.
 * 지하철역별 여러 시설 정보를 저장할 수 있습니다.
 */
@Entity
@Table(name = "subway_facility")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubwayFacility extends BaseEntity {
    
    /**
     * 지하철 시설 정보 ID (PK)
     */
    @Id
    @Column(name = "sub_facility_info", nullable = false)
    private Integer subFacilityInfo;

    /**
     * 승강기명
     */
    @Column(name = "elvtr_name", length = 50)
    private String elvtrName;

    /**
     * 운영 구간
     */
    @Column(name = "operate_sector", length = 20)
    private String operateSector;

    /**
     * 설치 위치
     */
    @Column(name = "install_position", length = 20)
    private String installPosition;

    /**
     * 사용 가능 여부
     */
    @Column(name = "use_yn", length = 20)
    private String useYn;

    /**
     * 승강기 구분 (EV: 엘리베이터, ES: 에스컬레이터)
     */
    @Column(name = "elvtr_section", length = 20)
    private String elvtrSection;

    /**
     * 지하철역 ID (FK -> subway_station.sub_id)
     */
    @Column(name = "sub_id", nullable = false)
    private Integer subId;

    /**
     * 지하철역 정보 (FK)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_id", insertable = false, updatable = false)
    private SubwayStation subwayStation;
}

