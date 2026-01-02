package groom.backend.domain.tag.entity;

import groom.backend.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

/**
 * 태그 정보 엔티티
 * 테이블: tag
 * 
 * 공원을 분류하기 위한 태그 정보를 저장합니다.
 * 공원 분류를 위한 태그 마스터 데이터입니다.
 */
@Entity
@Table(name = "tag")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tag extends BaseEntity {
    
    /**
     * 태그 ID (PK)
     */
    @Id
    @Column(name = "tag_id", nullable = false)
    private Integer tagId;

    /**
     * 태그명
     */
    @Column(name = "tag_name", length = 20)
    private String tagName;

    /**
     * 설명
     */
    @Column(name = "description", length = 255)
    private String description;
}

