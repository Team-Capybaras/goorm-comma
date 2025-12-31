package groom.backend.interfaces.seoul.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

import java.util.List;

/**
 * 주차장 현황 리스트 DTO
 * 
 * XML 구조:
 * <PRK_STTS>
 *   <PRK_STTS>...</PRK_STTS>
 *   ...
 * </PRK_STTS>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ParkingStatusListDto {
    
    @JacksonXmlProperty(localName = "PRK_STTS")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<ParkingStatusItemDto> prkStts;
}
