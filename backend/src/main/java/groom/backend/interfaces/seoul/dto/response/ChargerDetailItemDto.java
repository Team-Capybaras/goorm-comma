package groom.backend.interfaces.seoul.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

/**
 * 충전기 상세 정보 항목 DTO
 * 
 * XML 구조:
 * <CHARGER_DETAILS>
 *   <CHARGER_ID>...</CHARGER_ID>
 *   <CHARGER_TYPE>...</CHARGER_TYPE>
 *   ...
 * </CHARGER_DETAILS>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChargerDetailItemDto {
    
    @JacksonXmlProperty(localName = "CHARGER_ID")
    private String chargerId;

    @JacksonXmlProperty(localName = "CHARGER_TYPE")
    private String chargerType;

    @JacksonXmlProperty(localName = "CHARGER_STAT")
    private String chargerStat;

    @JacksonXmlProperty(localName = "STATUPDDT")
    private String statuupddt;

    @JacksonXmlProperty(localName = "LASTTSDT")
    private String lasttsdt;

    @JacksonXmlProperty(localName = "LASTTEDT")
    private String lasttedt;

    @JacksonXmlProperty(localName = "NOWTSDT")
    private String nowtsdt;

    @JacksonXmlProperty(localName = "OUTPUT")
    private String output;

    @JacksonXmlProperty(localName = "METHOD")
    private String method;
}
