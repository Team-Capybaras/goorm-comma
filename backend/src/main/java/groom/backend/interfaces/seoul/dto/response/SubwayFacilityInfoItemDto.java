package groom.backend.interfaces.seoul.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

/**
 * 지하철 시설 정보 항목 DTO
 * 
 * XML 구조:
 * <SUB_FACIINFO>
 *   <ELVTR_NM>...</ELVTR_NM>
 *   <OPR_SEC>...</OPR_SEC>
 *   ...
 * </SUB_FACIINFO>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SubwayFacilityInfoItemDto {
    
    @JacksonXmlProperty(localName = "ELVTR_NM")
    private String elvtrNm;

    @JacksonXmlProperty(localName = "OPR_SEC")
    private String oprSec;

    @JacksonXmlProperty(localName = "INSTL_PSTN")
    private String instlPstn;

    @JacksonXmlProperty(localName = "USE_YN")
    private String useYn;

    @JacksonXmlProperty(localName = "ELVTR_SE")
    private String elvtrSe;
}
