package groom.backend.interfaces.seoul.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

/**
 * 버스 인구 현황 DTO
 * 
 * XML 구조:
 * <LIVE_BUS_PPLTN>
 *   <BUS_ACML_GTON_PPLTN_MIN>...</BUS_ACML_GTON_PPLTN_MIN>
 *   <BUS_ACML_GTON_PPLTN_MAX>...</BUS_ACML_GTON_PPLTN_MAX>
 *   ...
 *   <BUS_STN_CNT>...</BUS_STN_CNT>
 *   <BUS_STN_TIME>...</BUS_STN_TIME>
 * </LIVE_BUS_PPLTN>
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LiveBusPopulationDto {
    
    /**
     * 누적 승차 인구 최소
     * XML: <BUS_ACML_GTON_PPLTN_MIN>
     */
    @JacksonXmlProperty(localName = "BUS_ACML_GTON_PPLTN_MIN")
    private String busAcmlGtonPpltnMin;

    /**
     * 누적 승차 인구 최대
     * XML: <BUS_ACML_GTON_PPLTN_MAX>
     */
    @JacksonXmlProperty(localName = "BUS_ACML_GTON_PPLTN_MAX")
    private String busAcmlGtonPpltnMax;

    /**
     * 누적 하차 인구 최소
     * XML: <BUS_ACML_GTOFF_PPLTN_MIN>
     */
    @JacksonXmlProperty(localName = "BUS_ACML_GTOFF_PPLTN_MIN")
    private String busAcmlGtoffPpltnMin;

    /**
     * 누적 하차 인구 최대
     * XML: <BUS_ACML_GTOFF_PPLTN_MAX>
     */
    @JacksonXmlProperty(localName = "BUS_ACML_GTOFF_PPLTN_MAX")
    private String busAcmlGtoffPpltnMax;

    /**
     * 30분 이내 승차 인구 최소
     * XML: <BUS_30WTHN_GTON_PPLTN_MIN>
     */
    @JacksonXmlProperty(localName = "BUS_30WTHN_GTON_PPLTN_MIN")
    private String bus30wthnGtonPpltnMin;

    /**
     * 30분 이내 승차 인구 최대
     * XML: <BUS_30WTHN_GTON_PPLTN_MAX>
     */
    @JacksonXmlProperty(localName = "BUS_30WTHN_GTON_PPLTN_MAX")
    private String bus30wthnGtonPpltnMax;

    /**
     * 30분 이내 하차 인구 최소
     * XML: <BUS_30WTHN_GTOFF_PPLTN_MIN>
     */
    @JacksonXmlProperty(localName = "BUS_30WTHN_GTOFF_PPLTN_MIN")
    private String bus30wthnGtoffPpltnMin;

    /**
     * 30분 이내 하차 인구 최대
     * XML: <BUS_30WTHN_GTOFF_PPLTN_MAX>
     */
    @JacksonXmlProperty(localName = "BUS_30WTHN_GTOFF_PPLTN_MAX")
    private String bus30wthnGtoffPpltnMax;

    /**
     * 10분 이내 승차 인구 최소
     * XML: <BUS_10WTHN_GTON_PPLTN_MIN>
     */
    @JacksonXmlProperty(localName = "BUS_10WTHN_GTON_PPLTN_MIN")
    private String bus10wthnGtonPpltnMin;

    /**
     * 10분 이내 승차 인구 최대
     * XML: <BUS_10WTHN_GTON_PPLTN_MAX>
     */
    @JacksonXmlProperty(localName = "BUS_10WTHN_GTON_PPLTN_MAX")
    private String bus10wthnGtonPpltnMax;

    /**
     * 10분 이내 하차 인구 최소
     * XML: <BUS_10WTHN_GTOFF_PPLTN_MIN>
     */
    @JacksonXmlProperty(localName = "BUS_10WTHN_GTOFF_PPLTN_MIN")
    private String bus10wthnGtoffPpltnMin;

    /**
     * 10분 이내 하차 인구 최대
     * XML: <BUS_10WTHN_GTOFF_PPLTN_MAX>
     */
    @JacksonXmlProperty(localName = "BUS_10WTHN_GTOFF_PPLTN_MAX")
    private String bus10wthnGtoffPpltnMax;

    /**
     * 5분 이내 승차 인구 최소
     * XML: <BUS_5WTHN_GTON_PPLTN_MIN>
     */
    @JacksonXmlProperty(localName = "BUS_5WTHN_GTON_PPLTN_MIN")
    private String bus5wthnGtonPpltnMin;

    /**
     * 5분 이내 승차 인구 최대
     * XML: <BUS_5WTHN_GTON_PPLTN_MAX>
     */
    @JacksonXmlProperty(localName = "BUS_5WTHN_GTON_PPLTN_MAX")
    private String bus5wthnGtonPpltnMax;

    /**
     * 5분 이내 하차 인구 최소
     * XML: <BUS_5WTHN_GTOFF_PPLTN_MIN>
     */
    @JacksonXmlProperty(localName = "BUS_5WTHN_GTOFF_PPLTN_MIN")
    private String bus5wthnGtoffPpltnMin;

    /**
     * 5분 이내 하차 인구 최대
     * XML: <BUS_5WTHN_GTOFF_PPLTN_MAX>
     */
    @JacksonXmlProperty(localName = "BUS_5WTHN_GTOFF_PPLTN_MAX")
    private String bus5wthnGtoffPpltnMax;

    /**
     * 버스 정류장 개수
     * XML: <BUS_STN_CNT>
     */
    @JacksonXmlProperty(localName = "BUS_STN_CNT")
    private String busStnCnt;

    /**
     * 버스 정류장 시간
     * XML: <BUS_STN_TIME>
     */
    @JacksonXmlProperty(localName = "BUS_STN_TIME")
    private String busStnTime;
}

