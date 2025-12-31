package groom.backend.interfaces.seoul.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

/**
 * 버스 인구 현황 DTO
 * 
 * XML 구조:
 * <LIVE_BUS_PPLTN>
 *   <BUS_ACML_GTON_PPLTN_MIN>...</BUS_ACML_GTON_PPLTN_MIN>
 *   ...
 * </LIVE_BUS_PPLTN>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LiveBusPopulationDto {
    
    @JacksonXmlProperty(localName = "BUS_ACML_GTON_PPLTN_MIN")
    private String busAcmlGtonPpltnMin;

    @JacksonXmlProperty(localName = "BUS_ACML_GTON_PPLTN_MAX")
    private String busAcmlGtonPpltnMax;

    @JacksonXmlProperty(localName = "BUS_ACML_GTOFF_PPLTN_MIN")
    private String busAcmlGtoffPpltnMin;

    @JacksonXmlProperty(localName = "BUS_ACML_GTOFF_PPLTN_MAX")
    private String busAcmlGtoffPpltnMax;

    @JacksonXmlProperty(localName = "BUS_30WTHN_GTON_PPLTN_MIN")
    private String bus30wthnGtonPpltnMin;

    @JacksonXmlProperty(localName = "BUS_30WTHN_GTON_PPLTN_MAX")
    private String bus30wthnGtonPpltnMax;

    @JacksonXmlProperty(localName = "BUS_30WTHN_GTOFF_PPLTN_MIN")
    private String bus30wthnGtoffPpltnMin;

    @JacksonXmlProperty(localName = "BUS_30WTHN_GTOFF_PPLTN_MAX")
    private String bus30wthnGtoffPpltnMax;

    @JacksonXmlProperty(localName = "BUS_10WTHN_GTON_PPLTN_MIN")
    private String bus10wthnGtonPpltnMin;

    @JacksonXmlProperty(localName = "BUS_10WTHN_GTON_PPLTN_MAX")
    private String bus10wthnGtonPpltnMax;

    @JacksonXmlProperty(localName = "BUS_10WTHN_GTOFF_PPLTN_MIN")
    private String bus10wthnGtoffPpltnMin;

    @JacksonXmlProperty(localName = "BUS_10WTHN_GTOFF_PPLTN_MAX")
    private String bus10wthnGtoffPpltnMax;

    @JacksonXmlProperty(localName = "BUS_5WTHN_GTON_PPLTN_MIN")
    private String bus5wthnGtonPpltnMin;

    @JacksonXmlProperty(localName = "BUS_5WTHN_GTON_PPLTN_MAX")
    private String bus5wthnGtonPpltnMax;

    @JacksonXmlProperty(localName = "BUS_5WTHN_GTOFF_PPLTN_MIN")
    private String bus5wthnGtoffPpltnMin;

    @JacksonXmlProperty(localName = "BUS_5WTHN_GTOFF_PPLTN_MAX")
    private String bus5wthnGtoffPpltnMax;

    @JacksonXmlProperty(localName = "BUS_STN_CNT")
    private String busStnCnt;

    @JacksonXmlProperty(localName = "BUS_STN_TIME")
    private String busStnTime;
}
