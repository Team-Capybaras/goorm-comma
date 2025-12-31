package groom.backend.interfaces.seoul.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

/**
 * 주차장 현황 항목 DTO
 * 
 * XML 구조:
 * <PRK_STTS>
 *   <PRK_NM>...</PRK_NM>
 *   <PRK_CD>...</PRK_CD>
 *   ...
 * </PRK_STTS>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ParkingStatusItemDto {
    
    @JacksonXmlProperty(localName = "PRK_NM")
    private String prkNm;

    @JacksonXmlProperty(localName = "PRK_CD")
    private String prkCd;

    @JacksonXmlProperty(localName = "PRK_TYPE")
    private String prkType;

    @JacksonXmlProperty(localName = "CPCTY")
    private String cpcty;

    @JacksonXmlProperty(localName = "CUR_PRK_CNT")
    private String curPrkCnt;

    @JacksonXmlProperty(localName = "CUR_PRK_TIME")
    private String curPrkTime;

    @JacksonXmlProperty(localName = "CUR_PRK_YN")
    private String curPrkYn;

    @JacksonXmlProperty(localName = "PAY_YN")
    private String payYn;

    @JacksonXmlProperty(localName = "RATES")
    private String rates;

    @JacksonXmlProperty(localName = "TIME_RATES")
    private String timeRates;

    @JacksonXmlProperty(localName = "ADD_RATES")
    private String addRates;

    @JacksonXmlProperty(localName = "ADD_TIME_RATES")
    private String addTimeRates;

    @JacksonXmlProperty(localName = "ADDRESS")
    private String address;

    @JacksonXmlProperty(localName = "ROAD_ADDR")
    private String roadAddr;

    @JacksonXmlProperty(localName = "LNG")
    private String lng;

    @JacksonXmlProperty(localName = "LAT")
    private String lat;
}
