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
 *   <AREA_CONGEST_MSG>...</AREA_CONGEST_MSG>
 *   <AREA_PPLTN_MIN>...</AREA_PPLTN_MIN>
 *   <AREA_PPLTN_MAX>...</AREA_PPLTN_MAX>
 *   <MALE_PPLTN_RATE>...</MALE_PPLTN_RATE>
 *   <FEMALE_PPLTN_RATE>...</FEMALE_PPLTN_RATE>
 *   <PPLTN_RATE_0>...</PPLTN_RATE_0>
 *   ...
 *   <FCST_PPLTN>...</FCST_PPLTN>
 * </LIVE_PPLTN_STTS>
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LivePopulationStatusDetailDto {
    
    /**
     * 지역명
     * XML: <AREA_NM>
     */
    @JacksonXmlProperty(localName = "AREA_NM")
    private String areaNm;

    /**
     * 지역 코드
     * XML: <AREA_CD>
     */
    @JacksonXmlProperty(localName = "AREA_CD")
    private String areaCd;

    /**
     * 혼잡도 레벨 (여유, 보통, 붐빔, 매우붐빔)
     * XML: <AREA_CONGEST_LVL>
     */
    @JacksonXmlProperty(localName = "AREA_CONGEST_LVL")
    private String areaCongestLvl;

    /**
     * 혼잡도 메시지
     * XML: <AREA_CONGEST_MSG>
     */
    @JacksonXmlProperty(localName = "AREA_CONGEST_MSG")
    private String areaCongestMsg;

    /**
     * 최소 인구 수
     * XML: <AREA_PPLTN_MIN>
     */
    @JacksonXmlProperty(localName = "AREA_PPLTN_MIN")
    private String areaPpltnMin;

    /**
     * 최대 인구 수
     * XML: <AREA_PPLTN_MAX>
     */
    @JacksonXmlProperty(localName = "AREA_PPLTN_MAX")
    private String areaPpltnMax;

    /**
     * 남성 비율
     * XML: <MALE_PPLTN_RATE>
     */
    @JacksonXmlProperty(localName = "MALE_PPLTN_RATE")
    private String malePpltnRate;

    /**
     * 여성 비율
     * XML: <FEMALE_PPLTN_RATE>
     */
    @JacksonXmlProperty(localName = "FEMALE_PPLTN_RATE")
    private String femalePpltnRate;

    /**
     * 0대 비율
     * XML: <PPLTN_RATE_0>
     */
    @JacksonXmlProperty(localName = "PPLTN_RATE_0")
    private String ppltnRate0;

    /**
     * 10대 비율
     * XML: <PPLTN_RATE_10>
     */
    @JacksonXmlProperty(localName = "PPLTN_RATE_10")
    private String ppltnRate10;

    /**
     * 20대 비율
     * XML: <PPLTN_RATE_20>
     */
    @JacksonXmlProperty(localName = "PPLTN_RATE_20")
    private String ppltnRate20;

    /**
     * 30대 비율
     * XML: <PPLTN_RATE_30>
     */
    @JacksonXmlProperty(localName = "PPLTN_RATE_30")
    private String ppltnRate30;

    /**
     * 40대 비율
     * XML: <PPLTN_RATE_40>
     */
    @JacksonXmlProperty(localName = "PPLTN_RATE_40")
    private String ppltnRate40;

    /**
     * 50대 비율
     * XML: <PPLTN_RATE_50>
     */
    @JacksonXmlProperty(localName = "PPLTN_RATE_50")
    private String ppltnRate50;

    /**
     * 60대 비율
     * XML: <PPLTN_RATE_60>
     */
    @JacksonXmlProperty(localName = "PPLTN_RATE_60")
    private String ppltnRate60;

    /**
     * 70대 비율
     * XML: <PPLTN_RATE_70>
     */
    @JacksonXmlProperty(localName = "PPLTN_RATE_70")
    private String ppltnRate70;

    /**
     * 거주자 비율
     * XML: <RESNT_PPLTN_RATE>
     */
    @JacksonXmlProperty(localName = "RESNT_PPLTN_RATE")
    private String resntPpltnRate;

    /**
     * 비거주자 비율
     * XML: <NON_RESNT_PPLTN_RATE>
     */
    @JacksonXmlProperty(localName = "NON_RESNT_PPLTN_RATE")
    private String nonResntPpltnRate;

    /**
     * 교체 여부 (Y/N)
     * XML: <REPLACE_YN>
     */
    @JacksonXmlProperty(localName = "REPLACE_YN")
    private String replaceYn;

    /**
     * 인구 수집 시간
     * XML: <PPLTN_TIME>
     */
    @JacksonXmlProperty(localName = "PPLTN_TIME")
    private String ppltnTime;

    /**
     * 예보 여부 (Y/N)
     * XML: <FCST_YN>
     */
    @JacksonXmlProperty(localName = "FCST_YN")
    private String fcstYn;

    /**
     * 인구 예보 정보
     * XML: <FCST_PPLTN>
     */
    @JacksonXmlProperty(localName = "FCST_PPLTN")
    private PopulationForecastDto fcstPpltn;
}

