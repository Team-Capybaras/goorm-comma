package groom.backend.interfaces.seoul.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

/**
 * 평균 도로 데이터 DTO
 * 
 * XML 구조:
 * <AVG_ROAD_DATA>
 *   <ROAD_MSG>...</ROAD_MSG>
 *   <ROAD_TRAFFIC_IDX>...</ROAD_TRAFFIC_IDX>
 *   <ROAD_TRAFFIC_SPD>...</ROAD_TRAFFIC_SPD>
 *   <ROAD_TRAFFIC_TIME>...</ROAD_TRAFFIC_TIME>
 * </AVG_ROAD_DATA>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AvgRoadDataDto {
    
    @JacksonXmlProperty(localName = "ROAD_MSG")
    private String roadMsg;

    @JacksonXmlProperty(localName = "ROAD_TRAFFIC_IDX")
    private String roadTrafficIdx;

    @JacksonXmlProperty(localName = "ROAD_TRAFFIC_SPD")
    private String roadTrafficSpd;

    @JacksonXmlProperty(localName = "ROAD_TRAFFIC_TIME")
    private String roadTrafficTime;
}
