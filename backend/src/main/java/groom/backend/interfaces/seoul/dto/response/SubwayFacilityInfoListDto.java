package groom.backend.interfaces.seoul.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

import java.util.List;

/**
 * 지하철 시설 정보 리스트 DTO
 * 
 * XML 구조:
 * <SUB_FACIINFO>
 *   <SUB_FACIINFO>
 *     <ELVTR_NM>...</ELVTR_NM>
 *     <OPR_SEC>...</OPR_SEC>
 *     ...
 *   </SUB_FACIINFO>
 *   ...
 * </SUB_FACIINFO>
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SubwayFacilityInfoListDto {
    
    /**
     * 지하철 시설 정보 항목 리스트
     * XML: <SUB_FACIINFO><SUB_FACIINFO>...</SUB_FACIINFO></SUB_FACIINFO>
     */
    @JacksonXmlProperty(localName = "SUB_FACIINFO")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<SubwayFacilityInfoItemDto> subFaciinfo;
}

