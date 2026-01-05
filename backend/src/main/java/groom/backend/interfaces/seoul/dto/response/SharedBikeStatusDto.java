package groom.backend.interfaces.seoul.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

/**
 * 공유 자전거 현황 DTO
 * 
 * XML 구조:
 * <SBIKE_STTS>
 *   <SBIKE_STTS>
 *     <SBIKE_SPOT_NM>...</SBIKE_SPOT_NM>
 *     <SBIKE_SPOT_ID>...</SBIKE_SPOT_ID>
 *     <SBIKE_SHARED>...</SBIKE_SHARED>
 *     <SBIKE_PARKING_CNT>...</SBIKE_PARKING_CNT>
 *     <SBIKE_RACK_CNT>...</SBIKE_RACK_CNT>
 *     <SBIKE_X>...</SBIKE_X>
 *     <SBIKE_Y>...</SBIKE_Y>
 *   </SBIKE_STTS>
 * </SBIKE_STTS>
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SharedBikeStatusDto {
    
    /**
     * 공유 자전거 현황 상세 정보
     * XML: <SBIKE_STTS><SBIKE_STTS>
     */
    @JacksonXmlProperty(localName = "SBIKE_STTS")
    private SharedBikeStatusDetailDto sbikeStts;
}

