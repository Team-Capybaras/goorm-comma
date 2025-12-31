package groom.backend.interfaces.seoul.dto.request;

import lombok.*;

/**
 * 서울시 공공 API 핫스팟 장소 조회 요청 DTO
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeoulCityDataRequest {
    /**
     * 핫스팟 장소명 (AREA_NM)
     */
    private String areaNm;
    
    /**
     * 시작 인덱스 (기본값: 1)
     */
    @Builder.Default
    private Integer startIndex = 1;
    
    /**
     * 종료 인덱스 (기본값: 5)
     */
    @Builder.Default
    private Integer endIndex = 5;
}

