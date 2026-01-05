package groom.backend.interfaces.seoul.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

import java.util.List;

/**
 * 지하철 현황 상세 정보 DTO
 * 
 * XML 구조:
 * <SUB_STTS>
 *   <SUB_STN_NM>...</SUB_STN_NM>
 *   <SUB_STN_LINE>...</SUB_STN_LINE>
 *   <SUB_STN_RADDR>...</SUB_STN_RADDR>
 *   <SUB_STN_JIBUN>...</SUB_STN_JIBUN>
 *   <SUB_STN_X>...</SUB_STN_X>
 *   <SUB_STN_Y>...</SUB_STN_Y>
 *   <SUB_DETAIL>...</SUB_DETAIL>
 *   <SUB_FACIINFO>...</SUB_FACIINFO>
 * </SUB_STTS>
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SubwayStatusDetailDto {
    
    /**
     * 지하철역명
     * XML: <SUB_STN_NM>
     */
    @JacksonXmlProperty(localName = "SUB_STN_NM")
    private String subStnNm;

    /**
     * 지하철 노선
     * XML: <SUB_STN_LINE>
     */
    @JacksonXmlProperty(localName = "SUB_STN_LINE")
    private String subStnLine;

    /**
     * 지하철역 도로명 주소
     * XML: <SUB_STN_RADDR>
     */
    @JacksonXmlProperty(localName = "SUB_STN_RADDR")
    private String subStnRaddr;

    /**
     * 지하철역 지번 주소
     * XML: <SUB_STN_JIBUN>
     */
    @JacksonXmlProperty(localName = "SUB_STN_JIBUN")
    private String subStnJibun;

    /**
     * 지하철역 X좌표 (경도)
     * XML: <SUB_STN_X>
     */
    @JacksonXmlProperty(localName = "SUB_STN_X")
    private String subStnX;

    /**
     * 지하철역 Y좌표 (위도)
     * XML: <SUB_STN_Y>
     */
    @JacksonXmlProperty(localName = "SUB_STN_Y")
    private String subStnY;

    /**
     * 지하철 상세 정보 리스트
     * XML: <SUB_DETAIL><SUB_DETAIL>...</SUB_DETAIL></SUB_DETAIL>
     */
    @JacksonXmlProperty(localName = "SUB_DETAIL")
    private SubwayDetailListDto subDetail;

    /**
     * 지하철 시설 정보 리스트
     * XML: <SUB_FACIINFO><SUB_FACIINFO>...</SUB_FACIINFO></SUB_FACIINFO>
     */
    @JacksonXmlProperty(localName = "SUB_FACIINFO")
    private SubwayFacilityInfoListDto subFaciinfo;
}

