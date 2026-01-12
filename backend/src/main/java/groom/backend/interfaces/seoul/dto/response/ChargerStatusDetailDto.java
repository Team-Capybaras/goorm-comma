package groom.backend.interfaces.seoul.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

/**
 * 충전소 현황 상세 정보 DTO
 * 
 * XML 구조:
 * <CHARGER_STTS>
 *   <STAT_NM>...</STAT_NM>
 *   <STAT_ID>...</STAT_ID>
 *   <STAT_ADDR>...</STAT_ADDR>
 *   <STAT_X>...</STAT_X>
 *   <STAT_Y>...</STAT_Y>
 *   <STAT_USETIME>...</STAT_USETIME>
 *   <STAT_PARKPAY>...</STAT_PARKPAY>
 *   <STAT_LIMITYN>...</STAT_LIMITYN>
 *   <STAT_LIMITDETAIL>...</STAT_LIMITDETAIL>
 *   <STAT_KINDDETAIL>...</STAT_KINDDETAIL>
 *   <CHARGER_DETAILS>...</CHARGER_DETAILS>
 * </CHARGER_STTS>
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChargerStatusDetailDto {
    
    /**
     * 충전소명
     * XML: <STAT_NM>
     */
    @JacksonXmlProperty(localName = "STAT_NM")
    private String statNm;

    /**
     * 충전소 ID
     * XML: <STAT_ID>
     */
    @JacksonXmlProperty(localName = "STAT_ID")
    private String statId;

    /**
     * 충전소 주소
     * XML: <STAT_ADDR>
     */
    @JacksonXmlProperty(localName = "STAT_ADDR")
    private String statAddr;

    /**
     * 충전소 X좌표 (경도)
     * XML: <STAT_X>
     */
    @JacksonXmlProperty(localName = "STAT_X")
    private String statX;

    /**
     * 충전소 Y좌표 (위도)
     * XML: <STAT_Y>
     */
    @JacksonXmlProperty(localName = "STAT_Y")
    private String statY;

    /**
     * 사용 시간
     * XML: <STAT_USETIME>
     */
    @JacksonXmlProperty(localName = "STAT_USETIME")
    private String statUsetime;

    /**
     * 주차 요금 여부 (Y/N)
     * XML: <STAT_PARKPAY>
     */
    @JacksonXmlProperty(localName = "STAT_PARKPAY")
    private String statParkpay;

    /**
     * 제한 여부 (Y/N)
     * XML: <STAT_LIMITYN>
     */
    @JacksonXmlProperty(localName = "STAT_LIMITYN")
    private String statLimityn;

    /**
     * 제한 상세
     * XML: <STAT_LIMITDETAIL>
     */
    @JacksonXmlProperty(localName = "STAT_LIMITDETAIL")
    private String statLimitdetail;

    /**
     * 종류 상세
     * XML: <STAT_KINDDETAIL>
     */
    @JacksonXmlProperty(localName = "STAT_KINDDETAIL")
    private String statKinddetail;

    /**
     * 충전기 상세 정보
     * XML: <CHARGER_DETAILS>
     */
    @JacksonXmlProperty(localName = "CHARGER_DETAILS")
    private ChargerDetailsDto chargerDetails;
}

