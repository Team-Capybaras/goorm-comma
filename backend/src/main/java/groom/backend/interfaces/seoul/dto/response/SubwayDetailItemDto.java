package groom.backend.interfaces.seoul.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

/**
 * 지하철 상세 정보 항목 DTO
 * 
 * XML 구조:
 * <SUB_DETAIL>
 *   <SUB_NT_STN>...</SUB_NT_STN>
 *   <SUB_BF_STN>...</SUB_BF_STN>
 *   <SUB_ROUTE_NM>...</SUB_ROUTE_NM>
 *   <SUB_LINE>...</SUB_LINE>
 *   <SUB_ORD>...</SUB_ORD>
 *   <SUB_DIR>...</SUB_DIR>
 *   <SUB_TERMINAL>...</SUB_TERMINAL>
 *   <SUB_ARVTIME>...</SUB_ARVTIME>
 *   <SUB_ARMG1>...</SUB_ARMG1>
 *   <SUB_ARMG2>...</SUB_ARMG2>
 *   <SUB_ARVINFO>...</SUB_ARVINFO>
 * </SUB_DETAIL>
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SubwayDetailItemDto {
    
    /**
     * 다음 역 코드
     * XML: <SUB_NT_STN>
     */
    @JacksonXmlProperty(localName = "SUB_NT_STN")
    private String subNtStn;

    /**
     * 이전 역 코드
     * XML: <SUB_BF_STN>
     */
    @JacksonXmlProperty(localName = "SUB_BF_STN")
    private String subBfStn;

    /**
     * 노선명
     * XML: <SUB_ROUTE_NM>
     */
    @JacksonXmlProperty(localName = "SUB_ROUTE_NM")
    private String subRouteNm;

    /**
     * 노선 (예: "2호선")
     * XML: <SUB_LINE>
     */
    @JacksonXmlProperty(localName = "SUB_LINE")
    private String subLine;

    /**
     * 순서
     * XML: <SUB_ORD>
     */
    @JacksonXmlProperty(localName = "SUB_ORD")
    private String subOrd;

    /**
     * 방향 (외선, 내선)
     * XML: <SUB_DIR>
     */
    @JacksonXmlProperty(localName = "SUB_DIR")
    private String subDir;

    /**
     * 종점역
     * XML: <SUB_TERMINAL>
     */
    @JacksonXmlProperty(localName = "SUB_TERMINAL")
    private String subTerminal;

    /**
     * 도착 예정 시간 (초)
     * XML: <SUB_ARVTIME>
     */
    @JacksonXmlProperty(localName = "SUB_ARVTIME")
    private String subArvtime;

    /**
     * 도착 안내 1
     * XML: <SUB_ARMG1>
     */
    @JacksonXmlProperty(localName = "SUB_ARMG1")
    private String subArmg1;

    /**
     * 도착 안내 2
     * XML: <SUB_ARMG2>
     */
    @JacksonXmlProperty(localName = "SUB_ARMG2")
    private String subArmg2;

    /**
     * 도착 정보
     * XML: <SUB_ARVINFO>
     */
    @JacksonXmlProperty(localName = "SUB_ARVINFO")
    private String subArvinfo;
}

