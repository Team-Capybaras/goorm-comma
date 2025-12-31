package groom.backend.interfaces.seoul.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

/**
 * 실시간 인구 현황 상세 정보 DTO
 * 
 * XML 구조:
 * <LIVE_PPLTN_STTS>
 *   <AREA_NM>...</AREA_NM>
 *   <AREA_CD>...</AREA_CD>
 *   <AREA_CONGEST_LVL>...</AREA_CONGEST_LVL>
 *   ...
 *   <FCST_PPLTN>...</FCST_PPLTN>
 * </LIVE_PPLTN_STTS>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LivePopulationStatusDetailDto {
    
    @JacksonXmlProperty(localName = "AREA_NM")
    private String areaNm;

    @JacksonXmlProperty(localName = "AREA_CD")
    private String areaCd;

    @JacksonXmlProperty(localName = "AREA_CONGEST_LVL")
    private String areaCongestLvl;

    @JacksonXmlProperty(localName = "AREA_CONGEST_MSG")
    private String areaCongestMsg;

    @JacksonXmlProperty(localName = "AREA_PPLTN_MIN")
    private String areaPpltnMin;

    @JacksonXmlProperty(localName = "AREA_PPLTN_MAX")
    private String areaPpltnMax;

    @JacksonXmlProperty(localName = "MALE_PPLTN_RATE")
    private String malePpltnRate;

    @JacksonXmlProperty(localName = "FEMALE_PPLTN_RATE")
    private String femalePpltnRate;

    @JacksonXmlProperty(localName = "PPLTN_RATE_0")
    private String ppltnRate0;

    @JacksonXmlProperty(localName = "PPLTN_RATE_10")
    private String ppltnRate10;

    @JacksonXmlProperty(localName = "PPLTN_RATE_20")
    private String ppltnRate20;

    @JacksonXmlProperty(localName = "PPLTN_RATE_30")
    private String ppltnRate30;

    @JacksonXmlProperty(localName = "PPLTN_RATE_40")
    private String ppltnRate40;

    @JacksonXmlProperty(localName = "PPLTN_RATE_50")
    private String ppltnRate50;

    @JacksonXmlProperty(localName = "PPLTN_RATE_60")
    private String ppltnRate60;

    @JacksonXmlProperty(localName = "PPLTN_RATE_70")
    private String ppltnRate70;

    @JacksonXmlProperty(localName = "RESNT_PPLTN_RATE")
    private String resntPpltnRate;

    @JacksonXmlProperty(localName = "NON_RESNT_PPLTN_RATE")
    private String nonResntPpltnRate;

    @JacksonXmlProperty(localName = "REPLACE_YN")
    private String replaceYn;

    @JacksonXmlProperty(localName = "PPLTN_TIME")
    private String ppltnTime;

    @JacksonXmlProperty(localName = "FCST_YN")
    private String fcstYn;

    @JacksonXmlProperty(localName = "FCST_PPLTN")
    private PopulationForecastDto fcstPpltn;
}
