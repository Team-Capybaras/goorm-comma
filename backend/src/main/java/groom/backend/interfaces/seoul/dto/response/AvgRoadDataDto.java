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
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AvgRoadDataDto {
    
    /**
     * 도로 메시지
     * XML: <ROAD_MSG>
     */
    @JacksonXmlProperty(localName = "ROAD_MSG")
    private String roadMsg;

    /**
     * 교통 지수 (원활, 서행, 정체 등)
     * XML: <ROAD_TRAFFIC_IDX>
     */
    @JacksonXmlProperty(localName = "ROAD_TRAFFIC_IDX")
    private String roadTrafficIdx;

    /**
     * 교통 속도 (km/h)
     * XML: <ROAD_TRAFFIC_SPD>
     */
    @JacksonXmlProperty(localName = "ROAD_TRAFFIC_SPD")
    private String roadTrafficSpd;

    /**
     * 교통 시간
     * XML: <ROAD_TRAFFIC_TIME>
     */
    @JacksonXmlProperty(localName = "ROAD_TRAFFIC_TIME")
    private String roadTrafficTime;
}

