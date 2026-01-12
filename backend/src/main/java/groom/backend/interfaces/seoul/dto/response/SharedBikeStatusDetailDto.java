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
 *   <SBIKE_SHARED>...</SBIKE_SHARED>
 *   <SBIKE_PARKING_CNT>...</SBIKE_PARKING_CNT>
 *   <SBIKE_RACK_CNT>...</SBIKE_RACK_CNT>
 *   <SBIKE_X>...</SBIKE_X>
 *   <SBIKE_Y>...</SBIKE_Y>
 * </SBIKE_STTS>
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SharedBikeStatusDetailDto {
    
    /**
     * 공유 자전거 스팟명
     * XML: <SBIKE_SPOT_NM>
     */
    @JacksonXmlProperty(localName = "SBIKE_SPOT_NM")
    private String sbikeSpotNm;

    /**
     * 공유 자전거 스팟 ID
     * XML: <SBIKE_SPOT_ID>
     */
    @JacksonXmlProperty(localName = "SBIKE_SPOT_ID")
    private String sbikeSpotId;

    /**
     * 공유 가능 대수
     * XML: <SBIKE_SHARED>
     */
    @JacksonXmlProperty(localName = "SBIKE_SHARED")
    private String sbikeShared;

    /**
     * 주차 대수
     * XML: <SBIKE_PARKING_CNT>
     */
    @JacksonXmlProperty(localName = "SBIKE_PARKING_CNT")
    private String sbikeParkingCnt;

    /**
     * 거치대 개수
     * XML: <SBIKE_RACK_CNT>
     */
    @JacksonXmlProperty(localName = "SBIKE_RACK_CNT")
    private String sbikeRackCnt;

    /**
     * X좌표 (경도)
     * XML: <SBIKE_X>
     */
    @JacksonXmlProperty(localName = "SBIKE_X")
    private String sbikeX;

    /**
     * Y좌표 (위도)
     * XML: <SBIKE_Y>
     */
    @JacksonXmlProperty(localName = "SBIKE_Y")
    private String sbikeY;
}

