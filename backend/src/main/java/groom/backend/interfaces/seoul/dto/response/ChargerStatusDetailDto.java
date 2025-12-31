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
 *   ...
 *   <CHARGER_DETAILS>...</CHARGER_DETAILS>
 * </CHARGER_STTS>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChargerStatusDetailDto {
    
    @JacksonXmlProperty(localName = "STAT_NM")
    private String statNm;

    @JacksonXmlProperty(localName = "STAT_ID")
    private String statId;

    @JacksonXmlProperty(localName = "STAT_ADDR")
    private String statAddr;

    @JacksonXmlProperty(localName = "STAT_X")
    private String statX;

    @JacksonXmlProperty(localName = "STAT_Y")
    private String statY;

    @JacksonXmlProperty(localName = "STAT_USETIME")
    private String statUsetime;

    @JacksonXmlProperty(localName = "STAT_PARKPAY")
    private String statParkpay;

    @JacksonXmlProperty(localName = "STAT_LIMITYN")
    private String statLimityn;

    @JacksonXmlProperty(localName = "STAT_LIMITDETAIL")
    private String statLimitdetail;

    @JacksonXmlProperty(localName = "STAT_KINDDETAIL")
    private String statKinddetail;

    @JacksonXmlProperty(localName = "CHARGER_DETAILS")
    private ChargerDetailsDto chargerDetails;
}
