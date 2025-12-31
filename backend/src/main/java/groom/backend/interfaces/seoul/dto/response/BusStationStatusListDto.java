package groom.backend.interfaces.seoul.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

import java.util.List;

/**
 * 버스 정류장 현황 리스트 DTO
 * 
 * XML 구조:
 * <BUS_STN_STTS>
 *   <BUS_STN_STTS>...</BUS_STN_STTS>
 *   ...
 * </BUS_STN_STTS>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BusStationStatusListDto {
    
    @JacksonXmlProperty(localName = "BUS_STN_STTS")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<BusStationStatusItemDto> busStnStts;
}
