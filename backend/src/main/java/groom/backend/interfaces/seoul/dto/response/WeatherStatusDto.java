package groom.backend.interfaces.seoul.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

/**
 * 날씨 현황 DTO
 * 
 * XML 구조:
 * <WEATHER_STTS>
 *   <WEATHER_STTS>
 *     <WEATHER_TIME>...</WEATHER_TIME>
 *     <TEMP>...</TEMP>
 *     ...
 *     <FCST24HOURS>...</FCST24HOURS>
 *     <NEWS_LIST>...</NEWS_LIST>
 *   </WEATHER_STTS>
 * </WEATHER_STTS>
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WeatherStatusDto {
    
    /**
     * 날씨 현황 상세 정보
     * XML: <WEATHER_STTS><WEATHER_STTS>
     */
    @JacksonXmlProperty(localName = "WEATHER_STTS")
    private WeatherStatusDetailDto weatherStts;
}

