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
 *   <FCST24HOURS>
 *     <FCST_DT>...</FCST_DT>
 *     <TEMP>...</TEMP>
 *     <PRECIPITATION>...</PRECIPITATION>
 *     <PRECPT_TYPE>...</PRECPT_TYPE>
 *     <RAIN_CHANCE>...</RAIN_CHANCE>
 *     <SKY_STTS>...</SKY_STTS>
 *   </FCST24HOURS>
 *   ...
 * </FCST24HOURS>
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WeatherForecast24HoursDto {
    
    /**
     * 24시간 날씨 예보 항목 리스트
     * XML: <FCST24HOURS><FCST24HOURS>...</FCST24HOURS></FCST24HOURS>
     */
    @JacksonXmlProperty(localName = "FCST24HOURS")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<WeatherForecast24HoursItemDto> fcst24Hours;
}

