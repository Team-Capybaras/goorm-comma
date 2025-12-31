package groom.backend.interfaces.seoul.dto.response;

import lombok.*;

import java.util.Map;

/**
 * 서울시 공공 API 핫스팟 장소 조회 응답 DTO
 * XML 응답을 JSON으로 변환하여 반환
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SeoulCityDataResponse {
    /**
     * JSON 형식의 응답 데이터 (Map 형태로 동적 필드 지원)
     */
    private Map<String, Object> data;
}

