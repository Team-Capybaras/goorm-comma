package groom.backend.domain.transit.service.impl;

import groom.backend.domain.park.entity.Park;
import groom.backend.domain.park.repository.ParkRepository;
import groom.backend.domain.transit.dto.response.GetTransitResponse;
import groom.backend.domain.transit.entity.*;
import groom.backend.domain.transit.repository.*;
import groom.backend.domain.transit.service.spec.TransitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 대중교통 정보 조회 서비스 구현체
 * 지하철역, 버스 정류장, 공유 자전거 정보를 조회합니다.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TransitServiceImpl implements TransitService {
    private final SubwayStationRepository subwayStationRepository;
    private final SubwayFacilityRepository subwayFacilityRepository;
    private final BusStationRepository busStationRepository;
    private final SbikeRepository sbikeRepository;
    private final SbikeStatusRepository sbikeStatusRepository;
    private final ParkRepository parkRepository;

    /**
     * 지역 코드로 대중교통 정보를 조회합니다.
     *
     * @param areaCode 지역 코드
     * @return 대중교통 정보 (지하철역, 버스 정류장, 공유 자전거)
     */
    @Override
    @Transactional(readOnly = true)
    public GetTransitResponse getTransitByAreaCode(String areaCode) {
        log.info("대중교통 정보 조회 시작 - AREA_CODE: {}", areaCode);

        // 1. 지역 정보 조회
        Optional<Park> parkOptional = parkRepository.findByAreaCode(areaCode);
        if (parkOptional.isEmpty()) {
            log.warn("지역 정보를 찾을 수 없습니다 - AREA_CODE: {}", areaCode);
            return GetTransitResponse.builder()
                    .areaCode(areaCode)
                    .build();
        }

        Park park = parkOptional.get();
        String areaName = park.getAreaName();

        // 2. 지하철역 정보 조회
        List<SubwayStation> subwayStations = subwayStationRepository.findByAreaCode(areaCode);
        List<GetTransitResponse.SubwayStationInfo> subwayStationInfoList = subwayStations.stream()
                .map(subwayStation -> {
                    // 지하철 시설 정보 조회
                    List<SubwayFacility> facilities = subwayFacilityRepository.findBySubId(subwayStation.getSubId());
                    List<GetTransitResponse.SubwayFacilityInfo> facilityInfoList = facilities.stream()
                            .map(facility -> GetTransitResponse.SubwayFacilityInfo.builder()
                                    .subFacilityInfo(facility.getSubFacilityInfo())
                                    .elvtrName(facility.getElvtrName())
                                    .operateSector(facility.getOperateSector())
                                    .installPosition(facility.getInstallPosition())
                                    .useYn(facility.getUseYn())
                                    .elvtrSection(facility.getElvtrSection())
                                    .build())
                            .collect(Collectors.toList());

                    return GetTransitResponse.SubwayStationInfo.builder()
                            .subId(subwayStation.getSubId())
                            .subStnName(subwayStation.getSubStnName())
                            .subStnLine(subwayStation.getSubStnLine())
                            .addr(subwayStation.getAddr())
                            .roadAddr(subwayStation.getRoadAddr())
                            .subStnX(subwayStation.getSubStnX())
                            .subStnY(subwayStation.getSubStnY())
                            .facilities(facilityInfoList)
                            .build();
                })
                .collect(Collectors.toList());

        // 3. 버스 정류장 정보 조회
        List<BusStation> busStations = busStationRepository.findByAreaCode(areaCode);
        List<GetTransitResponse.BusStationInfo> busStationInfoList = busStations.stream()
                .map(busStation -> {
                    String busStnName = busStation.getBusStnName();
                    log.debug("버스 정류장 매핑 - ID: {}, Name: {} (타입: {})", 
                            busStation.getBusStnId(), busStnName, 
                            busStnName != null ? busStnName.getClass().getSimpleName() : "null");
                    
                    return GetTransitResponse.BusStationInfo.builder()
                            .busStnId(busStation.getBusStnId())
                            .busArsId(busStation.getBusArsId())
                            .busStnName(busStnName)
                            .busStnX(busStation.getBusStnX())
                            .busStnY(busStation.getBusStnY())
                            .build();
                })
                .collect(Collectors.toList());

        // 4. 공유 자전거 정보 조회
        List<Sbike> sbikes = sbikeRepository.findByAreaCode(areaCode);
        List<GetTransitResponse.SbikeInfo> sbikeInfoList = sbikes.stream()
                .map(sbike -> {
                    // 공유 자전거 최신 현황 조회
                    Optional<SbikeStatus> statusOptional = sbikeStatusRepository.findLatestBySbikeSpotId(sbike.getSbikeSpotId());
                    GetTransitResponse.SbikeStatusInfo statusInfo = null;
                    if (statusOptional.isPresent()) {
                        SbikeStatus status = statusOptional.get();
                        statusInfo = GetTransitResponse.SbikeStatusInfo.builder()
                                .dataGetTime(status.getDataGetTime())
                                .sbikeParkingPer(status.getSbikeParkingPer())
                                .sbikeParkingCnt(status.getSbikeParkingCnt())
                                .build();
                    }

                    return GetTransitResponse.SbikeInfo.builder()
                            .sbikeSpotId(sbike.getSbikeSpotId())
                            .sbikeSpotName(sbike.getSbikeSpotName())
                            .sbikeCapacity(sbike.getSbikeCapacity())
                            .sbikeX(sbike.getSbikeX())
                            .sbikeY(sbike.getSbikeY())
                            .status(statusInfo)
                            .build();
                })
                .collect(Collectors.toList());

        GetTransitResponse response = GetTransitResponse.builder()
                .areaCode(areaCode)
                .areaName(areaName)
                .subwayStations(subwayStationInfoList)
                .busStations(busStationInfoList)
                .sbikes(sbikeInfoList)
                .build();

        log.info("대중교통 정보 조회 완료 - AREA_CODE: {}, 지하철: {}, 버스: {}, 자전거: {}", 
                areaCode, subwayStationInfoList.size(), busStationInfoList.size(), sbikeInfoList.size());
        return response;
    }
}

