package groom.backend.domain.seoul.park.entity;

import groom.backend.common.entity.BaseEntity;
import groom.backend.domain.seoul.tag.entity.Tag;
import jakarta.persistence.*;
import lombok.*;

/**
 * 공원 태그 연결 엔티티
 * 테이블: park_tag
 * 
 * 공원과 태그의 다대다 관계를 저장합니다.
 */
@Entity
@Table(name = "park_tag")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkTag extends BaseEntity {
    
    /**
     * 공원 태그 키 (PK)
     */
    @Id
    @Column(name = "park_tag_key", length = 255, nullable = false)
    private String parkTagKey;

    /**
     * 태그 ID (FK -> tag.tag_id)
     */
    @Column(name = "tag_id", nullable = false)
    private Integer tagId;

    /**
     * 지역 코드 (FK -> park.area_code)
     */
    @Column(name = "area_code", length = 20, nullable = false)
    private String areaCode;

    /**
     * 태그 정보 (FK)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", insertable = false, updatable = false)
    private Tag tag;

    /**
     * 공원 정보 (FK)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_code", insertable = false, updatable = false)
    private Park park;
}

