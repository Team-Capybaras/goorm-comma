package groom.backend.interfaces.seoul.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

/**
 * 지하철 인구 현황 DTO
 * 
 * XML 구조:
 * <LIVE_SUB_PPLTN>
 *   <SUB_ACML_GTON_PPLTN_MIN>...</SUB_ACML_GTON_PPLTN_MIN>
 *   ...
 * </LIVE_SUB_PPLTN>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LiveSubwayPopulationDto {
    
    @JacksonXmlProperty(localName = "SUB_ACML_GTON_PPLTN_MIN")
    private String subAcmlGtonPpltnMin;

    @JacksonXmlProperty(localName = "SUB_ACML_GTON_PPLTN_MAX")
    private String subAcmlGtonPpltnMax;

    @JacksonXmlProperty(localName = "SUB_ACML_GTOFF_PPLTN_MIN")
    private String subAcmlGtoffPpltnMin;

    @JacksonXmlProperty(localName = "SUB_ACML_GTOFF_PPLTN_MAX")
    private String subAcmlGtoffPpltnMax;

    @JacksonXmlProperty(localName = "SUB_30WTHN_GTON_PPLTN_MIN")
    private String sub30wthnGtonPpltnMin;

    @JacksonXmlProperty(localName = "SUB_30WTHN_GTON_PPLTN_MAX")
    private String sub30wthnGtonPpltnMax;

    @JacksonXmlProperty(localName = "SUB_30WTHN_GTOFF_PPLTN_MIN")
    private String sub30wthnGtoffPpltnMin;

    @JacksonXmlProperty(localName = "SUB_30WTHN_GTOFF_PPLTN_MAX")
    private String sub30wthnGtoffPpltnMax;

    @JacksonXmlProperty(localName = "SUB_10WTHN_GTON_PPLTN_MIN")
    private String sub10wthnGtonPpltnMin;

    @JacksonXmlProperty(localName = "SUB_10WTHN_GTON_PPLTN_MAX")
    private String sub10wthnGtonPpltnMax;

    @JacksonXmlProperty(localName = "SUB_10WTHN_GTOFF_PPLTN_MIN")
    private String sub10wthnGtoffPpltnMin;

    @JacksonXmlProperty(localName = "SUB_10WTHN_GTOFF_PPLTN_MAX")
    private String sub10wthnGtoffPpltnMax;

    @JacksonXmlProperty(localName = "SUB_5WTHN_GTON_PPLTN_MIN")
    private String sub5wthnGtonPpltnMin;

    @JacksonXmlProperty(localName = "SUB_5WTHN_GTON_PPLTN_MAX")
    private String sub5wthnGtonPpltnMax;

    @JacksonXmlProperty(localName = "SUB_5WTHN_GTOFF_PPLTN_MIN")
    private String sub5wthnGtoffPpltnMin;

    @JacksonXmlProperty(localName = "SUB_5WTHN_GTOFF_PPLTN_MAX")
    private String sub5wthnGtoffPpltnMax;

    @JacksonXmlProperty(localName = "SUB_STN_CNT")
    private String subStnCnt;

    @JacksonXmlProperty(localName = "SUB_STN_TIME")
    private String subStnTime;
}
