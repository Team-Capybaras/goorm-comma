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
 *   <PRK_STTS>
 *     <PRK_NM>...</PRK_NM>
 *     <PRK_CD>...</PRK_CD>
 *     ...
 *   </PRK_STTS>
 *   ...
 * </PRK_STTS>
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ParkingStatusListDto {
    
    /**
     * 주차장 현황 항목 리스트
     * XML: <PRK_STTS><PRK_STTS>...</PRK_STTS></PRK_STTS>
     */
    @JacksonXmlProperty(localName = "PRK_STTS")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<ParkingStatusItemDto> prkStts;
}

