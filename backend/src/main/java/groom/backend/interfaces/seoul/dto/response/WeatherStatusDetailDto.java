package groom.backend.interfaces.seoul.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

/**
 * 날씨 현황 상세 정보 DTO
 * 
 * XML 구조:
 * <WEATHER_STTS>
 *   <WEATHER_TIME>...</WEATHER_TIME>
 *   <TEMP>...</TEMP>
 *   ...
 *   <FCST24HOURS>...</FCST24HOURS>
 *   <NEWS_LIST>...</NEWS_LIST>
 * </WEATHER_STTS>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WeatherStatusDetailDto {
    
    @JacksonXmlProperty(localName = "WEATHER_TIME")
    private String weatherTime;

    @JacksonXmlProperty(localName = "TEMP")
    private String temp;

    @JacksonXmlProperty(localName = "SENSIBLE_TEMP")
    private String sensibleTemp;

    @JacksonXmlProperty(localName = "MAX_TEMP")
    private String maxTemp;

    @JacksonXmlProperty(localName = "MIN_TEMP")
    private String minTemp;

    @JacksonXmlProperty(localName = "HUMIDITY")
    private String humidity;

    @JacksonXmlProperty(localName = "WIND_DIRCT")
    private String windDirct;

    @JacksonXmlProperty(localName = "WIND_SPD")
    private String windSpd;

    @JacksonXmlProperty(localName = "PRECIPITATION")
    private String precipitation;

    @JacksonXmlProperty(localName = "PRECPT_TYPE")
    private String precptType;

    @JacksonXmlProperty(localName = "PCP_MSG")
    private String pcpMsg;

    @JacksonXmlProperty(localName = "SUNRISE")
    private String sunrise;

    @JacksonXmlProperty(localName = "SUNSET")
    private String sunset;

    @JacksonXmlProperty(localName = "UV_INDEX_LVL")
    private String uvIndexLvl;

    @JacksonXmlProperty(localName = "UV_INDEX")
    private String uvIndex;

    @JacksonXmlProperty(localName = "UV_MSG")
    private String uvMsg;

    @JacksonXmlProperty(localName = "PM25_INDEX")
    private String pm25Index;

    @JacksonXmlProperty(localName = "PM25")
    private String pm25;

    @JacksonXmlProperty(localName = "PM10_INDEX")
    private String pm10Index;

    @JacksonXmlProperty(localName = "PM10")
    private String pm10;

    @JacksonXmlProperty(localName = "AIR_IDX")
    private String airIdx;

    @JacksonXmlProperty(localName = "AIR_IDX_MVL")
    private String airIdxMvl;

    @JacksonXmlProperty(localName = "AIR_IDX_MAIN")
    private String airIdxMain;

    @JacksonXmlProperty(localName = "AIR_MSG")
    private String airMsg;

    @JacksonXmlProperty(localName = "FCST24HOURS")
    private WeatherForecast24HoursDto fcst24Hours;

    @JacksonXmlProperty(localName = "NEWS_LIST")
    private String newsList;
}
