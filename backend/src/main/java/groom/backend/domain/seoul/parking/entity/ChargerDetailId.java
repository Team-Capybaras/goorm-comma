package groom.backend.domain.seoul.parking.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ChargerDetail 복합 키 클래스
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ChargerDetailId implements Serializable {
    private Integer chargerId;
    private String stationId;
}

