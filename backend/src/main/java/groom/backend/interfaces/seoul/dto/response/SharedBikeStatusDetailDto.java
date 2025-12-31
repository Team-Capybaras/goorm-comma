package groom.backend.interfaces.seoul.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

/**
 * 공유 자전거 현황 상세 정보 DTO
 * 
 * XML 구조:
 * <SBIKE_STTS>
 *   <SBIKE_SPOT_NM>...</SBIKE_SPOT_NM>
 *   <SBIKE_SPOT_ID>...</SBIKE_SPOT_ID>
 *   ...
 * </SBIKE_STTS>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SharedBikeStatusDetailDto {
    
    @JacksonXmlProperty(localName = "SBIKE_SPOT_NM")
    private String sbikeSpotNm;

    @JacksonXmlProperty(localName = "SBIKE_SPOT_ID")
    private String sbikeSpotId;

    @JacksonXmlProperty(localName = "SBIKE_SHARED")
    private String sbikeShared;

    @JacksonXmlProperty(localName = "SBIKE_PARKING_CNT")
    private String sbikeParkingCnt;

    @JacksonXmlProperty(localName = "SBIKE_RACK_CNT")
    private String sbikeRackCnt;

    @JacksonXmlProperty(localName = "SBIKE_X")
    private String sbikeX;

    @JacksonXmlProperty(localName = "SBIKE_Y")
    private String sbikeY;
}
