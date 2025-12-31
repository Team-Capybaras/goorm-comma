package groom.backend.interfaces.seoul.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

/**
 * 충전소 현황 DTO
 * 
 * XML 구조:
 * <CHARGER_STTS>
 *   <CHARGER_STTS>
 *     <STAT_NM>...</STAT_NM>
 *     <STAT_ID>...</STAT_ID>
 *     ...
 *     <CHARGER_DETAILS>...</CHARGER_DETAILS>
 *   </CHARGER_STTS>
 * </CHARGER_STTS>
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChargerStatusDto {
    
    /**
     * 충전소 현황 상세 정보
     * XML: <CHARGER_STTS><CHARGER_STTS>
     */
    @JacksonXmlProperty(localName = "CHARGER_STTS")
    private ChargerStatusDetailDto chargerStts;
}

