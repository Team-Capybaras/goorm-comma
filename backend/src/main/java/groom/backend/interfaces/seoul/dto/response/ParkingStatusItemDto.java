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
 *   <PRK_TYPE>...</PRK_TYPE>
 *   <CPCTY>...</CPCTY>
 *   <CUR_PRK_CNT>...</CUR_PRK_CNT>
 *   <CUR_PRK_TIME>...</CUR_PRK_TIME>
 *   <CUR_PRK_YN>...</CUR_PRK_YN>
 *   <PAY_YN>...</PAY_YN>
 *   <RATES>...</RATES>
 *   <TIME_RATES>...</TIME_RATES>
 *   <ADD_RATES>...</ADD_RATES>
 *   <ADD_TIME_RATES>...</ADD_TIME_RATES>
 *   <ADDRESS>...</ADDRESS>
 *   <ROAD_ADDR>...</ROAD_ADDR>
 *   <LNG>...</LNG>
 *   <LAT>...</LAT>
 * </PRK_STTS>
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ParkingStatusItemDto {
    
    /**
     * 주차장명
     * XML: <PRK_NM>
     */
    @JacksonXmlProperty(localName = "PRK_NM")
    private String prkNm;

    /**
     * 주차장 코드
     * XML: <PRK_CD>
     */
    @JacksonXmlProperty(localName = "PRK_CD")
    private String prkCd;

    /**
     * 주차장 유형 (BP: 건물부설, NW: 노상)
     * XML: <PRK_TYPE>
     */
    @JacksonXmlProperty(localName = "PRK_TYPE")
    private String prkType;

    /**
     * 수용 가능 대수
     * XML: <CPCTY>
     */
    @JacksonXmlProperty(localName = "CPCTY")
    private String cpcty;

    /**
     * 현재 주차 대수
     * XML: <CUR_PRK_CNT>
     */
    @JacksonXmlProperty(localName = "CUR_PRK_CNT")
    private String curPrkCnt;

    /**
     * 현재 주차 시간
     * XML: <CUR_PRK_TIME>
     */
    @JacksonXmlProperty(localName = "CUR_PRK_TIME")
    private String curPrkTime;

    /**
     * 현재 주차 가능 여부 (Y/N)
     * XML: <CUR_PRK_YN>
     */
    @JacksonXmlProperty(localName = "CUR_PRK_YN")
    private String curPrkYn;

    /**
     * 유료 여부 (Y/N)
     * XML: <PAY_YN>
     */
    @JacksonXmlProperty(localName = "PAY_YN")
    private String payYn;

    /**
     * 기본 요금
     * XML: <RATES>
     */
    @JacksonXmlProperty(localName = "RATES")
    private String rates;

    /**
     * 기본 시간 (분)
     * XML: <TIME_RATES>
     */
    @JacksonXmlProperty(localName = "TIME_RATES")
    private String timeRates;

    /**
     * 추가 요금
     * XML: <ADD_RATES>
     */
    @JacksonXmlProperty(localName = "ADD_RATES")
    private String addRates;

    /**
     * 추가 시간 (분)
     * XML: <ADD_TIME_RATES>
     */
    @JacksonXmlProperty(localName = "ADD_TIME_RATES")
    private String addTimeRates;

    /**
     * 주소
     * XML: <ADDRESS>
     */
    @JacksonXmlProperty(localName = "ADDRESS")
    private String address;

    /**
     * 도로명 주소
     * XML: <ROAD_ADDR>
     */
    @JacksonXmlProperty(localName = "ROAD_ADDR")
    private String roadAddr;

    /**
     * 경도
     * XML: <LNG>
     */
    @JacksonXmlProperty(localName = "LNG")
    private String lng;

    /**
     * 위도
     * XML: <LAT>
     */
    @JacksonXmlProperty(localName = "LAT")
    private String lat;
}

