package groom.backend.interfaces.seoul.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

/**
 * 도시 데이터 정보 DTO
 * 
 * XML 구조:
 * <CITYDATA>
 *   <AREA_NM>...</AREA_NM>
 *   <AREA_CD>...</AREA_CD>
 *   <LIVE_PPLTN_STTS>...</LIVE_PPLTN_STTS>
 *   <ROAD_TRAFFIC_STTS>...</ROAD_TRAFFIC_STTS>
 *   <PRK_STTS>...</PRK_STTS>
 *   <SUB_STTS>...</SUB_STTS>
 *   <LIVE_SUB_PPLTN>...</LIVE_SUB_PPLTN>
 *   <BUS_STN_STTS>...</BUS_STN_STTS>
 *   <LIVE_BUS_PPLTN>...</LIVE_BUS_PPLTN>
 *   <SBIKE_STTS>...</SBIKE_STTS>
 *   <WEATHER_STTS>...</WEATHER_STTS>
 *   <CHARGER_STTS>...</CHARGER_STTS>
 *   <LIVE_DST_MESSAGE>...</LIVE_DST_MESSAGE>
 * </CITYDATA>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CityDataDto {
    
    /**
     * 지역명 (예: "잠실한강공원")
     * XML: <AREA_NM>
     */
    @JacksonXmlProperty(localName = "AREA_NM")
    private String areaNm;

    /**
     * 지역 코드 (예: "POI110")
     * XML: <AREA_CD>
     */
    @JacksonXmlProperty(localName = "AREA_CD")
    private String areaCd;

    /**
     * 실시간 인구 현황
     * XML: <LIVE_PPLTN_STTS>
     */
    @JacksonXmlProperty(localName = "LIVE_PPLTN_STTS")
    private LivePopulationStatusDto livePpltnStts;

    /**
     * 도로 교통 현황
     * XML: <ROAD_TRAFFIC_STTS>
     */
    @JacksonXmlProperty(localName = "ROAD_TRAFFIC_STTS")
    private RoadTrafficStatusDto roadTrafficStts;

    /**
     * 주차장 현황 리스트
     * XML: <PRK_STTS>
     */
    @JacksonXmlProperty(localName = "PRK_STTS")
    private ParkingStatusListDto prkStts;

    /**
     * 지하철 현황
     * XML: <SUB_STTS>
     */
    @JacksonXmlProperty(localName = "SUB_STTS")
    private SubwayStatusDto subStts;

    /**
     * 지하철 인구 현황
     * XML: <LIVE_SUB_PPLTN>
     */
    @JacksonXmlProperty(localName = "LIVE_SUB_PPLTN")
    private LiveSubwayPopulationDto liveSubPpltn;

    /**
     * 버스 정류장 현황 리스트
     * XML: <BUS_STN_STTS>
     */
    @JacksonXmlProperty(localName = "BUS_STN_STTS")
    private BusStationStatusListDto busStnStts;

    /**
     * 버스 인구 현황
     * XML: <LIVE_BUS_PPLTN>
     */
    @JacksonXmlProperty(localName = "LIVE_BUS_PPLTN")
    private LiveBusPopulationDto liveBusPpltn;

    /**
     * 사고 통제 현황 (빈 문자열 가능)
     * XML: <ACDNT_CNTRL_STTS>
     */
    @JacksonXmlProperty(localName = "ACDNT_CNTRL_STTS")
    private String acdntCntrlStts;

    /**
     * 공유 자전거 현황
     * XML: <SBIKE_STTS>
     */
    @JacksonXmlProperty(localName = "SBIKE_STTS")
    private SharedBikeStatusDto sbikeStts;

    /**
     * 날씨 현황
     * XML: <WEATHER_STTS>
     */
    @JacksonXmlProperty(localName = "WEATHER_STTS")
    private WeatherStatusDto weatherStts;

    /**
     * 충전소 현황
     * XML: <CHARGER_STTS>
     */
    @JacksonXmlProperty(localName = "CHARGER_STTS")
    private ChargerStatusDto chargerStts;

    /**
     * 이벤트 현황 (빈 문자열 가능)
     * XML: <EVENT_STTS>
     */
    @JacksonXmlProperty(localName = "EVENT_STTS")
    private String eventStts;

    /**
     * 상업 현황 (빈 문자열 가능)
     * XML: <LIVE_CMRCL_STTS>
     */
    @JacksonXmlProperty(localName = "LIVE_CMRCL_STTS")
    private String liveCmrclStts;

    /**
     * 재난 안전 메시지
     * XML: <LIVE_DST_MESSAGE>
     */
    @JacksonXmlProperty(localName = "LIVE_DST_MESSAGE")
    private DisasterMessageDto liveDstMessage;

    /**
     * 뉴스 (빈 문자열 가능)
     * XML: <LIVE_YNA_NEWS>
     */
    @JacksonXmlProperty(localName = "LIVE_YNA_NEWS")
    private String liveYnaNews;
}
