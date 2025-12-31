package groom.backend.domain.seoul.entity;

import groom.backend.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

/**
 * 태그 정보 엔티티
 * 테이블: tag
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

