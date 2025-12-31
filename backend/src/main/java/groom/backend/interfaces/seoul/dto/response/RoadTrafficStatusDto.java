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
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RoadTrafficStatusDto {
    
    /**
     * 평균 도로 데이터
     * XML: <AVG_ROAD_DATA>
     */
    @JacksonXmlProperty(localName = "AVG_ROAD_DATA")
    private AvgRoadDataDto avgRoadData;

    /**
     * 도로 교통 현황 링크 리스트
     * XML: <ROAD_TRAFFIC_STTS><ROAD_TRAFFIC_STTS>...</ROAD_TRAFFIC_STTS></ROAD_TRAFFIC_STTS>
     */
    @JacksonXmlProperty(localName = "ROAD_TRAFFIC_STTS")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<RoadTrafficLinkDto> roadTrafficStts;
}

