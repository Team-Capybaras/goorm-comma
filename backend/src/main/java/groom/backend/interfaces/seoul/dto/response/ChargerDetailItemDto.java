package groom.backend.interfaces.seoul.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

/**
 * 충전기 상세 정보 항목 DTO
 * 
 * XML 구조:
 * <CHARGER_DETAILS>
 *   <CHARGER_ID>...</CHARGER_ID>
 *   <CHARGER_TYPE>...</CHARGER_TYPE>
 *   <CHARGER_STAT>...</CHARGER_STAT>
 *   <STATUPDDT>...</STATUPDDT>
 *   <LASTTSDT>...</LASTTSDT>
 *   <LASTTEDT>...</LASTTEDT>
 *   <NOWTSDT>...</NOWTSDT>
 *   <OUTPUT>...</OUTPUT>
 *   <METHOD>...</METHOD>
 * </CHARGER_DETAILS>
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChargerDetailItemDto {
    
    /**
     * 충전기 ID
     * XML: <CHARGER_ID>
     */
    @JacksonXmlProperty(localName = "CHARGER_ID")
    private String chargerId;

    /**
     * 충전기 유형 (AC완속, DC급속 등)
     * XML: <CHARGER_TYPE>
     */
    @JacksonXmlProperty(localName = "CHARGER_TYPE")
    private String chargerType;

    /**
     * 충전기 상태 (사용가능, 사용중 등)
     * XML: <CHARGER_STAT>
     */
    @JacksonXmlProperty(localName = "CHARGER_STAT")
    private String chargerStat;

    /**
     * 상태 업데이트 일시
     * XML: <STATUPDDT>
     */
    @JacksonXmlProperty(localName = "STATUPDDT")
    private String statuupddt;

    /**
     * 마지막 충전 시작 일시
     * XML: <LASTTSDT>
     */
    @JacksonXmlProperty(localName = "LASTTSDT")
    private String lasttsdt;

    /**
     * 마지막 충전 종료 일시
     * XML: <LASTTEDT>
     */
    @JacksonXmlProperty(localName = "LASTTEDT")
    private String lasttedt;

    /**
     * 현재 충전 시작 일시
     * XML: <NOWTSDT>
     */
    @JacksonXmlProperty(localName = "NOWTSDT")
    private String nowtsdt;

    /**
     * 출력 (kW)
     * XML: <OUTPUT>
     */
    @JacksonXmlProperty(localName = "OUTPUT")
    private String output;

    /**
     * 충전 방식 (단독, 동시 등)
     * XML: <METHOD>
     */
    @JacksonXmlProperty(localName = "METHOD")
    private String method;
}

