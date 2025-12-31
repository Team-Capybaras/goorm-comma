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
 *   ...
 * </BUS_STN_STTS>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BusStationStatusItemDto {
    
    @JacksonXmlProperty(localName = "BUS_RESULT_MSG")
    private String busResultMsg;

    @JacksonXmlProperty(localName = "BUS_STN_ID")
    private String busStnId;

    @JacksonXmlProperty(localName = "BUS_ARS_ID")
    private String busArsId;

    @JacksonXmlProperty(localName = "BUS_STN_NM")
    private String busStnNm;

    @JacksonXmlProperty(localName = "BUS_STN_X")
    private String busStnX;

    @JacksonXmlProperty(localName = "BUS_STN_Y")
    private String busStnY;
}
