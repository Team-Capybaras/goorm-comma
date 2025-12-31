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
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PopulationForecastItemDto {
    
    @JacksonXmlProperty(localName = "FCST_TIME")
    private String fcstTime;

    @JacksonXmlProperty(localName = "FCST_CONGEST_LVL")
    private String fcstCongestLvl;

    @JacksonXmlProperty(localName = "FCST_PPLTN_MIN")
    private String fcstPpltnMin;

    @JacksonXmlProperty(localName = "FCST_PPLTN_MAX")
    private String fcstPpltnMax;
}
