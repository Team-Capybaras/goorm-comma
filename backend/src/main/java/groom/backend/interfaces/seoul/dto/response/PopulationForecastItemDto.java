package groom.backend.interfaces.seoul.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

/**
 * 인구 예보 항목 DTO
 * 
 * XML 구조:
 * <FCST_PPLTN>
 *   <FCST_TIME>...</FCST_TIME>
 *   <FCST_CONGEST_LVL>...</FCST_CONGEST_LVL>
 *   <FCST_PPLTN_MIN>...</FCST_PPLTN_MIN>
 *   <FCST_PPLTN_MAX>...</FCST_PPLTN_MAX>
 * </FCST_PPLTN>
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PopulationForecastItemDto {
    
    /**
     * 예보 시간 (예: "2025-12-31 15:00")
     * XML: <FCST_TIME>
     */
    @JacksonXmlProperty(localName = "FCST_TIME")
    private String fcstTime;

    /**
     * 예보 혼잡도 레벨 (여유, 보통, 붐빔, 매우붐빔)
     * XML: <FCST_CONGEST_LVL>
     */
    @JacksonXmlProperty(localName = "FCST_CONGEST_LVL")
    private String fcstCongestLvl;

    /**
     * 예보 최소 인구 수
     * XML: <FCST_PPLTN_MIN>
     */
    @JacksonXmlProperty(localName = "FCST_PPLTN_MIN")
    private String fcstPpltnMin;

    /**
     * 예보 최대 인구 수
     * XML: <FCST_PPLTN_MAX>
     */
    @JacksonXmlProperty(localName = "FCST_PPLTN_MAX")
    private String fcstPpltnMax;
}

