package groom.backend.interfaces.seoul.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

/**
 * 지하철 현황 DTO
 * 
 * XML 구조:
 * <SUB_STTS>
 *   <SUB_STTS>...</SUB_STTS>
 * </SUB_STTS>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SubwayStatusDto {
    
    @JacksonXmlProperty(localName = "SUB_STTS")
    private SubwayStatusDetailDto subStts;
}
