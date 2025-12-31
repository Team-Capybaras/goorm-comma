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
 *   <INSTL_PSTN>...</INSTL_PSTN>
 *   <USE_YN>...</USE_YN>
 *   <ELVTR_SE>...</ELVTR_SE>
 * </SUB_FACIINFO>
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SubwayFacilityInfoItemDto {
    
    /**
     * 승강기명
     * XML: <ELVTR_NM>
     */
    @JacksonXmlProperty(localName = "ELVTR_NM")
    private String elvtrNm;

    /**
     * 운영 구간
     * XML: <OPR_SEC>
     */
    @JacksonXmlProperty(localName = "OPR_SEC")
    private String oprSec;

    /**
     * 설치 위치
     * XML: <INSTL_PSTN>
     */
    @JacksonXmlProperty(localName = "INSTL_PSTN")
    private String instlPstn;

    /**
     * 사용 가능 여부
     * XML: <USE_YN>
     */
    @JacksonXmlProperty(localName = "USE_YN")
    private String useYn;

    /**
     * 승강기 구분 (EV: 엘리베이터, ES: 에스컬레이터)
     * XML: <ELVTR_SE>
     */
    @JacksonXmlProperty(localName = "ELVTR_SE")
    private String elvtrSe;
}

