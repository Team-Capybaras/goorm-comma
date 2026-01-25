package groom.backend.application.park.service.impl;

import groom.backend.application.park.dto.response.GetAllParksResponse;
import groom.backend.application.park.dto.response.GetParkResponse;
import groom.backend.application.park.enums.ParkSortType;
import groom.backend.application.park.service.spec.ParkApplicationService;
import groom.backend.common.exception.BusinessException;
import groom.backend.common.exception.ErrorCode;
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
import java.util.Map;
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
     * 필터(tagNames)와 정렬(sort)을 함께 사용할 수 있습니다.
     *
     * @param cursor 커서 (areaCode), 첫 페이지는 null
     * @param size 페이지 크기 (기본값: 10, 최대값: 100)
     * @param sort 정렬 타입 (기본값: DEFAULT)
     * @param tagNames 태그명 리스트 (필터링용, 선택)
     * @param longitude 현재 위치 경도 (거리 계산 및 BY_DISTANCE 정렬용, BY_DISTANCE 정렬 시 필수)
     * @param latitude 현재 위치 위도 (거리 계산 및 BY_DISTANCE 정렬용, BY_DISTANCE 정렬 시 필수)
     * @return 공원 리스트 및 다음 페이지 정보
     */
    @Override
    @Transactional(readOnly = true)
    public GetAllParksResponse getParks(String cursor, Integer size, ParkSortType sort, List<String> tagNames, Double longitude, Double latitude) {
        // size 검증 및 기본값 설정
        int pageSize = (size == null || size <= 0) ? DEFAULT_SIZE : Math.min(size, MAX_SIZE);
        
        // 정렬 타입 기본값 설정
        if (sort == null) {
            sort = ParkSortType.DEFAULT;
        }
        
        // BY_DISTANCE 정렬 시 위치 정보 필수 검증
        if (sort == ParkSortType.BY_DISTANCE && (longitude == null || latitude == null)) {
            throw new BusinessException(ErrorCode.MISSING_PARAMETER, 
                    "BY_DISTANCE 정렬을 위해서는 longitude와 latitude가 필수입니다.");
        }
        
        // 좌표가 제공된 경우 유효성 검증
        if (longitude != null && latitude != null) {
            validateCoordinates(longitude, latitude);
        }
        
        log.info("공원 리스트 조회 시작 - cursor: {}, size: {}, sort: {}, tagNames: {}", 
                cursor, pageSize, sort, tagNames);

        // 1. 태그 필터링 (태그가 제공된 경우)
        List<String> filteredAreaCodes = null;
        if (tagNames != null && !tagNames.isEmpty()) {
            long tagNameCount = tagNames.size();
            filteredAreaCodes = parkTagRepository.findAreaCodesByTagNames(tagNames, tagNameCount);
            
            if (filteredAreaCodes.isEmpty()) {
                log.info("태그에 해당하는 공원이 없습니다 - tagNames: {}", tagNames);
                int totalCount = (int) parkRepository.count();
                return GetAllParksResponse.builder()
                        .parks(List.of())
                        .nextCursor(null)
                        .hasNext(false)
                        .size(0)
                        .totalCount(totalCount)
                        .count(0)
                        .build();
            }
            log.debug("태그에 해당하는 공원 수: {}", filteredAreaCodes.size());
        }

        // 2. 공원 조회 (태그 필터링이 있으면 필터링된 공원만, 없으면 전체 공원)
        List<Park> parks;
        if (filteredAreaCodes != null) {
            // 태그 필터링된 공원만 조회
            parks = filteredAreaCodes.stream()
                    .map(areaCode -> parkRepository.findByAreaCode(areaCode))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
        } else {
            // 전체 공원 조회 (정렬 타입과 관계없이 동일한 공원 집합 사용)
            parks = parkRepository.findAllByOrderByAreaCode();
        }

        // 3. ParkInfo 변환 (날씨, 인구, 태그, 거리 정보 포함)
        // 배치 조회로 N+1 문제 해결
        List<GetAllParksResponse.ParkInfo> allParkInfoList = convertToParkInfoBatch(parks, longitude, latitude);

        // 4. 정렬 적용 (모든 정렬 타입에서 동일한 공원 집합 사용, 순서만 변경)
        switch (sort) {
            case LOW_CONGESTION:
                // 혼잡도 낮은 순으로 정렬 (여유 < 보통 < 붐빔 < 매우붐빔)
                // 혼잡도 정보가 없는 공원은 뒤로 보냄
                allParkInfoList = allParkInfoList.stream()
                        .sorted((p1, p2) -> {
                            int level1 = getCongestionLevelOrder(p1.getAreaCongestLevel());
                            int level2 = getCongestionLevelOrder(p2.getAreaCongestLevel());
                            int compare = Integer.compare(level1, level2);
                            if (compare == 0) {
                                // 혼잡도가 같으면 areaCode 순서 유지
                                String code1 = p1.getAreaCode() != null ? p1.getAreaCode() : "";
                                String code2 = p2.getAreaCode() != null ? p2.getAreaCode() : "";
                                compare = code1.compareTo(code2);
                            }
                            return compare;
                        })
                        .collect(Collectors.toList());
                break;
            case BY_DISTANCE:
                // 거리 가까운 순으로 정렬
                // 거리 정보가 없는 공원은 뒤로 보냄
                allParkInfoList = allParkInfoList.stream()
                        .sorted((p1, p2) -> {
                            Double distance1 = p1.getDistance();
                            Double distance2 = p2.getDistance();
                            if (distance1 == null && distance2 == null) {
                                // 둘 다 거리 정보가 없으면 areaCode 순서 유지
                                String code1 = p1.getAreaCode() != null ? p1.getAreaCode() : "";
                                String code2 = p2.getAreaCode() != null ? p2.getAreaCode() : "";
                                return code1.compareTo(code2);
                            }
                            if (distance1 == null) return 1;  // distance1이 null이면 뒤로
                            if (distance2 == null) return -1; // distance2가 null이면 뒤로
                            int compare = Double.compare(distance1, distance2);
                            if (compare == 0) {
                                // 거리가 같으면 areaCode 순서 유지
                                String code1 = p1.getAreaCode() != null ? p1.getAreaCode() : "";
                                String code2 = p2.getAreaCode() != null ? p2.getAreaCode() : "";
                                compare = code1.compareTo(code2);
                            }
                            return compare;
                        })
                        .collect(Collectors.toList());
                break;
            case DEFAULT:
            default:
                // DEFAULT는 areaCode 순서 유지 (이미 정렬되어 있음)
                break;
        }

        // 5. 커서 기반 페이지네이션 적용
        int startIndex = 0;
        if (cursor != null && !cursor.trim().isEmpty()) {
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

        // 전체 공원 수 조회
        int totalCount = (int) parkRepository.count();
        // 태그에 해당하는 전체 공원 개수 (태그 필터링이 있는 경우)
        Integer tagMatchCount = (filteredAreaCodes != null) ? filteredAreaCodes.size() : null;

        GetAllParksResponse response = GetAllParksResponse.builder()
                .parks(parkInfoList)
                .nextCursor(nextCursor)
                .hasNext(hasNext)
                .size(parkInfoList.size())
                .totalCount(totalCount)
                .count(tagMatchCount)
                .build();

        log.info("공원 리스트 조회 완료 - 조회된 공원 수: {}, 전체 공원 수: {}, 태그 매칭 수: {}, 다음 페이지 존재: {}", 
                parkInfoList.size(), totalCount, tagMatchCount, hasNext);

        return response;
    }

    /**
     * 커서 기반 페이지네이션으로 공원 리스트를 조회합니다 (DEFAULT 정렬, 태그 필터 없음).
     * 성능 최적화를 위해 필요한 만큼만 조회합니다.
     */
    private GetAllParksResponse getParksWithCursorPagination(String cursor, Integer size, Double longitude, Double latitude) {
        int pageSize = (size == null || size <= 0) ? DEFAULT_SIZE : Math.min(size, MAX_SIZE);
        
        log.info("커서 기반 공원 리스트 조회 시작 - cursor: {}, size: {}", cursor, pageSize);

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

        // DTO 변환 (날씨 및 인구 정보 포함) - 배치 조회로 N+1 문제 해결
        List<GetAllParksResponse.ParkInfo> parkInfoList = convertToParkInfoBatch(parks, longitude, latitude);

        // 전체 공원 수 조회
        int totalCount = (int) parkRepository.count();

        GetAllParksResponse response = GetAllParksResponse.builder()
                .parks(parkInfoList)
                .nextCursor(nextCursor)
                .hasNext(hasNext)
                .size(parkInfoList.size())
                .totalCount(totalCount)
                .build();

        log.info("커서 기반 공원 리스트 조회 완료 - 조회된 공원 수: {}, 전체 공원 수: {}, 다음 페이지 존재: {}", 
                parkInfoList.size(), totalCount, hasNext);

        return response;
    }

    /**
     * Park 엔티티 리스트를 ParkInfo DTO 리스트로 변환합니다 (배치 조회).
     * N+1 문제를 해결하기 위해 배치로 조회합니다.
     */
    private List<GetAllParksResponse.ParkInfo> convertToParkInfoBatch(List<Park> parks, Double longitude, Double latitude) {
        if (parks.isEmpty()) {
            return List.of();
        }

        // areaCode 리스트 추출
        List<String> areaCodes = parks.stream()
                .map(Park::getAreaCode)
                .collect(Collectors.toList());

        // 1. 배치로 날씨 정보 조회 (한 번의 쿼리로 모든 areaCode의 최신 날씨 조회)
        Map<String, WeatherStatus> weatherStatusMap = weatherStatusRepository.findLatestByAreaCodes(areaCodes)
                .stream()
                .collect(Collectors.toMap(WeatherStatus::getAreaCode, w -> w, (existing, replacement) -> existing));

        // 2. 배치로 인구 정보 조회 (한 번의 쿼리로 모든 areaCode의 최신 인구 조회)
        Map<String, LivePopStatus> livePopStatusMap = livePopStatusRepository.findLatestByAreaCodes(areaCodes)
                .stream()
                .collect(Collectors.toMap(LivePopStatus::getAreaCode, p -> p, (existing, replacement) -> existing));

        // 3. 배치로 태그 정보 조회 (한 번의 쿼리로 모든 areaCode의 태그 조회)
        Map<String, List<String>> tagsMap = parkTagRepository.findByAreaCodesWithTag(areaCodes)
                .stream()
                .collect(Collectors.groupingBy(
                        ParkTag::getAreaCode,
                        Collectors.mapping(
                                pt -> pt.getTag() != null ? pt.getTag().getTagName() : null,
                                Collectors.filtering(tagName -> tagName != null, Collectors.toList())
                        )
                ));

        // 4. ParkInfo 변환
        return parks.stream()
                .map(park -> {
                    String areaCode = park.getAreaCode();
                    
                    // 거리 계산
                    Double distance = null;
                    if (longitude != null && latitude != null
                            && park.getLongitude() != null && park.getLatitude() != null) {
                        double calculatedDistance = DistanceCalculator.calculateDistance(
                                latitude,
                                longitude,
                                park.getLatitude(),
                                park.getLongitude()
                        );
                        distance = Math.round(calculatedDistance * 10.0) / 10.0;
                    }

                    GetAllParksResponse.ParkInfo.ParkInfoBuilder builder = GetAllParksResponse.ParkInfo.builder()
                            .areaCode(areaCode)
                            .areaName(park.getAreaName())
                            .longitude(park.getLongitude())
                            .latitude(park.getLatitude())
                            .distance(distance)
                            .images(park.getImageUrls() != null && !park.getImageUrls().isEmpty() ? park.getImageUrls() : null)
                            .tags(tagsMap.getOrDefault(areaCode, List.of()).isEmpty() ? null : tagsMap.get(areaCode));

                    // 날씨 정보 설정
                    WeatherStatus weatherStatus = weatherStatusMap.get(areaCode);
                    if (weatherStatus != null) {
                        builder.temp(weatherStatus.getTemp())
                                .precptMsg(weatherStatus.getPrecptMsg())
                                .airIndex(weatherStatus.getAirIndex());
                    }

                    // 인구 혼잡도 정보 설정
                    LivePopStatus livePopStatus = livePopStatusMap.get(areaCode);
                    if (livePopStatus != null) {
                        builder.areaCongestLevel(livePopStatus.getAreaCongestLevel());
                    }

                    // 여유 예상 시간 계산
                    String recommendedVisitHour = calculateRecommendedVisitHour(areaCode);
                    builder.recommendedVisitHour(recommendedVisitHour);

                    return builder.build();
                })
                .collect(Collectors.toList());
    }

    /**
     * Park 엔티티를 ParkInfo DTO로 변환합니다 (단일 조회용).
     */
    private GetAllParksResponse.ParkInfo convertToParkInfo(Park park, Double longitude, Double latitude) {
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
            throw new BusinessException(ErrorCode.PARK_NOT_FOUND, 
                    "지역코드 '" + areaCode + "'에 해당하는 공원 정보를 찾을 수 없습니다.");
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
            // 좌표 유효성 검증
            validateCoordinates(longitude, latitude);
            
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

        GetParkResponse.ParkInfo parkInfo = builder.build();

        GetParkResponse response = GetParkResponse.builder()
                .park(parkInfo)
                .build();

        log.info("특정 공원 조회 완료 - AREA_CODE: {}", areaCode);

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

    /**
     * 좌표 유효성 검증
     * longitude: -180 ~ 180
     * latitude: -90 ~ 90
     */
    private void validateCoordinates(Double longitude, Double latitude) {
        if (longitude == null || latitude == null) {
            return; // null인 경우는 상위에서 처리
        }
        
        if (longitude < -180 || longitude > 180) {
            throw new BusinessException(ErrorCode.PARK_RECOMMEND_INVALID_COORDINATES,
                    "longitude는 -180과 180 사이의 값이어야 합니다. 입력값: " + longitude);
        }
        if (latitude < -90 || latitude > 90) {
            throw new BusinessException(ErrorCode.PARK_RECOMMEND_INVALID_COORDINATES,
                    "latitude는 -90과 90 사이의 값이어야 합니다. 입력값: " + latitude);
        }
    }
}

