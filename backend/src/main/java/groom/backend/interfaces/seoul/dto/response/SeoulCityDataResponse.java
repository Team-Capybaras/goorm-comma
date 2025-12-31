package groom.backend.interfaces.seoul.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;

/**
 * 서울시 공공 API 핫스팟 장소 조회 응답 DTO
 * XML 응답을 구조화된 DTO로 매핑하여 반환
 * 
 * XML 루트 구조:
 * <SeoulCityDataResponse>
 *   <list_total_count>...</list_total_count>
 *   <RESULT>...</RESULT>
 *   <CITYDATA>...</CITYDATA>
 * </SeoulCityDataResponse>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JacksonXmlRootElement(localName = "SeoulCityDataResponse")
public class SeoulCityDataResponse {
    
    /**
     * 리스트 총 개수
     * XML: <list_total_count>
     */
    @JacksonXmlProperty(localName = "list_total_count")
    private String listTotalCount;

    /**
     * API 결과 정보
     * XML: <RESULT>
     */
    @JacksonXmlProperty(localName = "RESULT")
    private ResultDto result;

    /**
     * 도시 데이터 정보
     * XML: <CITYDATA>
     */
    @JacksonXmlProperty(localName = "CITYDATA")
    private CityDataDto cityData;
}
