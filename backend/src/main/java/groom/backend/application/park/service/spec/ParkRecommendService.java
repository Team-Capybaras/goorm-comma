package groom.backend.application.park.service.spec;

import groom.backend.application.park.dto.response.GetAllParksResponse;

/**
 * 공원 추천 애플리케이션 서비스
 *
 * 혼잡도와 거리 정보를 종합하여
 * 혼잡도가 낮고, 거리가 가까울수록
 * 우선순위가 높은 공원 리스트를 추천합니다.
 */
public interface ParkRecommendService {

  /**
   * 혼잡도 + 거리 혼합 기준으로 공원 리스트를 추천합니다.
   *
   * 여유 또는 보통인 혼잡도의 공원만 추천하며, 거리순으로 정렬합니다.
   *
   * 반환 개수는 최대 5개입니다.
   *
   * @param longitude 현재 위치 경도 (거리 계산용, 필수)
   * @param latitude  현재 위치 위도 (거리 계산용, 필수)
   *
   * @return 추천 공원 최대 5개 리스트
   */
  GetAllParksResponse recommendTop5Parks(
          double longitude,
          double latitude
  );

  /**
   * 혼잡도 + 거리 혼합 기준으로 공원 리스트를 추천합니다.
   *
   * 여유 또는 보통인 혼잡도의 공원만 추천하며, 거리순으로 정렬합니다.
   *
   * 거리 제한을 줄 수 있습니다. 단위는 km입니다.
   *
   * 반환 개수는 최대 5개입니다.
   *
   * @param longitude 현재 위치 경도 (거리 계산용, 필수)
   * @param latitude  현재 위치 위도 (거리 계산용, 필수)
   * @param limitDistance  거리 제한 (필터링 용, 필수)
   *
   * @return 추천 공원 최대 5개 리스트
   */
  GetAllParksResponse recommendTop5Parks(
          double longitude,
          double latitude,
          int limitDistance
  );

  /**
   * 특정 공원을 대상으로 하여 공원 리스트를 추천합니다.
   * 혼잡도 + 거리 혼합 기준으로 공원 리스트를 추천합니다.
   *
   * 정렬 우선순위:
   * 1. 혼잡도 (차이가 크며 낮을수록 우선.)
   * 2. 거리 (가까울수록 우선)
   *
   * 거리 제한을 줄 수 있습니다. 단위는 km입니다.
   *
   * 반환 개수는 최대 5개입니다.
   *
   * @param longitude 현재 위치 경도 (거리 계산용, 필수)
   * @param latitude  현재 위치 위도 (거리 계산용, 필수)
   * @param baseAreaCode  거리 제한 (필터링 용, 필수)
   *
   * @return 추천 공원 최대 5개 리스트
   */
  GetAllParksResponse recommendTop5Parks(
          double longitude,
          double latitude,
          String baseAreaCode
  );
}
