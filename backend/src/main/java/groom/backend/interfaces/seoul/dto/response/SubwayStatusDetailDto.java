package groom.backend.interfaces.seoul.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

/**
 * 지하철 현황 상세 정보 DTO
 * 
 * XML 구조:
 * <SUB_STTS>
 *   <SUB_STN_NM>...</SUB_STN_NM>
 *   <SUB_STN_LINE>...</SUB_STN_LINE>
 *   ...
 *   <SUB_DETAIL>...</SUB_DETAIL>
 *   <SUB_FACIINFO>...</SUB_FACIINFO>
 * </SUB_STTS>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SubwayStatusDetailDto {
    
    @JacksonXmlProperty(localName = "SUB_STN_NM")
    private String subStnNm;

    @JacksonXmlProperty(localName = "SUB_STN_LINE")
    private String subStnLine;

    @JacksonXmlProperty(localName = "SUB_STN_RADDR")
    private String subStnRaddr;

    @JacksonXmlProperty(localName = "SUB_STN_JIBUN")
    private String subStnJibun;

    @JacksonXmlProperty(localName = "SUB_STN_X")
    private String subStnX;

    @JacksonXmlProperty(localName = "SUB_STN_Y")
    private String subStnY;

    @JacksonXmlProperty(localName = "SUB_DETAIL")
    private SubwayDetailListDto subDetail;

    @JacksonXmlProperty(localName = "SUB_FACIINFO")
    private SubwayFacilityInfoListDto subFaciinfo;
}
