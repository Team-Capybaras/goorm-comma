package groom.backend.application.park.service.impl;

import groom.backend.application.park.dto.response.GetAllParksResponse;
import groom.backend.application.park.dto.response.GetParkResponse;
import groom.backend.application.park.service.spec.ParkApplicationService;
import groom.backend.common.utils.DistanceCalculator;
import groom.backend.domain.park.entity.Park;
import groom.backend.domain.park.entity.ParkTag;
import groom.backend.domain.park.repository.ParkRepository;
import groom.backend.domain.park.repository.ParkTagRepository;
import groom.backend.domain.tag.entity.Tag;
import groom.backend.domain.weather.entity.WeatherStatus;
import groom.backend.domain.weather.repository.WeatherStatusRepository;
import groom.backend.domain.population.entity.LivePopStatus;
import groom.backend.domain.population.repository.LivePopStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 공원 정보 조회 애플리케이션 서비스 구현체
 * 여러 도메인(공원, 날씨, 인구)을 조합하여 공원 정보를 제공합니다.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ParkApplicationServiceImpl implements ParkApplicationService {
    private final ParkRepository parkRepository;
    private final ParkTagRepository parkTagRepository;
    private final WeatherStatusRepository weatherStatusRepository;
    private final LivePopStatusRepository livePopStatusRepository;

    private static final int DEFAULT_SIZE = 10;
    private static final int MAX_SIZE = 100;

    /**
     * 커서 기반 페이지네이션으로 공원 리스트를 조회합니다.
     *
     * @param cursor 커서 (areaCode), 첫 페이지는 null
     * @param size 페이지 크기 (기본값: 10, 최대값: 100)
     * @param longitude 현재 위치 경도 (거리 계산용, 선택)
     * @param latitude 현재 위치 위도 (거리 계산용, 선택)
     * @return 공원 리스트 및 다음 페이지 정보
     */
    @Override
    @Transactional(readOnly = true)
    public GetAllParksResponse getParks(String cursor, Integer size, Double longitude, Double latitude) {
        // size 검증 및 기본값 설정
        int pageSize = (size == null || size <= 0) ? DEFAULT_SIZE : Math.min(size, MAX_SIZE);
        
        log.info("공원 리스트 조회 시작 - cursor: {}, size: {}", cursor, pageSize);

        // 커서 기반 조회 (cursor보다 큰 areaCode를 가진 공원들을 조회)
        // size + 1개 조회하여 다음 페이지 존재 여부 확인
        Pageable pageable = PageRequest.of(0, pageSize + 1);
        List<Park> parks;
        if (cursor == null || cursor.trim().isEmpty()) {
            // 첫 페이지: 가장 작은 areaCode부터 조회
            parks = parkRepository.findAllParksOrderByAreaCodeAsc(pageable);
        } else {
            // 다음 페이지: cursor보다 큰 areaCode를 가진 공원들을 조회
            parks = parkRepository.findParksByAreaCodeGreaterThan(cursor, pageable);
        }
        
        log.info("조회된 공원 수: {}, 요청한 size: {}", parks.size(), pageSize);

        // 다음 페이지 존재 여부 확인 (size + 1개 조회했으므로)
        boolean hasNext = parks.size() > pageSize;
        if (hasNext) {
            parks = parks.subList(0, pageSize); // 마지막 하나 제거
        }

        // 다음 커서 설정
        String nextCursor = null;
        if (hasNext && !parks.isEmpty()) {
            nextCursor = parks.get(parks.size() - 1).getAreaCode();
        }

        // DTO 변환 (날씨 및 인구 정보 포함)
        List<GetAllParksResponse.ParkInfo> parkInfoList = parks.stream()
                .map(park -> {
                    // 날씨 정보 조회
                    Optional<WeatherStatus> weatherStatusOptional = 
                            weatherStatusRepository.findTopByAreaCodeOrderByDataGetTimeDesc(park.getAreaCode());
                    
                    // 인구 정보 조회
                    Optional<LivePopStatus> livePopStatusOptional = 
                            livePopStatusRepository.findLatestByAreaCode(park.getAreaCode());

                    // 태그 정보 조회
                    List<ParkTag> parkTags = parkTagRepository.findByAreaCodeWithTag(park.getAreaCode());
                    List<String> tags = parkTags.stream()
                            .map(ParkTag::getTag)
                            .filter(tag -> tag != null)
                            .map(Tag::getTagName)
                            .collect(Collectors.toList());

                    // 거리 계산 (현재 위치와 공원 좌표가 모두 있는 경우)
                    Double distance = null;
                    if (longitude != null && latitude != null
                            && park.getLongitude() != null && park.getLatitude() != null) {
                        double calculatedDistance = DistanceCalculator.calculateDistance(
                                latitude,
                                longitude,
                                park.getLatitude(),
                                park.getLongitude()
                        );
                        // 소수점 첫째자리까지 반올림
                        distance = Math.round(calculatedDistance * 10.0) / 10.0;
                    }

                    GetAllParksResponse.ParkInfo.ParkInfoBuilder builder = GetAllParksResponse.ParkInfo.builder()
                            .areaCode(park.getAreaCode())
                            .areaName(park.getAreaName())
                            .longitude(park.getLongitude())
                            .latitude(park.getLatitude())
                            .distance(distance)
                            .images(park.getImageUrls() != null && !park.getImageUrls().isEmpty() ? park.getImageUrls() : null)
                            .tags(tags.isEmpty() ? null : tags);

                    // 날씨 정보 설정
                    if (weatherStatusOptional.isPresent()) {
                        WeatherStatus weatherStatus = weatherStatusOptional.get();
                        builder.temp(weatherStatus.getTemp())
                                .precptMsg(weatherStatus.getPrecptMsg())
                                .airIndex(weatherStatus.getAirIndex());
                    }

                    // 인구 혼잡도 정보 설정
                    if (livePopStatusOptional.isPresent()) {
                        LivePopStatus livePopStatus = livePopStatusOptional.get();
                        builder.areaCongestLevel(livePopStatus.getAreaCongestLevel());
                    }

                    return builder.build();
                })
                .collect(Collectors.toList());

        GetAllParksResponse response = GetAllParksResponse.builder()
                .parks(parkInfoList)
                .nextCursor(nextCursor)
                .hasNext(hasNext)
                .size(parkInfoList.size())
                .build();

        log.info("공원 리스트 조회 완료 - 조회된 공원 수: {}, 다음 페이지 존재: {}", 
                parkInfoList.size(), hasNext);

        return response;
    }

    /**
     * areaCode로 특정 공원을 조회합니다.
     * 공원 상세페이지가 아닌 지도뷰 공원 상세 정보 조회입니다.
     * @param areaCode 지역 코드
     * @param longitude 현재 위치 경도 (거리 계산용, 선택)
     * @param latitude 현재 위치 위도 (거리 계산용, 선택)
     * @return 공원 정보
     */
    @Override
    @Transactional(readOnly = true)
    public GetParkResponse getParkByAreaCode(String areaCode, Double longitude, Double latitude) {
        log.info("특정 공원 조회 시작 - AREA_CODE: {}", areaCode);

        // 공원 정보 조회
        Optional<Park> parkOptional = parkRepository.findByAreaCode(areaCode);
        if (parkOptional.isEmpty()) {
            log.warn("공원 정보를 찾을 수 없습니다 - AREA_CODE: {}", areaCode);
            throw new RuntimeException("공원 정보를 찾을 수 없습니다: " + areaCode);
        }

        Park park = parkOptional.get();

        // 날씨 정보 조회
        Optional<WeatherStatus> weatherStatusOptional = 
                weatherStatusRepository.findTopByAreaCodeOrderByDataGetTimeDesc(park.getAreaCode());
        
        // 인구 정보 조회
        Optional<LivePopStatus> livePopStatusOptional = 
                livePopStatusRepository.findLatestByAreaCode(park.getAreaCode());

        // 태그 정보 조회
        List<ParkTag> parkTags = parkTagRepository.findByAreaCodeWithTag(park.getAreaCode());
        List<String> tags = parkTags.stream()
                .map(ParkTag::getTag)
                .filter(tag -> tag != null)
                .map(Tag::getTagName)
                .collect(Collectors.toList());

        // 거리 계산 (현재 위치와 공원 좌표가 모두 있는 경우)
        Double distance = null;
        if (longitude != null && latitude != null
                && park.getLongitude() != null && park.getLatitude() != null) {
            double calculatedDistance = DistanceCalculator.calculateDistance(
                    latitude,
                    longitude,
                    park.getLatitude(),
                    park.getLongitude()
            );
            // 소수점 첫째자리까지 반올림
            distance = Math.round(calculatedDistance * 10.0) / 10.0;
        }

        GetParkResponse.ParkInfo.ParkInfoBuilder builder = GetParkResponse.ParkInfo.builder()
                .areaCode(park.getAreaCode())
                .areaName(park.getAreaName())
                .longitude(park.getLongitude())
                .latitude(park.getLatitude())
                .distance(distance)
                .images(park.getImageUrls() != null && !park.getImageUrls().isEmpty() ? park.getImageUrls() : null)
                .tags(tags.isEmpty() ? null : tags);

        // 날씨 정보 설정
        if (weatherStatusOptional.isPresent()) {
            WeatherStatus weatherStatus = weatherStatusOptional.get();
            builder.temp(weatherStatus.getTemp())
                    .precptMsg(weatherStatus.getPrecptMsg())
                    .airIndex(weatherStatus.getAirIndex());
        }

        // 인구 혼잡도 정보 설정
        if (livePopStatusOptional.isPresent()) {
            LivePopStatus livePopStatus = livePopStatusOptional.get();
            builder.areaCongestLevel(livePopStatus.getAreaCongestLevel());
        }

        GetParkResponse.ParkInfo parkInfo = builder.build();

        GetParkResponse response = GetParkResponse.builder()
                .park(parkInfo)
                .build();

        log.info("특정 공원 조회 완료 - AREA_CODE: {}", areaCode);

        return response;
    }
}

