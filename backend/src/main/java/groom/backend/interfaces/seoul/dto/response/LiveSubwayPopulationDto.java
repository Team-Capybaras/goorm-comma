package groom.backend.interfaces.seoul.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

/**
 * 지하철 인구 현황 DTO
 * 
 * XML 구조:
 * <LIVE_SUB_PPLTN>
 *   <SUB_ACML_GTON_PPLTN_MIN>...</SUB_ACML_GTON_PPLTN_MIN>
 *   <SUB_ACML_GTON_PPLTN_MAX>...</SUB_ACML_GTON_PPLTN_MAX>
 *   <SUB_ACML_GTOFF_PPLTN_MIN>...</SUB_ACML_GTOFF_PPLTN_MIN>
 *   ...
 *   <SUB_STN_CNT>...</SUB_STN_CNT>
 *   <SUB_STN_TIME>...</SUB_STN_TIME>
 * </LIVE_SUB_PPLTN>
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LiveSubwayPopulationDto {
    
    /**
     * 누적 승차 인구 최소
     * XML: <SUB_ACML_GTON_PPLTN_MIN>
     */
    @JacksonXmlProperty(localName = "SUB_ACML_GTON_PPLTN_MIN")
    private String subAcmlGtonPpltnMin;

    /**
     * 누적 승차 인구 최대
     * XML: <SUB_ACML_GTON_PPLTN_MAX>
     */
    @JacksonXmlProperty(localName = "SUB_ACML_GTON_PPLTN_MAX")
    private String subAcmlGtonPpltnMax;

    /**
     * 누적 하차 인구 최소
     * XML: <SUB_ACML_GTOFF_PPLTN_MIN>
     */
    @JacksonXmlProperty(localName = "SUB_ACML_GTOFF_PPLTN_MIN")
    private String subAcmlGtoffPpltnMin;

    /**
     * 누적 하차 인구 최대
     * XML: <SUB_ACML_GTOFF_PPLTN_MAX>
     */
    @JacksonXmlProperty(localName = "SUB_ACML_GTOFF_PPLTN_MAX")
    private String subAcmlGtoffPpltnMax;

    /**
     * 30분 이내 승차 인구 최소
     * XML: <SUB_30WTHN_GTON_PPLTN_MIN>
     */
    @JacksonXmlProperty(localName = "SUB_30WTHN_GTON_PPLTN_MIN")
    private String sub30wthnGtonPpltnMin;

    /**
     * 30분 이내 승차 인구 최대
     * XML: <SUB_30WTHN_GTON_PPLTN_MAX>
     */
    @JacksonXmlProperty(localName = "SUB_30WTHN_GTON_PPLTN_MAX")
    private String sub30wthnGtonPpltnMax;

    /**
     * 30분 이내 하차 인구 최소
     * XML: <SUB_30WTHN_GTOFF_PPLTN_MIN>
     */
    @JacksonXmlProperty(localName = "SUB_30WTHN_GTOFF_PPLTN_MIN")
    private String sub30wthnGtoffPpltnMin;

    /**
     * 30분 이내 하차 인구 최대
     * XML: <SUB_30WTHN_GTOFF_PPLTN_MAX>
     */
    @JacksonXmlProperty(localName = "SUB_30WTHN_GTOFF_PPLTN_MAX")
    private String sub30wthnGtoffPpltnMax;

    /**
     * 10분 이내 승차 인구 최소
     * XML: <SUB_10WTHN_GTON_PPLTN_MIN>
     */
    @JacksonXmlProperty(localName = "SUB_10WTHN_GTON_PPLTN_MIN")
    private String sub10wthnGtonPpltnMin;

    /**
     * 10분 이내 승차 인구 최대
     * XML: <SUB_10WTHN_GTON_PPLTN_MAX>
     */
    @JacksonXmlProperty(localName = "SUB_10WTHN_GTON_PPLTN_MAX")
    private String sub10wthnGtonPpltnMax;

    /**
     * 10분 이내 하차 인구 최소
     * XML: <SUB_10WTHN_GTOFF_PPLTN_MIN>
     */
    @JacksonXmlProperty(localName = "SUB_10WTHN_GTOFF_PPLTN_MIN")
    private String sub10wthnGtoffPpltnMin;

    /**
     * 10분 이내 하차 인구 최대
     * XML: <SUB_10WTHN_GTOFF_PPLTN_MAX>
     */
    @JacksonXmlProperty(localName = "SUB_10WTHN_GTOFF_PPLTN_MAX")
    private String sub10wthnGtoffPpltnMax;

    /**
     * 5분 이내 승차 인구 최소
     * XML: <SUB_5WTHN_GTON_PPLTN_MIN>
     */
    @JacksonXmlProperty(localName = "SUB_5WTHN_GTON_PPLTN_MIN")
    private String sub5wthnGtonPpltnMin;

    /**
     * 5분 이내 승차 인구 최대
     * XML: <SUB_5WTHN_GTON_PPLTN_MAX>
     */
    @JacksonXmlProperty(localName = "SUB_5WTHN_GTON_PPLTN_MAX")
    private String sub5wthnGtonPpltnMax;

    /**
     * 5분 이내 하차 인구 최소
     * XML: <SUB_5WTHN_GTOFF_PPLTN_MIN>
     */
    @JacksonXmlProperty(localName = "SUB_5WTHN_GTOFF_PPLTN_MIN")
    private String sub5wthnGtoffPpltnMin;

    /**
     * 5분 이내 하차 인구 최대
     * XML: <SUB_5WTHN_GTOFF_PPLTN_MAX>
     */
    @JacksonXmlProperty(localName = "SUB_5WTHN_GTOFF_PPLTN_MAX")
    private String sub5wthnGtoffPpltnMax;

    /**
     * 지하철역 개수
     * XML: <SUB_STN_CNT>
     */
    @JacksonXmlProperty(localName = "SUB_STN_CNT")
    private String subStnCnt;

    /**
     * 지하철 시간
     * XML: <SUB_STN_TIME>
     */
    @JacksonXmlProperty(localName = "SUB_STN_TIME")
    private String subStnTime;
}

