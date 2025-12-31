package groom.backend.interfaces.seoul.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

/**
 * 충전소 현황 DTO
 * 
 * XML 구조:
 * <CHARGER_STTS>
 *   <CHARGER_STTS>...</CHARGER_STTS>
 * </CHARGER_STTS>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChargerStatusDto {
    
    @JacksonXmlProperty(localName = "CHARGER_STTS")
    private ChargerStatusDetailDto chargerStts;
}
