package groom.backend.interfaces.seoul.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

import java.util.List;

/**
 * 지하철 상세 정보 리스트 DTO
 * 
 * XML 구조:
 * <SUB_DETAIL>
 *   <SUB_DETAIL>...</SUB_DETAIL>
 *   ...
 * </SUB_DETAIL>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SubwayDetailListDto {
    
    @JacksonXmlProperty(localName = "SUB_DETAIL")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<SubwayDetailItemDto> subDetail;
}
