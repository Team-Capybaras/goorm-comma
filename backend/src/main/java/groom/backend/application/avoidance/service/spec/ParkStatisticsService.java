package groom.backend.application.avoidance.service.spec;

import groom.backend.application.avoidance.dto.response.ParkStatisticsResponse;

public interface ParkStatisticsService {
  /**
   * 전체 인구 데이터를 기반으로 공원 혼잡도 통계를 집계한다.
   */
  public void aggregateAll();

  /**
   * 특정 공원의 요일별 시간단위 혼잡도 데이터를 조회한다.
   * @param areaCode
   */
  public ParkStatisticsResponse getParkStatistics(String areaCode);
}
