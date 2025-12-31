package groom.backend.interfaces.seoul.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

/**
 * 날씨 현황 DTO
 * 
 * XML 구조:
 * <WEATHER_STTS>
 *   <WEATHER_STTS>...</WEATHER_STTS>
 * </WEATHER_STTS>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WeatherStatusDto {
    
    @JacksonXmlProperty(localName = "WEATHER_STTS")
    private WeatherStatusDetailDto weatherStts;
}
