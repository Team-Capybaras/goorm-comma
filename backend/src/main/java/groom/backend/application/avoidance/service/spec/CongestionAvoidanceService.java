package groom.backend.application.avoidance.service.spec;

import groom.backend.application.avoidance.dto.response.CongestionRecommendResponse;

public interface CongestionAvoidanceService {

  /**
   * 특정 공원의 요일별 시간단위 혼잡도 데이터를 조회한다.
   * @param areaCode
   */
  public CongestionRecommendResponse getParkStatistics(String areaCode);
}
