package groom.backend.interfaces.seoul.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

/**
 * 공유 자전거 현황 DTO
 * 
 * XML 구조:
 * <SBIKE_STTS>
 *   <SBIKE_STTS>...</SBIKE_STTS>
 * </SBIKE_STTS>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SharedBikeStatusDto {
    
    @JacksonXmlProperty(localName = "SBIKE_STTS")
    private SharedBikeStatusDetailDto sbikeStts;
}
