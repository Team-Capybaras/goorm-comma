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
   * 정렬 우선순위:
   * 1. 혼잡도 (낮을수록 우선)
   * 2. 거리 (가까울수록 우선)
   * 3. 안정 정렬을 위한 areaCode
   *
   * 반환 개수는 최대 5개입니다.
   *
   * @param longitude 현재 위치 경도 (거리 계산용, 필수)
   * @param latitude  현재 위치 위도 (거리 계산용, 필수)
   *
   * @return 추천 공원 최대 5개 리스트
   */
  GetAllParksResponse recommendTop5Parks(
          Double longitude,
          Double latitude
  );
}
