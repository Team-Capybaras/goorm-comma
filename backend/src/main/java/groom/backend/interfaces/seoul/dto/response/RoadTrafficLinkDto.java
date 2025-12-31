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
 *   <START_ND_CD>...</START_ND_CD>
 *   <START_ND_NM>...</START_ND_NM>
 *   <START_ND_XY>...</START_ND_XY>
 *   <END_ND_CD>...</END_ND_CD>
 *   <END_ND_NM>...</END_ND_NM>
 *   <END_ND_XY>...</END_ND_XY>
 *   <DIST>...</DIST>
 *   <SPD>...</SPD>
 *   <IDX>...</IDX>
 *   <XYLIST>...</XYLIST>
 * </ROAD_TRAFFIC_STTS>
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RoadTrafficLinkDto {
    
    /**
     * 링크 ID
     * XML: <LINK_ID>
     */
    @JacksonXmlProperty(localName = "LINK_ID")
    private String linkId;

    /**
     * 도로명
     * XML: <ROAD_NM>
     */
    @JacksonXmlProperty(localName = "ROAD_NM")
    private String roadNm;

    /**
     * 시작 노드 코드
     * XML: <START_ND_CD>
     */
    @JacksonXmlProperty(localName = "START_ND_CD")
    private String startNdCd;

    /**
     * 시작 노드명
     * XML: <START_ND_NM>
     */
    @JacksonXmlProperty(localName = "START_ND_NM")
    private String startNdNm;

    /**
     * 시작 노드 좌표 (경도_위도)
     * XML: <START_ND_XY>
     */
    @JacksonXmlProperty(localName = "START_ND_XY")
    private String startNdXy;

    /**
     * 종료 노드 코드
     * XML: <END_ND_CD>
     */
    @JacksonXmlProperty(localName = "END_ND_CD")
    private String endNdCd;

    /**
     * 종료 노드명
     * XML: <END_ND_NM>
     */
    @JacksonXmlProperty(localName = "END_ND_NM")
    private String endNdNm;

    /**
     * 종료 노드 좌표 (경도_위도)
     * XML: <END_ND_XY>
     */
    @JacksonXmlProperty(localName = "END_ND_XY")
    private String endNdXy;

    /**
     * 거리 (미터)
     * XML: <DIST>
     */
    @JacksonXmlProperty(localName = "DIST")
    private String dist;

    /**
     * 속도 (km/h)
     * XML: <SPD>
     */
    @JacksonXmlProperty(localName = "SPD")
    private String spd;

    /**
     * 교통 지수 (원활, 서행, 정체 등)
     * XML: <IDX>
     */
    @JacksonXmlProperty(localName = "IDX")
    private String idx;

    /**
     * 좌표 리스트 (파이프(|)로 구분)
     * XML: <XYLIST>
     */
    @JacksonXmlProperty(localName = "XYLIST")
    private String xylist;
}

