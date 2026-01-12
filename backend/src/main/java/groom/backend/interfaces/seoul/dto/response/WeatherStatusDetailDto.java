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
 *   <SENSIBLE_TEMP>...</SENSIBLE_TEMP>
 *   <MAX_TEMP>...</MAX_TEMP>
 *   <MIN_TEMP>...</MIN_TEMP>
 *   <HUMIDITY>...</HUMIDITY>
 *   <WIND_DIRCT>...</WIND_DIRCT>
 *   <WIND_SPD>...</WIND_SPD>
 *   <PRECIPITATION>...</PRECIPITATION>
 *   <PRECPT_TYPE>...</PRECPT_TYPE>
 *   <PCP_MSG>...</PCP_MSG>
 *   <SUNRISE>...</SUNRISE>
 *   <SUNSET>...</SUNSET>
 *   <UV_INDEX_LVL>...</UV_INDEX_LVL>
 *   <UV_INDEX>...</UV_INDEX>
 *   <UV_MSG>...</UV_MSG>
 *   <PM25_INDEX>...</PM25_INDEX>
 *   <PM25>...</PM25>
 *   <PM10_INDEX>...</PM10_INDEX>
 *   <PM10>...</PM10>
 *   <AIR_IDX>...</AIR_IDX>
 *   <AIR_IDX_MVL>...</AIR_IDX_MVL>
 *   <AIR_IDX_MAIN>...</AIR_IDX_MAIN>
 *   <AIR_MSG>...</AIR_MSG>
 *   <FCST24HOURS>...</FCST24HOURS>
 *   <NEWS_LIST>...</NEWS_LIST>
 * </WEATHER_STTS>
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WeatherStatusDetailDto {
    
    /**
     * 날씨 시간
     * XML: <WEATHER_TIME>
     */
    @JacksonXmlProperty(localName = "WEATHER_TIME")
    private String weatherTime;

    /**
     * 온도
     * XML: <TEMP>
     */
    @JacksonXmlProperty(localName = "TEMP")
    private String temp;

    /**
     * 체감 온도
     * XML: <SENSIBLE_TEMP>
     */
    @JacksonXmlProperty(localName = "SENSIBLE_TEMP")
    private String sensibleTemp;

    /**
     * 최고 온도
     * XML: <MAX_TEMP>
     */
    @JacksonXmlProperty(localName = "MAX_TEMP")
    private String maxTemp;

    /**
     * 최저 온도
     * XML: <MIN_TEMP>
     */
    @JacksonXmlProperty(localName = "MIN_TEMP")
    private String minTemp;

    /**
     * 습도
     * XML: <HUMIDITY>
     */
    @JacksonXmlProperty(localName = "HUMIDITY")
    private String humidity;

    /**
     * 풍향
     * XML: <WIND_DIRCT>
     */
    @JacksonXmlProperty(localName = "WIND_DIRCT")
    private String windDirct;

    /**
     * 풍속
     * XML: <WIND_SPD>
     */
    @JacksonXmlProperty(localName = "WIND_SPD")
    private String windSpd;

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
    private String precptType;

    /**
     * 강수 메시지
     * XML: <PCP_MSG>
     */
    @JacksonXmlProperty(localName = "PCP_MSG")
    private String pcpMsg;

    /**
     * 일출 시간
     * XML: <SUNRISE>
     */
    @JacksonXmlProperty(localName = "SUNRISE")
    private String sunrise;

    /**
     * 일몰 시간
     * XML: <SUNSET>
     */
    @JacksonXmlProperty(localName = "SUNSET")
    private String sunset;

    /**
     * 자외선 지수 레벨
     * XML: <UV_INDEX_LVL>
     */
    @JacksonXmlProperty(localName = "UV_INDEX_LVL")
    private String uvIndexLvl;

    /**
     * 자외선 지수
     * XML: <UV_INDEX>
     */
    @JacksonXmlProperty(localName = "UV_INDEX")
    private String uvIndex;

    /**
     * 자외선 메시지
     * XML: <UV_MSG>
     */
    @JacksonXmlProperty(localName = "UV_MSG")
    private String uvMsg;

    /**
     * 미세먼지(PM2.5) 지수
     * XML: <PM25_INDEX>
     */
    @JacksonXmlProperty(localName = "PM25_INDEX")
    private String pm25Index;

    /**
     * 미세먼지(PM2.5) 수치
     * XML: <PM25>
     */
    @JacksonXmlProperty(localName = "PM25")
    private String pm25;

    /**
     * 초미세먼지(PM10) 지수
     * XML: <PM10_INDEX>
     */
    @JacksonXmlProperty(localName = "PM10_INDEX")
    private String pm10Index;

    /**
     * 초미세먼지(PM10) 수치
     * XML: <PM10>
     */
    @JacksonXmlProperty(localName = "PM10")
    private String pm10;

    /**
     * 대기질 지수
     * XML: <AIR_IDX>
     */
    @JacksonXmlProperty(localName = "AIR_IDX")
    private String airIdx;

    /**
     * 대기질 지수 수치
     * XML: <AIR_IDX_MVL>
     */
    @JacksonXmlProperty(localName = "AIR_IDX_MVL")
    private String airIdxMvl;

    /**
     * 대기질 지수 주요 원인
     * XML: <AIR_IDX_MAIN>
     */
    @JacksonXmlProperty(localName = "AIR_IDX_MAIN")
    private String airIdxMain;

    /**
     * 대기질 메시지
     * XML: <AIR_MSG>
     */
    @JacksonXmlProperty(localName = "AIR_MSG")
    private String airMsg;

    /**
     * 24시간 날씨 예보
     * XML: <FCST24HOURS>
     */
    @JacksonXmlProperty(localName = "FCST24HOURS")
    private WeatherForecast24HoursDto fcst24Hours;

    /**
     * 뉴스 리스트 (빈 문자열 가능)
     * XML: <NEWS_LIST>
     */
    @JacksonXmlProperty(localName = "NEWS_LIST")
    private String newsList;
}

