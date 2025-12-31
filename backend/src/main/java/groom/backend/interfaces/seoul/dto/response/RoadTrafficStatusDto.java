package groom.backend.interfaces.seoul.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

import java.util.List;

/**
 * 도로 교통 현황 DTO
 * 
 * XML 구조:
 * <ROAD_TRAFFIC_STTS>
 *   <AVG_ROAD_DATA>...</AVG_ROAD_DATA>
 *   <ROAD_TRAFFIC_STTS>...</ROAD_TRAFFIC_STTS>
 * </ROAD_TRAFFIC_STTS>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoadTrafficStatusDto {
    
    @JacksonXmlProperty(localName = "AVG_ROAD_DATA")
    private AvgRoadDataDto avgRoadData;

    @JacksonXmlProperty(localName = "ROAD_TRAFFIC_STTS")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<RoadTrafficLinkDto> roadTrafficStts;
}
