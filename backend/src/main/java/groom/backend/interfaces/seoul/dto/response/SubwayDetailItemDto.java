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
 *   ...
 * </SUB_DETAIL>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SubwayDetailItemDto {
    
    @JacksonXmlProperty(localName = "SUB_NT_STN")
    private String subNtStn;

    @JacksonXmlProperty(localName = "SUB_BF_STN")
    private String subBfStn;

    @JacksonXmlProperty(localName = "SUB_ROUTE_NM")
    private String subRouteNm;

    @JacksonXmlProperty(localName = "SUB_LINE")
    private String subLine;

    @JacksonXmlProperty(localName = "SUB_ORD")
    private String subOrd;

    @JacksonXmlProperty(localName = "SUB_DIR")
    private String subDir;

    @JacksonXmlProperty(localName = "SUB_TERMINAL")
    private String subTerminal;

    @JacksonXmlProperty(localName = "SUB_ARVTIME")
    private String subArvtime;

    @JacksonXmlProperty(localName = "SUB_ARMG1")
    private String subArmg1;

    @JacksonXmlProperty(localName = "SUB_ARMG2")
    private String subArmg2;

    @JacksonXmlProperty(localName = "SUB_ARVINFO")
    private String subArvinfo;
}
