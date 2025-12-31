package groom.backend.interfaces.seoul.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

/**
 * 버스 정류장 현황 항목 DTO
 * 
 * XML 구조:
 * <BUS_STN_STTS>
 *   <BUS_RESULT_MSG>...</BUS_RESULT_MSG>
 *   <BUS_STN_ID>...</BUS_STN_ID>
 *   <BUS_ARS_ID>...</BUS_ARS_ID>
 *   <BUS_STN_NM>...</BUS_STN_NM>
 *   <BUS_STN_X>...</BUS_STN_X>
 *   <BUS_STN_Y>...</BUS_STN_Y>
 * </BUS_STN_STTS>
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BusStationStatusItemDto {
    
    /**
     * 버스 결과 메시지
     * XML: <BUS_RESULT_MSG>
     */
    @JacksonXmlProperty(localName = "BUS_RESULT_MSG")
    private String busResultMsg;

    /**
     * 버스 정류장 ID
     * XML: <BUS_STN_ID>
     */
    @JacksonXmlProperty(localName = "BUS_STN_ID")
    private String busStnId;

    /**
     * 버스 ARS ID
     * XML: <BUS_ARS_ID>
     */
    @JacksonXmlProperty(localName = "BUS_ARS_ID")
    private String busArsId;

    /**
     * 버스 정류장명
     * XML: <BUS_STN_NM>
     */
    @JacksonXmlProperty(localName = "BUS_STN_NM")
    private String busStnNm;

    /**
     * 버스 정류장 X좌표 (경도)
     * XML: <BUS_STN_X>
     */
    @JacksonXmlProperty(localName = "BUS_STN_X")
    private String busStnX;

    /**
     * 버스 정류장 Y좌표 (위도)
     * XML: <BUS_STN_Y>
     */
    @JacksonXmlProperty(localName = "BUS_STN_Y")
    private String busStnY;
}

