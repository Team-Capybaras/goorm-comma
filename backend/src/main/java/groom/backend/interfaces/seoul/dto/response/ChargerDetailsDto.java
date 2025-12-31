package groom.backend.interfaces.seoul.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

/**
 * 충전기 상세 정보 DTO
 * 
 * XML 구조:
 * <CHARGER_DETAILS>
 *   <CHARGER_DETAILS>
 *     <CHARGER_ID>...</CHARGER_ID>
 *     <CHARGER_TYPE>...</CHARGER_TYPE>
 *     <CHARGER_STAT>...</CHARGER_STAT>
 *     <STATUPDDT>...</STATUPDDT>
 *     <LASTTSDT>...</LASTTSDT>
 *     <LASTTEDT>...</LASTTEDT>
 *     <NOWTSDT>...</NOWTSDT>
 *     <OUTPUT>...</OUTPUT>
 *     <METHOD>...</METHOD>
 *   </CHARGER_DETAILS>
 * </CHARGER_DETAILS>
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChargerDetailsDto {
    
    /**
     * 충전기 상세 정보 항목
     * XML: <CHARGER_DETAILS><CHARGER_DETAILS>
     */
    @JacksonXmlProperty(localName = "CHARGER_DETAILS")
    private ChargerDetailItemDto chargerDetails;
}

