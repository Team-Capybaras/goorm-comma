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
 *
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
  public GetAllParksResponse recommendTop5Parks(Double longitude, Double latitude) {

    if (longitude == null || latitude == null) {
      throw new IllegalArgumentException("추천을 위해서는 longitude와 latitude가 필수입니다.");
    }

    log.info("공원 추천 시작 - longitude: {}, latitude: {}", longitude, latitude);

    // 1. 전체 공원 조회
    List<Park> parks = parkRepository.findAll();

    // 2. ParkInfo 조립
    List<GetAllParksResponse.ParkInfo> parkInfos = parks.stream()
            .map(park -> toParkInfo(park, longitude, latitude))
            .collect(Collectors.toList());

    // 3. 혼합 정렬 (혼잡도 → 거리 → areaCode)
    List<GetAllParksResponse.ParkInfo> sorted = parkInfos.stream()
            // 1. 혼잡도 "여유"만 필터링
            .filter(p -> ("여유".equals(p.getAreaCongestLevel()) || ("보통".equals(p.getAreaCongestLevel()))) )
            // 2. 혼합 정렬
            .sorted(mixedRecommendComparator())
            // 3. Top 5 제한
            .limit(RECOMMEND_LIMIT)
            .collect(Collectors.toList());


    log.info("공원 추천 완료 - 추천 개수: {}", sorted.size());

    return GetAllParksResponse.builder()
            .parks(sorted)
            .hasNext(false)
            .nextCursor(null)
            .size(sorted.size())
            .build();
  }

  /**
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
   * 혼잡도 + 거리 혼합 추천 Comparator
   */
  private Comparator<GetAllParksResponse.ParkInfo> mixedRecommendComparator() {
    return Comparator.<GetAllParksResponse.ParkInfo>comparingInt(p -> getCongestionLevelOrder(p.getAreaCongestLevel()))
            .thenComparing(
                    GetAllParksResponse.ParkInfo::getDistance,
                    Comparator.nullsLast(Double::compareTo)
            )
            .thenComparing(GetAllParksResponse.ParkInfo::getAreaCode);
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

}
