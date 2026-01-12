package groom.backend.domain.publicdata.mapper;

import groom.backend.domain.park.entity.Park;
import groom.backend.domain.population.entity.LivePopStatus;
import groom.backend.domain.population.entity.PredPopStatus;
import groom.backend.domain.weather.entity.WeatherStatus;
import groom.backend.domain.parking.entity.*;
import groom.backend.domain.transit.entity.*;
import groom.backend.interfaces.seoul.dto.response.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * DTO를 엔티티로 변환하는 매퍼
 */
@Component
@Slf4j
public class PublicDataMapper {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_TIME_SLICEDFORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

    /**
     * Park 엔티티로 변환
     */
    public Park toParkEntity(String areaCode, String areaName) {
        return Park.builder()
                .areaCode(areaCode)
                .areaName(areaName)
                .build();
    }

    /**
     * LivePopStatus 엔티티로 변환
     */
    public LivePopStatus toLivePopStatusEntity(String areaCode, LivePopulationStatusDetailDto detail, LocalDateTime dataGetTime) {
        if (detail == null) {
            return null;
        }

        return LivePopStatus.builder()
                .dataGetTime(dataGetTime)
                .areaCode(areaCode)
                .areaPopMin(parseInteger(detail.getAreaPpltnMin()))
                .areaPopMax(parseInteger(detail.getAreaPpltnMax()))
                .areaCongestLevel(detail.getAreaCongestLvl())
                .areaCongestMsg(detail.getAreaCongestMsg())
                .replaceYn(parseBoolean(detail.getReplaceYn()))
                .popTime(parseDateTime(detail.getPpltnTime()))
                .build();
    }

    /**
     * PredPopStatus 엔티티 리스트로 변환
     */
    public List<PredPopStatus> toPredPopStatusEntityList(String areaCode, PopulationForecastDto forecast, LocalDateTime dataGetTime) {
        List<PredPopStatus> result = new ArrayList<>();
        
        if (forecast == null || forecast.getFcstPpltn() == null) {
            return result;
        }

        for (PopulationForecastItemDto item : forecast.getFcstPpltn()) {
            PredPopStatus predPopStatus = PredPopStatus.builder()
                    .dataGetTime(dataGetTime)
                    .areaCode(areaCode)
                    .forecastTime(parseDateTime(item.getFcstTime()))
                    .forecastCongestLevel(item.getFcstCongestLvl())
                    .forecastPopMin(parseInteger(item.getFcstPpltnMin()))
                    .forecastPopMax(parseInteger(item.getFcstPpltnMax()))
                    .build();
            result.add(predPopStatus);
        }

        return result;
    }

    /**
     * WeatherStatus 엔티티로 변환
     */
    public WeatherStatus toWeatherStatusEntity(String areaCode, WeatherStatusDetailDto detail, LocalDateTime dataGetTime) {
        if (detail == null) {
            return null;
        }

        // 추출 시간.
        // 형식 : YYYYMMDDhhmm
        // 따라서 localdatetime sliced로 처리 필요.
        LocalDate baseDate = parseDateTime(detail.getFcst24Hours()
                .getFcst24Hours().getFirst().getForecastDateTime()).toLocalDate();


        // 강수확률 추출
        int maxRainChance =
                detail.getFcst24Hours()
                        .getFcst24Hours()
                        .stream()
                        .filter(fcst -> {
                                    LocalDateTime t = parseDateTime(fcst.getForecastDateTime());
                                    return t != null && t.toLocalDate().equals(baseDate);
                                }
                        )
                        .map(WeatherForecast24HoursItemDto::getRainChance)
                        .filter(Objects::nonNull)
                        .mapToInt(Integer::parseInt)
                        .max()
                        .orElse(0);

        return WeatherStatus.builder()
                .dataGetTime(dataGetTime)
                .areaCode(areaCode)
                .weatherTime(parseDateTime(detail.getWeatherTime()))
                .temp(parseFloat(detail.getTemp()))
                .sensibleTemp(parseFloat(detail.getSensibleTemp()))
                .humidity(parseInteger(detail.getHumidity()))
                .windDirct(detail.getWindDirct())
                .windSpd(parseFloat(detail.getWindSpd()))
                .precipitation(detail.getPrecipitation())
                .rainChance(maxRainChance)
                .precptType(detail.getPrecptType())
                .precptMsg(detail.getPcpMsg())  // DTO는 pcpMsg, 엔티티는 precptMsg
                .uvIndexLevel(parseInteger(detail.getUvIndexLvl()))
                .uvIndex(detail.getUvIndex())
                .pm25Index(detail.getPm25Index())
                .pm25(parseInteger(detail.getPm25()))
                .pm10Index(detail.getPm10Index())
                .pm10(parseInteger(detail.getPm10()))
                .airIndex(detail.getAirIdx())  // DTO는 airIdx, 엔티티는 airIndex
                .airIndexLevel(parseFloat(detail.getAirIdxMvl()))  // DTO는 airIdxMvl, 엔티티는 airIndexLevel
                .airIndexMain(detail.getAirIdxMain())
                .airMsg(detail.getAirMsg())
                .dataSource(detail.getNewsList())  // DTO는 newsList, 엔티티는 dataSource
                .build();
    }

