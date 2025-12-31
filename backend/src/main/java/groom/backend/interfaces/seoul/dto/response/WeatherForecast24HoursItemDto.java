package groom.backend.interfaces.seoul.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

/**
 * 24시간 날씨 예보 항목 DTO
 * 
 * XML 구조:
 * <FCST24HOURS>
 *   <FCST_DT>...</FCST_DT>
 *   <TEMP>...</TEMP>
 *   <PRECIPITATION>...</PRECIPITATION>
 *   <PRECPT_TYPE>...</PRECPT_TYPE>
 *   <RAIN_CHANCE>...</RAIN_CHANCE>
 *   <SKY_STTS>...</SKY_STTS>
 * </FCST24HOURS>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WeatherForecast24HoursItemDto {
    
    /**
     * 예보 일시 (YYYYMMDDHHmm 형식, 예: "202512311500")
     * XML: <FCST_DT>
     */
    @JacksonXmlProperty(localName = "FCST_DT")
    private String forecastDateTime;

    /**
     * 온도
     * XML: <TEMP>
     */
    @JacksonXmlProperty(localName = "TEMP")
    private String temperature;

    /**
     * 강수량
     * XML: <PRECIPITATION>
     */
    @JacksonXmlProperty(localName = "PRECIPITATION")
    private String precipitation;

    /**
     * 강수 유형
     * XML: <PRECPT_TYPE>
     */
    @JacksonXmlProperty(localName = "PRECPT_TYPE")
    private String precipitationType;

    /**
     * 강수 확률
     * XML: <RAIN_CHANCE>
     */
    @JacksonXmlProperty(localName = "RAIN_CHANCE")
    private String rainChance;

    /**
     * 하늘 상태 (맑음, 구름많음, 흐림 등)
     * XML: <SKY_STTS>
     */
    @JacksonXmlProperty(localName = "SKY_STTS")
    private String skyStatus;
}

