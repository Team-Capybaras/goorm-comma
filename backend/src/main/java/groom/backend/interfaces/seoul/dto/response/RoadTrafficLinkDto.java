package groom.backend.interfaces.seoul.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

/**
 * 도로 교통 링크 DTO
 * 
 * XML 구조:
 * <ROAD_TRAFFIC_STTS>
 *   <LINK_ID>...</LINK_ID>
 *   <ROAD_NM>...</ROAD_NM>
 *   ...
 * </ROAD_TRAFFIC_STTS>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoadTrafficLinkDto {
    
    @JacksonXmlProperty(localName = "LINK_ID")
    private String linkId;

    @JacksonXmlProperty(localName = "ROAD_NM")
    private String roadNm;

    @JacksonXmlProperty(localName = "START_ND_CD")
    private String startNdCd;

    @JacksonXmlProperty(localName = "START_ND_NM")
    private String startNdNm;

    @JacksonXmlProperty(localName = "START_ND_XY")
    private String startNdXy;

    @JacksonXmlProperty(localName = "END_ND_CD")
    private String endNdCd;

    @JacksonXmlProperty(localName = "END_ND_NM")
    private String endNdNm;

    @JacksonXmlProperty(localName = "END_ND_XY")
    private String endNdXy;

    @JacksonXmlProperty(localName = "DIST")
    private String dist;

    @JacksonXmlProperty(localName = "SPD")
    private String spd;

    @JacksonXmlProperty(localName = "IDX")
    private String idx;

    @JacksonXmlProperty(localName = "XYLIST")
    private String xylist;
}
