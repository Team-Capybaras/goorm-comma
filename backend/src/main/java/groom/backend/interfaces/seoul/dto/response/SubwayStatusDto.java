package groom.backend.interfaces.seoul.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

/**
 * 지하철 현황 DTO
 * 
 * XML 구조:
 * <SUB_STTS>
 *   <SUB_STTS>
 *     <SUB_STN_NM>...</SUB_STN_NM>
 *     <SUB_STN_LINE>...</SUB_STN_LINE>
 *     ...
 *     <SUB_DETAIL>...</SUB_DETAIL>
 *     <SUB_FACIINFO>...</SUB_FACIINFO>
 *   </SUB_STTS>
 * </SUB_STTS>
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SubwayStatusDto {
    
    /**
     * 지하철 현황 상세 정보
     * XML: <SUB_STTS><SUB_STTS>
     */
    @JacksonXmlProperty(localName = "SUB_STTS")
    private SubwayStatusDetailDto subStts;
}

