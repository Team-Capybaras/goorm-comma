package groom.backend.interfaces.seoul.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

/**
 * 실시간 인구 현황 DTO
 * 
 * XML 구조:
 * <LIVE_PPLTN_STTS>
 *   <LIVE_PPLTN_STTS>
 *     <AREA_NM>...</AREA_NM>
 *     <AREA_CD>...</AREA_CD>
 *     <AREA_CONGEST_LVL>...</AREA_CONGEST_LVL>
 *     ...
 *     <FCST_PPLTN>...</FCST_PPLTN>
 *   </LIVE_PPLTN_STTS>
 * </LIVE_PPLTN_STTS>
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LivePopulationStatusDto {
    
    /**
     * 실시간 인구 현황 상세 정보
     * XML: <LIVE_PPLTN_STTS><LIVE_PPLTN_STTS>
     */
    @JacksonXmlProperty(localName = "LIVE_PPLTN_STTS")
    private LivePopulationStatusDetailDto livePpltnStts;
}