    // 유틸리티 메서드들
    private Integer parseInteger(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            log.warn("정수 파싱 실패: {}", value);
            return null;
        }
    }

    private BigDecimal parseBigDecimal(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return new BigDecimal(value.trim());
        } catch (NumberFormatException e) {
            log.warn("BigDecimal 파싱 실패: {}", value);
            return null;
        }
    }

    private Float parseFloat(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return Float.parseFloat(value.trim());
        } catch (NumberFormatException e) {
            log.warn("Float 파싱 실패: {}", value);
            return null;
        }
    }

    private Boolean parseBoolean(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        String trimmed = value.trim().toUpperCase();
        return "Y".equals(trimmed) || "YES".equals(trimmed) || "TRUE".equals(trimmed) || "1".equals(trimmed);
    }

    private LocalDateTime parseDateTime(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            String trimmed = value.trim();
            // "yyyy-MM-dd HH:mm" 형식도 처리
            if (trimmed.length() == 16) {
                trimmed = trimmed + ":00";
            }
            if (trimmed.length() == 12) {
                return LocalDateTime.parse(trimmed, DATE_TIME_SLICEDFORMATTER);
            }
            return LocalDateTime.parse(trimmed, DATE_TIME_FORMATTER);
        } catch (Exception e) {
            log.warn("날짜 파싱 실패: {}", value);
            return null;
        }
    }

    /**
     * ParkingLot 엔티티로 변환
     */
    public ParkingLot toParkingLotEntity(String areaCode, ParkingStatusItemDto item) {
        if (item == null || item.getPrkCd() == null || item.getPrkCd().trim().isEmpty()) {
            return null;
        }

        return ParkingLot.builder()
                .prkCode(parseLong(item.getPrkCd()))
                .prkName(item.getPrkNm())
                .prkType(item.getPrkType())
                .capacity(parseInteger(item.getCpcty()))
                .currentInfoYn(parseBoolean(item.getCurPrkYn()))
                .payYn(parseBoolean(item.getPayYn()))
                .rates(parseInteger(item.getRates()))
                .timeRates(parseInteger(item.getTimeRates()))
                .addRates(parseInteger(item.getAddRates()))
                .addTimeRates(parseInteger(item.getAddTimeRates()))
                .addr(item.getAddress())
                .roadAddr(item.getRoadAddr())
                .prkX(parseBigDecimal(item.getLng()))
                .prkY(parseBigDecimal(item.getLat()))
                .areaCode(areaCode)
                .build();
    }

    /**
     * ParkingLotStatus 엔티티로 변환
     */
    public ParkingLotStatus toParkingLotStatusEntity(Long prkCode, ParkingStatusItemDto item, LocalDateTime dataGetTime) {
        if (item == null || item.getCurPrkYn() == null || !"Y".equalsIgnoreCase(item.getCurPrkYn().trim())) {
            return null;
        }

        return ParkingLotStatus.builder()
                .dataGetTime(dataGetTime)
                .prkCode(prkCode)
                .currentPrkCnt(parseInteger(item.getCurPrkCnt()))
                .currentPrkTime(parseDateTime(item.getCurPrkTime()))
                .build();
    }

    /**
     * SubwayStation 엔티티로 변환
     * subId는 areaCode, subStnNm, subStnLine의 해시코드로 생성
     * 같은 지하철역명과 노선이라도 areaCode가 다르면 다른 것으로 판단
     */
    public SubwayStation toSubwayStationEntity(String areaCode, SubwayStatusDetailDto detail) {
        if (detail == null || detail.getSubStnNm() == null || detail.getSubStnNm().trim().isEmpty()) {
            return null;
        }

        // subId 생성: areaCode, subStnNm, subStnLine의 조합으로 해시코드 생성
        String key = (areaCode + "_" + detail.getSubStnNm() + "_" + (detail.getSubStnLine() != null ? detail.getSubStnLine() : "")).trim();
        Integer subId = Math.abs(key.hashCode());

        // 지번 주소 처리: 빈 문자열이면 null로 저장
        String addr = detail.getSubStnJibun();
        if (addr != null && addr.trim().isEmpty()) {
            addr = null;
        }
        
        // 도로명 주소 처리: 빈 문자열이면 null로 저장
        String roadAddr = detail.getSubStnRaddr();
        if (roadAddr != null && roadAddr.trim().isEmpty()) {
            roadAddr = null;
        }
        
        log.debug("지하철역 매핑 - 역명: {}, 지번주소: {}, 도로명주소: {}", 
                detail.getSubStnNm(), addr, roadAddr);
        
        return SubwayStation.builder()
                .subId(subId)
                .subStnName(detail.getSubStnNm())
                .subStnLine(detail.getSubStnLine())
                .addr(addr)
                .roadAddr(roadAddr)
                .subStnX(parseBigDecimal(detail.getSubStnX()))
                .subStnY(parseBigDecimal(detail.getSubStnY()))
                .areaCode(areaCode)
                .build();
    }

    /**
     * SubwayFacility 엔티티 리스트로 변환
     * subFacilityInfo는 subId, elvtrNm, oprSec, instlPstn의 조합으로 생성
     * 같은 승강기명이라도 areaCode가 다르면 다른 것으로 판단 (subId에 areaCode가 포함됨)
     */
    public List<SubwayFacility> toSubwayFacilityEntityList(Integer subId, SubwayFacilityInfoListDto facilityList) {
        List<SubwayFacility> result = new ArrayList<>();
        
        if (facilityList == null || facilityList.getSubFaciinfo() == null) {
            return result;
        }

        for (SubwayFacilityInfoItemDto item : facilityList.getSubFaciinfo()) {
            // subFacilityInfo 생성: subId(이미 areaCode 포함), elvtrNm, oprSec, instlPstn의 조합으로 고유 ID 생성
            // 같은 승강기명(ELVTR_NM)이라도 areaCode가 다르면 다른 것으로 판단 (subId에 areaCode 포함)
            String facilityKey = subId + "_" + 
                    (item.getElvtrNm() != null ? item.getElvtrNm() : "") + "_" +
                    (item.getOprSec() != null ? item.getOprSec() : "") + "_" +
                    (item.getInstlPstn() != null ? item.getInstlPstn() : "");
            Integer subFacilityInfo = Math.abs(facilityKey.hashCode());
            
            SubwayFacility facility = SubwayFacility.builder()
                    .subFacilityInfo(subFacilityInfo)
                    .elvtrName(item.getElvtrNm())
                    .operateSector(item.getOprSec())
                    .installPosition(item.getInstlPstn())
                    .useYn(item.getUseYn())
                    .elvtrSection(item.getElvtrSe())
                    .subId(subId)
                    .build();
            result.add(facility);
        }

        return result;
    }

    /**
     * BusStation 엔티티로 변환
     */
    public BusStation toBusStationEntity(String areaCode, BusStationStatusItemDto item) {
        if (item == null || item.getBusStnId() == null || item.getBusStnId().trim().isEmpty()) {
            return null;
        }

        return BusStation.builder()
                .busStnId(parseInteger(item.getBusStnId()))
                .busArsId(parseInteger(item.getBusArsId()))
                .busStnName(item.getBusStnNm()) // 버스 정류장명 (문자열)
                .busStnX(parseBigDecimal(item.getBusStnX()))
                .busStnY(parseBigDecimal(item.getBusStnY()))
                .areaCode(areaCode)
                .build();
    }

    /**
     * Sbike 엔티티로 변환
     */
    public Sbike toSbikeEntity(String areaCode, SharedBikeStatusDetailDto detail) {
        if (detail == null || detail.getSbikeSpotId() == null || detail.getSbikeSpotId().trim().isEmpty()) {
            return null;
        }

        return Sbike.builder()
                .sbikeSpotId(detail.getSbikeSpotId())
                .sbikeSpotName(detail.getSbikeSpotNm())
                .sbikeCapacity(parseInteger(detail.getSbikeRackCnt())) // rackCnt를 capacity로 사용
                .sbikeX(parseBigDecimal(detail.getSbikeX()))
                .sbikeY(parseBigDecimal(detail.getSbikeY()))
                .areaCode(areaCode)
                .build();
    }

    /**
     * SbikeStatus 엔티티로 변환
     */
    public SbikeStatus toSbikeStatusEntity(String sbikeSpotId, SharedBikeStatusDetailDto detail, LocalDateTime dataGetTime) {
        if (detail == null || sbikeSpotId == null) {
            return null;
        }

        // 주차 비율 계산: (주차 대수 / 거치대 개수) * 100
        Integer parkingCnt = parseInteger(detail.getSbikeParkingCnt());
        Integer rackCnt = parseInteger(detail.getSbikeRackCnt());
        Integer parkingPer = null;
        if (parkingCnt != null && rackCnt != null && rackCnt > 0) {
            parkingPer = (parkingCnt * 100) / rackCnt;
        }

        return SbikeStatus.builder()
                .dataGetTime(dataGetTime)
                .sbikeSpotId(sbikeSpotId)
                .sbikeParkingPer(parkingPer)
                .sbikeParkingCnt(parkingCnt)
                .build();
    }

    /**
     * ChargerStation 엔티티로 변환
     */
    public ChargerStation toChargerStationEntity(String areaCode, ChargerStatusDetailDto detail) {
        if (detail == null || detail.getStatId() == null || detail.getStatId().trim().isEmpty()) {
            return null;
        }

        return ChargerStation.builder()
                .stationId(detail.getStatId())
                .stationName(detail.getStatNm())
                .stationAddr(detail.getStatAddr())
                .stationX(parseBigDecimal(detail.getStatX()))
                .stationY(parseBigDecimal(detail.getStatY()))
                .stationUsetime(detail.getStatUsetime())
                .stationParkpay(parseBoolean(detail.getStatParkpay()))
                .stationLimitDetail(detail.getStatLimitdetail())
                .stationKindDetail(detail.getStatKinddetail())
                .areaCode(areaCode)
                .build();
    }

    /**
     * ChargerDetail 엔티티로 변환
     */
    public ChargerDetail toChargerDetailEntity(String stationId, ChargerDetailItemDto item) {
        if (item == null || item.getChargerId() == null || item.getChargerId().trim().isEmpty()) {
            return null;
        }

        return ChargerDetail.builder()
                .chargerId(parseInteger(item.getChargerId()))
                .stationId(stationId)
                .chargerType(item.getChargerType())
                .chargerUpdated(parseDateTime(item.getStatuupddt()))
                .chargerTimestamp(parseDateTime(item.getNowtsdt()))
                .output(parseInteger(item.getOutput()))
                .method(item.getMethod())
                .build();
    }

    /**
     * ChargerStatus 엔티티로 변환
     */
    public ChargerStatus toChargerStatusEntity(Integer chargerId, String stationId, ChargerDetailItemDto item, LocalDateTime dataGetTime) {
        if (item == null || chargerId == null || stationId == null) {
            return null;
        }

        // chargerStatKey 생성: chargerId와 stationId의 해시코드 조합
        String key = chargerId + "_" + stationId + "_" + dataGetTime.toString();
        Integer chargerStatKey = Math.abs(key.hashCode());

        return ChargerStatus.builder()
                .chargerStatKey(chargerStatKey)
                .chargerStatus(item.getChargerStat())
                .statusUpdated(parseDateTime(item.getStatuupddt()))
                .statusTimestamp(parseDateTime(item.getNowtsdt()))
                .dataGetTime(dataGetTime)
                .chargerId(chargerId)
                .stationId(stationId)
                .build();
    }

    private Long parseLong(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return Long.parseLong(value.trim());
        } catch (NumberFormatException e) {
            log.warn("Long 파싱 실패: {}", value);
            return null;
        }
    }

}

