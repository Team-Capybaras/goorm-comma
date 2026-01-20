package groom.backend.application.park.service.impl;

import groom.backend.application.park.dto.response.GetAllParksResponse;
import groom.backend.application.park.service.spec.ParkRecommendService;
import groom.backend.common.utils.DistanceCalculator;
import groom.backend.domain.park.entity.Park;
import groom.backend.domain.park.entity.ParkTag;
import groom.backend.domain.park.repository.ParkRepository;
import groom.backend.domain.park.repository.ParkTagRepository;
import groom.backend.domain.population.entity.LivePopStatus;
import groom.backend.domain.population.repository.LivePopStatusRepository;
import groom.backend.domain.tag.entity.Tag;
import groom.backend.domain.weather.entity.WeatherStatus;
import groom.backend.domain.weather.repository.WeatherStatusRepository;
import groom.backend.application.avoidance.service.spec.CongestionRecommendService;
import groom.backend.domain.avoidance.enums.Weekday;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 공원 추천 서비스 구현체
 * <p>
 * 혼잡도 + 거리 기준으로 공원을 정렬하여
 * 상위 5개 공원을 추천합니다.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ParkRecommendServiceImpl implements ParkRecommendService {

  // 추천 갯수 제한
  private static final int RECOMMEND_LIMIT = 5;

  private final ParkRepository parkRepository;
  private final ParkTagRepository parkTagRepository;
  private final WeatherStatusRepository weatherStatusRepository;
  private final LivePopStatusRepository livePopStatusRepository;
  private final CongestionRecommendService congestionRecommendService;

  @Override
  @Transactional(readOnly = true)
  public GetAllParksResponse recommendTop5Parks(double longitude, double latitude) {

    log.info("실시간 사용자 위치 기반 공원 추천 - longitude: {}, latitude: {}", longitude, latitude);

    List<GetAllParksResponse.ParkInfo> sorted = getSortedParkList(longitude, latitude).stream()
            // Top K 제한
            .limit(RECOMMEND_LIMIT).toList();

    log.info("공원 추천 완료 - 추천 개수: {}", sorted.size());

    return GetAllParksResponse.builder()
            .parks(sorted)
            .hasNext(false)
            .nextCursor(null)
            .size(sorted.size())
            .build();
  }

  @Override
  @Transactional(readOnly = true)
  public GetAllParksResponse recommendTop5Parks(double longitude, double latitude, int limitDistance) {

    log.info("실시간 사용자 주변 거리 필터링 공원 추천 - longitude: {}, latitude: {}", longitude, latitude);

    List<GetAllParksResponse.ParkInfo> sorted = getSortedParkList(longitude, latitude).stream()
            // 거리 제한
            .filter(p -> p.getDistance() < limitDistance)
            // Top K 제한
            .limit(RECOMMEND_LIMIT).toList();


    return GetAllParksResponse.builder()
            .parks(sorted)
            .hasNext(false)
            .nextCursor(null)
            .size(sorted.size())
            .build();
  }


  @Override
  @Transactional(readOnly = true)
  public GetAllParksResponse recommendTop5Parks(double longitude, double latitude, String baseAreaCode) {

    int order = getBaseCongestionOrder(baseAreaCode);

    log.info("공원 주변 대체지 추천 - longitude: {}, latitude: {}", longitude, latitude);

    List<GetAllParksResponse.ParkInfo> sorted = getSortedParkList(longitude, latitude, order, baseAreaCode).stream()
            // 거리 제한
//            .filter(p -> p.getDistance() < limitDistance)
            // Top K 제한
            .limit(RECOMMEND_LIMIT).toList();


    return GetAllParksResponse.builder()
            .parks(sorted)
            .hasNext(false)
            .nextCursor(null)
            .size(sorted.size())
            .build();
  }

  private int getBaseCongestionOrder(String baseAreaCode) {
    return livePopStatusRepository.findLatestByAreaCode(baseAreaCode)
            .map(p -> getCongestionLevelOrder(p.getAreaCongestLevel()))
            .orElse(999);
  }


  private List<GetAllParksResponse.ParkInfo> getSortedParkList(double longitude, double latitude) {

    log.info("공원 추천 시작 - longitude: {}, latitude: {}", longitude, latitude);

    // 1. 전체 공원 조회
    List<Park> parks = parkRepository.findAll();

    // 2. ParkInfo 조립
    List<GetAllParksResponse.ParkInfo> parkInfos = parks.stream()
            .map(park -> toParkInfo(park, longitude, latitude))
            .toList();

    List<GetAllParksResponse.ParkInfo> sorted = parkInfos.stream()
            // 1. 혼잡도 여유 및 보통만 필터링
            .filter(p -> ("여유".equals(p.getAreaCongestLevel()) || ("보통".equals(p.getAreaCongestLevel()))))
            // 2. 혼합 정렬
            .sorted(recommendComparator())
            .toList();

    log.info("공원 추천 완료 - 추천 개수: {}", sorted.size());

    // 3. 혼합 정렬 (혼잡도 → 거리 → areaCode)
    return sorted;
  }

  private List<GetAllParksResponse.ParkInfo> getSortedParkList(double longitude, double latitude, int congestionOrder, String baseAreaCode) {

    log.info("공원 추천 시작 - longitude: {}, latitude: {}", longitude, latitude);

    // 1. 전체 공원 조회
    List<Park> parks = parkRepository.findAll();

    // 2. ParkInfo 조립
    List<GetAllParksResponse.ParkInfo> parkInfos = parks.stream()
            .map(park -> toParkInfo(park, longitude, latitude, baseAreaCode))
            .toList();

    List<GetAllParksResponse.ParkInfo> sorted = parkInfos.stream()
            // 1. 혼잡도 여유 및 보통만 필터링
            .filter(p -> ("여유".equals(p.getAreaCongestLevel()) || ("보통".equals(p.getAreaCongestLevel()))))
            // 2. 혼합 정렬
            .sorted(tagCongestionDistanceComparator(congestionOrder))
            .toList();

    log.info("공원 추천 완료 - 추천 개수: {}", sorted.size());

    // 3. 혼합 정렬 (혼잡도 → 거리 → areaCode)
    return sorted;
  }

  /**
   * TODO : Strategy Pattern refactoring
   * 맞춤형 대체지의 경우 자카드 유사도 기반, 사용자 위치 주변 검색 시는 일반 정렬 전략 사용. 전략 오브젝트를 패러미터로 받을 것.
   *
   * Park → ParkInfo 변환
   * TODO : Mapper 리팩토링
   * 날씨, 실시간 정보 종합 둘 다 필요해서 단순히 옮기기는 어려울 것으로 보임.
   */
  private GetAllParksResponse.ParkInfo toParkInfo(Park park, Double longitude, Double latitude) {

    Optional<WeatherStatus> weatherOpt =
            weatherStatusRepository.findTopByAreaCodeOrderByDataGetTimeDesc(park.getAreaCode());

    Optional<LivePopStatus> popOpt =
            livePopStatusRepository.findLatestByAreaCode(park.getAreaCode());

    List<String> tags = parkTagRepository.findByAreaCodeWithTag(park.getAreaCode()).stream()
            .map(ParkTag::getTag)
            .filter(tag -> tag != null)
            .map(Tag::getTagName)
            .collect(Collectors.toList());

    Double distance = null;
    if (park.getLongitude() != null && park.getLatitude() != null) {
      double calculated = DistanceCalculator.calculateDistance(
              latitude,
              longitude,
              park.getLatitude(),
              park.getLongitude()
      );
      distance = Math.round(calculated * 10.0) / 10.0;
    }

    GetAllParksResponse.ParkInfo.ParkInfoBuilder builder =
            GetAllParksResponse.ParkInfo.builder()
                    .areaCode(park.getAreaCode())
                    .areaName(park.getAreaName())
                    .longitude(park.getLongitude())
                    .latitude(park.getLatitude())
                    .distance(distance)
                    .images(park.getImageUrls())
                    .tags(tags.isEmpty() ? null : tags);

    weatherOpt.ifPresent(w ->
            builder.temp(w.getTemp())
                    .precptMsg(w.getPrecptMsg())
                    .airIndex(w.getAirIndex())
    );

    popOpt.ifPresent(p ->
            builder.areaCongestLevel(p.getAreaCongestLevel())
    );

    builder.recommendedVisitHour(calculateRecommendedVisitHour(park.getAreaCode()));

    return builder.build();
  }

  /**
   * 태그 일치도 적용 시 사용되는 toParkInfo
   * @param park
   * @param longitude
   * @param latitude
   * @param baseAreaCode
   * @return
   */
  private GetAllParksResponse.ParkInfo toParkInfo(Park park, Double longitude, Double latitude, String baseAreaCode) {

    List<String> baseTags = getBaseTags(baseAreaCode);

    Optional<WeatherStatus> weatherOpt =
            weatherStatusRepository.findTopByAreaCodeOrderByDataGetTimeDesc(park.getAreaCode());

    Optional<LivePopStatus> popOpt =
            livePopStatusRepository.findLatestByAreaCode(park.getAreaCode());

    List<String> tags = parkTagRepository.findByAreaCodeWithTag(park.getAreaCode()).stream()
            .map(ParkTag::getTag)
            .filter(tag -> tag != null)
            .map(Tag::getTagName)
            .toList();

    double tagSimilarity = calculateTagSimilarity(baseTags, tags);

    Double distance = null;
    if (park.getLongitude() != null && park.getLatitude() != null) {
      double calculated = DistanceCalculator.calculateDistance(
              latitude,
              longitude,
              park.getLatitude(),
              park.getLongitude()
      );
      distance = Math.round(calculated * 10.0) / 10.0;
    }

    GetAllParksResponse.ParkInfo.ParkInfoBuilder builder =
            GetAllParksResponse.ParkInfo.builder()
                    .areaCode(park.getAreaCode())
                    .areaName(park.getAreaName())
                    .longitude(park.getLongitude())
                    .latitude(park.getLatitude())
                    .distance(distance)
                    .images(park.getImageUrls())
                    .tagSimilarity(tagSimilarity)
                    .areaCongestLevel(popOpt.map(LivePopStatus::getAreaCongestLevel).orElse(null))
                    .tags(tags.isEmpty() ? null : tags);

    weatherOpt.ifPresent(w ->
            builder.temp(w.getTemp())
                    .precptMsg(w.getPrecptMsg())
                    .airIndex(w.getAirIndex())
    );

    popOpt.ifPresent(p ->
            builder.areaCongestLevel(p.getAreaCongestLevel())
    );

    builder.recommendedVisitHour(calculateRecommendedVisitHour(park.getAreaCode()));

    return builder.build();
  }

  /**
   * 거리 추천 Comparator
   */
  private Comparator<GetAllParksResponse.ParkInfo> recommendComparator() {
    return Comparator.comparingDouble(
            GetAllParksResponse.ParkInfo::getDistance);
//    안정 정렬을 위한 areaCode 비교였으나 우선순위 망가져서 보류
//            .thenComparing(GetAllParksResponse.ParkInfo::getAreaCode);
  }

  private Comparator<GetAllParksResponse.ParkInfo> congestionGapRecommendComparator(int baseCongestionOrder) {
    return Comparator.<GetAllParksResponse.ParkInfo>comparingInt(
                    // 혼잡도 차이가 클 수록 우선순위 높음
                    p -> baseCongestionOrder - getCongestionLevelOrder(p.getAreaCongestLevel())
            ).reversed()

            .thenComparingDouble((GetAllParksResponse.ParkInfo::getDistance));
  }

  private Comparator<GetAllParksResponse.ParkInfo> tagCongestionDistanceComparator(int baseCongestionOrder) {

    return Comparator
            // 1. 혼잡도 차이
            .<GetAllParksResponse.ParkInfo>comparingInt(
                    p -> baseCongestionOrder - getCongestionLevelOrder(p.getAreaCongestLevel())
            ).reversed()

            // 2. 태그 유사도
            .thenComparing(
                    GetAllParksResponse.ParkInfo::getTagSimilarity,
                    Comparator.nullsLast(Comparator.reverseOrder())
            )

            // 3. 거리
            .thenComparingDouble(GetAllParksResponse.ParkInfo::getDistance);
  }

  /**
   * 혼잡도 레벨 → 정렬용 숫자 변환
   */
  private int getCongestionLevelOrder(String congestLevel) {
    if (congestLevel == null || congestLevel.isBlank()) {
      return 999;
    }

    return switch (congestLevel) {
      case "여유" -> 1;
      case "보통" -> 2;
      case "붐빔" -> 3;
      case "매우붐빔" -> 4;
      default -> 999;
    };
  }

  /**
   * 여유 예상 시간 계산
   */
  private String calculateRecommendedVisitHour(String areaCode) {
    try {
      LocalDateTime now = LocalDateTime.now();
      Weekday weekday = Weekday.from(now.getDayOfWeek());

      var result = congestionRecommendService.recommend(areaCode, weekday);
      int hour = result.getRecommendedHour();

      if (hour > 0) {
        return hour + "시 여유 예상";
      }
    } catch (Exception e) {
      log.warn("여유 예상 시간 계산 실패 - areaCode: {}", areaCode);
    }
    return null;
  }

  /**
   * 태그 기반 정렬 기준 태그 가져오기
   *
   * @param baseAreaCode 정렬 기준 공원 코드
   * @return
   */
  private List<String> getBaseTags(String baseAreaCode) {
    return parkTagRepository.findByAreaCodeWithTag(baseAreaCode).stream()
            .map(ParkTag::getTag)
            .filter(tag -> tag != null)
            .map(Tag::getTagName)
            .toList();
  }

  /**
   * Jaccard Similarity = |A ∩ B| / |A ∪ B|
   */
  private double calculateTagSimilarity(List<String> baseTags, List<String> targetTags) {
    if (baseTags == null || targetTags == null ||
            baseTags.isEmpty() || targetTags.isEmpty()) {
      return 0.0;
    }

    long intersection =
            baseTags.stream()
                    .filter(targetTags::contains)
                    .count();

    long union =
            baseTags.stream()
                    .distinct()
                    .count()
                    + targetTags.stream()
                    .filter(t -> !baseTags.contains(t))
                    .count();

    return (double) intersection / union;
  }
}
