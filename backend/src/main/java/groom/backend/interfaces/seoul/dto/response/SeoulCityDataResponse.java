package groom.backend.interfaces.seoul.dto.response;

import lombok.*;

/**
 * 서울시 공공 API 핫스팟 장소 조회 응답 DTO
 * XML 응답을 String으로 받아서 처리
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SeoulCityDataResponse {
    /**
     * XML 형식의 응답 데이터
     */
    private String xmlData;
}

