package groom.backend.application.park.service.impl;

import groom.backend.application.park.dto.response.GetAllParksResponse;
import groom.backend.application.park.dto.response.GetParkResponse;
import groom.backend.application.park.service.spec.ParkApplicationService;
import groom.backend.common.utils.DistanceCalculator;
import groom.backend.domain.park.entity.Park;
import groom.backend.domain.park.entity.ParkFeature;
import groom.backend.domain.park.entity.ParkTag;
import groom.backend.domain.park.repository.ParkFeatureRepository;
import groom.backend.domain.park.repository.ParkRepository;
import groom.backend.domain.park.repository.ParkTagRepository;
import groom.backend.domain.tag.entity.Tag;
import groom.backend.domain.weather.entity.WeatherStatus;
import groom.backend.domain.weather.repository.WeatherStatusRepository;
import groom.backend.domain.population.entity.LivePopStatus;
import groom.backend.domain.population.repository.LivePopStatusRepository;
import groom.backend.application.avoidance.service.spec.CongestionRecommendService;
import groom.backend.domain.avoidance.enums.Weekday;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    private final CongestionRecommendService congestionRecommendService;
    private final ParkFeatureRepository parkFeatureRepository;

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

                    // 여유 예상 시간 계산
                    String recommendedVisitHour = calculateRecommendedVisitHour(park.getAreaCode());
                    builder.recommendedVisitHour(recommendedVisitHour);

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

        // 설명 정보 조회
        List<ParkFeature> parkFeatures = parkFeatureRepository.findByAreaCode(park.getAreaCode());
        List<GetParkResponse.ParkInfo.Feature> features = parkFeatures.stream()
                .map(pf -> new GetParkResponse.ParkInfo.Feature(
                        pf.getType(),
                        pf.getDescription()
                ))
                .toList();


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
                .tags(tags.isEmpty() ? null : tags)
                .address(park.getParkAddr())
                .features(features);

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

        // 여유 예상 시간 계산
        String recommendedVisitHour = calculateRecommendedVisitHour(park.getAreaCode());
        builder.recommendedVisitHour(recommendedVisitHour);

        // 주소 필드 (null로 설정)
        builder.address(null);

        GetParkResponse.ParkInfo parkInfo = builder.build();

        GetParkResponse response = GetParkResponse.builder()
                .park(parkInfo)
                .build();

        log.info("특정 공원 조회 완료 - AREA_CODE: {}", areaCode);

        return response;
    }

    /**
     * 혼잡도가 낮은 순으로 공원 리스트를 조회합니다 (커서 기반 페이지네이션).
     *
     * @param cursor 커서 (areaCode), 첫 페이지는 null
     * @param size 페이지 크기 (기본값: 10, 최대값: 100)
     * @param longitude 현재 위치 경도 (거리 계산용, 선택)
     * @param latitude 현재 위치 위도 (거리 계산용, 선택)
     * @return 공원 리스트 및 다음 페이지 정보 (혼잡도 낮은 순)
     */
    @Override
    @Transactional(readOnly = true)
    public GetAllParksResponse getParksByLowCongestion(String cursor, Integer size, Double longitude, Double latitude) {
        // size 검증 및 기본값 설정
        int pageSize = (size == null || size <= 0) ? DEFAULT_SIZE : Math.min(size, MAX_SIZE);
        
        log.info("혼잡도 낮은 순 공원 리스트 조회 시작 - cursor: {}, size: {}", cursor, pageSize);

        // 모든 공원 조회
        List<Park> allParks = parkRepository.findAllByOrderByAreaCode();
        
        // 각 공원의 혼잡도 정보를 조회하고 정렬
        List<GetAllParksResponse.ParkInfo> allParkInfoList = allParks.stream()
                .map(park -> {
                    // 인구 정보 조회 (혼잡도 레벨 확인용)
                    Optional<LivePopStatus> livePopStatusOptional = 
                            livePopStatusRepository.findLatestByAreaCode(park.getAreaCode());
                    
                    // 날씨 정보 조회
                    Optional<WeatherStatus> weatherStatusOptional = 
                            weatherStatusRepository.findTopByAreaCodeOrderByDataGetTimeDesc(park.getAreaCode());
                    
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

                    // 여유 예상 시간 계산
                    String recommendedVisitHour = calculateRecommendedVisitHour(park.getAreaCode());
                    builder.recommendedVisitHour(recommendedVisitHour);

                    return builder.build();
                })
                .sorted((p1, p2) -> {
                    // 혼잡도 레벨을 숫자로 변환하여 비교
                    int level1 = getCongestionLevelOrder(p1.getAreaCongestLevel());
                    int level2 = getCongestionLevelOrder(p2.getAreaCongestLevel());
                    
                    // 혼잡도가 낮은 순으로 정렬 (숫자가 작을수록 낮은 혼잡도)
                    int compare = Integer.compare(level1, level2);
                    
                    // 혼잡도가 같으면 areaCode로 정렬
                    if (compare == 0) {
                        String code1 = p1.getAreaCode() != null ? p1.getAreaCode() : "";
                        String code2 = p2.getAreaCode() != null ? p2.getAreaCode() : "";
                        compare = code1.compareTo(code2);
                    }
                    
                    return compare;
                })
                .collect(Collectors.toList());

        // 커서 기반 페이지네이션 적용
        int startIndex = 0;
        if (cursor != null && !cursor.trim().isEmpty()) {
            // cursor 이후의 인덱스 찾기
            for (int i = 0; i < allParkInfoList.size(); i++) {
                if (allParkInfoList.get(i).getAreaCode().equals(cursor)) {
                    startIndex = i + 1;
                    break;
                }
            }
        }

        // 페이지 크기 + 1개 조회하여 다음 페이지 존재 여부 확인
        int endIndex = Math.min(startIndex + pageSize + 1, allParkInfoList.size());
        List<GetAllParksResponse.ParkInfo> parkInfoList = allParkInfoList.subList(startIndex, endIndex);

        // 다음 페이지 존재 여부 확인
        boolean hasNext = parkInfoList.size() > pageSize;
        if (hasNext) {
            parkInfoList = parkInfoList.subList(0, pageSize); // 마지막 하나 제거
        }

        // 다음 커서 설정
        String nextCursor = null;
        if (hasNext && !parkInfoList.isEmpty()) {
            nextCursor = parkInfoList.get(parkInfoList.size() - 1).getAreaCode();
        }

        GetAllParksResponse response = GetAllParksResponse.builder()
                .parks(parkInfoList)
                .nextCursor(nextCursor)
                .hasNext(hasNext)
                .size(parkInfoList.size())
                .build();

        log.info("혼잡도 낮은 순 공원 리스트 조회 완료 - 조회된 공원 수: {}, 다음 페이지 존재: {}", 
                parkInfoList.size(), hasNext);

        return response;
    }

    /**
     * 거리가 가까운 순으로 공원 리스트를 조회합니다 (커서 기반 페이지네이션).
     *
     * @param cursor 커서 (areaCode), 첫 페이지는 null
     * @param size 페이지 크기 (기본값: 10, 최대값: 100)
     * @param longitude 현재 위치 경도 (거리 계산용, 필수)
     * @param latitude 현재 위치 위도 (거리 계산용, 필수)
     * @return 공원 리스트 및 다음 페이지 정보 (거리 가까운 순)
     */
    @Override
    @Transactional(readOnly = true)
    public GetAllParksResponse getParksByDistance(String cursor, Integer size, Double longitude, Double latitude) {
        // 거리 계산을 위해 위치 정보 필수
        if (longitude == null || latitude == null) {
            throw new IllegalArgumentException("거리 정렬을 위해서는 longitude와 latitude가 필수입니다.");
        }

        // size 검증 및 기본값 설정
        int pageSize = (size == null || size <= 0) ? DEFAULT_SIZE : Math.min(size, MAX_SIZE);
        
        log.info("거리 가까운 순 공원 리스트 조회 시작 - cursor: {}, size: {}, longitude: {}, latitude: {}", 
                cursor, pageSize, longitude, latitude);

        // 모든 공원 조회
        List<Park> allParks = parkRepository.findAllByOrderByAreaCode();
        
        // 각 공원의 거리를 계산하고 정렬
        List<GetAllParksResponse.ParkInfo> allParkInfoList = allParks.stream()
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

                    // 거리 계산
                    Double distance = null;
                    if (park.getLongitude() != null && park.getLatitude() != null) {
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

                    // 여유 예상 시간 계산
                    String recommendedVisitHour = calculateRecommendedVisitHour(park.getAreaCode());
                    builder.recommendedVisitHour(recommendedVisitHour);

                    return builder.build();
                })
                .sorted((p1, p2) -> {
                    Double distance1 = p1.getDistance();
                    Double distance2 = p2.getDistance();
                    
                    // 거리가 null인 경우 가장 뒤로 정렬
                    if (distance1 == null && distance2 == null) {
                        // 둘 다 null이면 areaCode로 정렬
                        String code1 = p1.getAreaCode() != null ? p1.getAreaCode() : "";
                        String code2 = p2.getAreaCode() != null ? p2.getAreaCode() : "";
                        return code1.compareTo(code2);
                    }
                    if (distance1 == null) {
                        return 1; // distance1이 null이면 뒤로
                    }
                    if (distance2 == null) {
                        return -1; // distance2가 null이면 뒤로
                    }
                    
                    // 거리가 가까운 순으로 정렬
                    int compare = Double.compare(distance1, distance2);
                    
                    // 거리가 같으면 areaCode로 정렬
                    if (compare == 0) {
                        String code1 = p1.getAreaCode() != null ? p1.getAreaCode() : "";
                        String code2 = p2.getAreaCode() != null ? p2.getAreaCode() : "";
                        compare = code1.compareTo(code2);
                    }
                    
                    return compare;
                })
                .collect(Collectors.toList());

        // 커서 기반 페이지네이션 적용
        int startIndex = 0;
        if (cursor != null && !cursor.trim().isEmpty()) {
            // cursor 이후의 인덱스 찾기
            for (int i = 0; i < allParkInfoList.size(); i++) {
                if (allParkInfoList.get(i).getAreaCode().equals(cursor)) {
                    startIndex = i + 1;
                    break;
                }
            }
        }

        // 페이지 크기 + 1개 조회하여 다음 페이지 존재 여부 확인
        int endIndex = Math.min(startIndex + pageSize + 1, allParkInfoList.size());
        List<GetAllParksResponse.ParkInfo> parkInfoList = allParkInfoList.subList(startIndex, endIndex);

        // 다음 페이지 존재 여부 확인
        boolean hasNext = parkInfoList.size() > pageSize;
        if (hasNext) {
            parkInfoList = parkInfoList.subList(0, pageSize); // 마지막 하나 제거
        }

        // 다음 커서 설정
        String nextCursor = null;
        if (hasNext && !parkInfoList.isEmpty()) {
            nextCursor = parkInfoList.get(parkInfoList.size() - 1).getAreaCode();
        }

        GetAllParksResponse response = GetAllParksResponse.builder()
                .parks(parkInfoList)
                .nextCursor(nextCursor)
                .hasNext(hasNext)
                .size(parkInfoList.size())
                .build();

        log.info("거리 가까운 순 공원 리스트 조회 완료 - 조회된 공원 수: {}, 다음 페이지 존재: {}", 
                parkInfoList.size(), hasNext);

        return response;
    }

    /**
     * 혼잡도 레벨을 숫자로 변환합니다.
     * 낮은 혼잡도일수록 작은 숫자를 반환합니다.
     * 
     * @param congestLevel 혼잡도 레벨 (여유, 보통, 붐빔, 매우붐빔)
     * @return 혼잡도 순서 (1: 여유, 2: 보통, 3: 붐빔, 4: 매우붐빔, 999: 없음)
     */
    private int getCongestionLevelOrder(String congestLevel) {
        if (congestLevel == null || congestLevel.trim().isEmpty()) {
            return 999; // 혼잡도 정보가 없으면 가장 뒤로
        }
        
        return switch (congestLevel.trim()) {
            case "여유" -> 1;
            case "보통" -> 2;
            case "붐빔" -> 3;
            case "매우붐빔" -> 4;
            default -> 999; // 알 수 없는 값도 가장 뒤로
        };
    }

    /**
     * 태그명으로 공원 리스트를 조회합니다 (커서 기반 페이지네이션).
     * 여러 태그명을 제공할 경우, 모든 태그를 모두 가지고 있는 공원만 조회됩니다 (AND 조건).
     *
     * @param tagNames 태그명 리스트
     * @param cursor 커서 (areaCode), 첫 페이지는 null
     * @param size 페이지 크기 (기본값: 10, 최대값: 100)
     * @param longitude 현재 위치 경도 (거리 계산용, 선택)
     * @param latitude 현재 위치 위도 (거리 계산용, 선택)
     * @return 공원 리스트 및 다음 페이지 정보
     */
    @Override
    @Transactional(readOnly = true)
    public GetAllParksResponse getParksByTags(List<String> tagNames, String cursor, Integer size, Double longitude, Double latitude) {
        // 태그명 검증
        if (tagNames == null || tagNames.isEmpty()) {
            log.warn("태그명이 제공되지 않았습니다.");
            return GetAllParksResponse.builder()
                    .parks(List.of())
                    .nextCursor(null)
                    .hasNext(false)
                    .size(0)
                    .build();
        }

        // size 검증 및 기본값 설정
        int pageSize = (size == null || size <= 0) ? DEFAULT_SIZE : Math.min(size, MAX_SIZE);
        
        log.info("태그 기반 공원 리스트 조회 시작 - tagNames: {}, cursor: {}, size: {}", tagNames, cursor, pageSize);

        // 태그명으로 areaCode 리스트 조회 (AND 조건: 모든 태그를 가진 공원만)
        long tagCount = tagNames.size();
        List<String> areaCodes = parkTagRepository.findAreaCodesByTagNames(tagNames, tagCount);
        
        if (areaCodes.isEmpty()) {
            log.info("태그에 해당하는 공원이 없습니다 - tagNames: {}", tagNames);
            return GetAllParksResponse.builder()
                    .parks(List.of())
                    .nextCursor(null)
                    .hasNext(false)
                    .size(0)
                    .build();
        }

        log.debug("태그에 해당하는 공원 수: {}", areaCodes.size());

        // 커서 기반 필터링
        List<String> filteredAreaCodes;
        if (cursor == null || cursor.trim().isEmpty()) {
            // 첫 페이지: 모든 areaCode
            filteredAreaCodes = areaCodes;
        } else {
            // 다음 페이지: cursor보다 큰 areaCode만
            int cursorIndex = areaCodes.indexOf(cursor);
            if (cursorIndex == -1) {
                // cursor가 리스트에 없으면 빈 결과 반환
                log.warn("커서에 해당하는 공원을 찾을 수 없습니다 - cursor: {}", cursor);
                return GetAllParksResponse.builder()
                        .parks(List.of())
                        .nextCursor(null)
                        .hasNext(false)
                        .size(0)
                        .build();
            }
            // cursor 다음부터
            filteredAreaCodes = areaCodes.subList(cursorIndex + 1, areaCodes.size());
        }

        // 페이지네이션 적용 (size + 1개 조회하여 다음 페이지 존재 여부 확인)
        int endIndex = Math.min(pageSize + 1, filteredAreaCodes.size());
        List<String> paginatedAreaCodes = filteredAreaCodes.subList(0, endIndex);

        // 다음 페이지 존재 여부 확인
        boolean hasNext = paginatedAreaCodes.size() > pageSize;
        if (hasNext) {
            paginatedAreaCodes = paginatedAreaCodes.subList(0, pageSize); // 마지막 하나 제거
        }

        // 다음 커서 설정
        String nextCursor = null;
        if (hasNext && !paginatedAreaCodes.isEmpty()) {
            nextCursor = paginatedAreaCodes.get(paginatedAreaCodes.size() - 1);
        }

        // areaCode로 공원 정보 조회
        List<Park> parks = paginatedAreaCodes.stream()
                .map(areaCode -> parkRepository.findByAreaCode(areaCode))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        log.debug("조회된 공원 수: {}, 요청한 size: {}", parks.size(), pageSize);

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

                    // 여유 예상 시간 계산
                    String recommendedVisitHour = calculateRecommendedVisitHour(park.getAreaCode());
                    builder.recommendedVisitHour(recommendedVisitHour);

                    return builder.build();
                })
                .collect(Collectors.toList());

        GetAllParksResponse response = GetAllParksResponse.builder()
                .parks(parkInfoList)
                .nextCursor(nextCursor)
                .hasNext(hasNext)
                .size(parkInfoList.size())
                .build();

        log.info("태그 기반 공원 리스트 조회 완료 - 조회된 공원 수: {}, 다음 페이지 존재: {}", 
                parkInfoList.size(), hasNext);

        return response;
    }

    /**
     * 공원의 여유 예상 시간을 계산합니다.
     * CongestionRecommendService를 사용하여 오늘 요일 기준으로 가장 여유로운 시간을 추천받습니다.
     * 
     * @param areaCode 지역 코드
     * @return "X시 여유 예상" 형태의 문자열, 추천 시간이 없으면 null
     */
    private String calculateRecommendedVisitHour(String areaCode) {
        try {
            LocalDateTime now = LocalDateTime.now();
            Weekday todayWeekday = Weekday.from(now.getDayOfWeek());
            
            var recommendResult = congestionRecommendService.recommend(areaCode, todayWeekday);
            int recommendedHour = recommendResult.getRecommendedHour();
            
            if (recommendedHour > 0) {
                return recommendedHour + "시 여유 예상";
            }
        } catch (Exception e) {
            log.warn("여유 예상 시간 계산 중 오류 발생 - areaCode: {}, error: {}", areaCode, e.getMessage());
        }
        
        return null;
    }
}

