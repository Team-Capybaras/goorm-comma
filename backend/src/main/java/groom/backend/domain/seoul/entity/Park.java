package groom.backend.domain.seoul.entity;

import groom.backend.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

/**
 * 공원 기본 정보 엔티티
 * 테이블: park
 */
@Entity
@Table(name = "park")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Park extends BaseEntity {
    
    /**
     * 지역 코드 (PK)
     */
    @Id
    @Column(name = "area_code", length = 20, nullable = false)
    private String areaCode;

    /**
     * 지역명
     */
    @Column(name = "area_name", length = 20)
    private String areaName;
}

