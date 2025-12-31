package groom.backend.interfaces.seoul.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

/**
 * 충전기 상세 정보 DTO
 * 
 * XML 구조:
 * <CHARGER_DETAILS>
 *   <CHARGER_DETAILS>...</CHARGER_DETAILS>
 * </CHARGER_DETAILS>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChargerDetailsDto {
    
    @JacksonXmlProperty(localName = "CHARGER_DETAILS")
    private ChargerDetailItemDto chargerDetails;
}
