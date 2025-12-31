package groom.backend.interfaces.seoul.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

import java.util.List;

/**
 * 24시간 날씨 예보 DTO
 * 
 * XML 구조:
 * <FCST24HOURS>
 *   <FCST24HOURS>...</FCST24HOURS>
 *   ...
 * </FCST24HOURS>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WeatherForecast24HoursDto {
    
    @JacksonXmlProperty(localName = "FCST24HOURS")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<WeatherForecast24HoursItemDto> fcst24Hours;
}
