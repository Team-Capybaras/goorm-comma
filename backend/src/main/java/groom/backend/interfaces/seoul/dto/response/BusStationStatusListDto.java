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
 *   <BUS_STN_STTS>
 *     <BUS_RESULT_MSG>...</BUS_RESULT_MSG>
 *     <BUS_STN_ID>...</BUS_STN_ID>
 *     ...
 *   </BUS_STN_STTS>
 *   ...
 * </BUS_STN_STTS>
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BusStationStatusListDto {
    
    /**
     * 버스 정류장 현황 항목 리스트
     * XML: <BUS_STN_STTS><BUS_STN_STTS>...</BUS_STN_STTS></BUS_STN_STTS>
     */
    @JacksonXmlProperty(localName = "BUS_STN_STTS")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<BusStationStatusItemDto> busStnStts;
}

