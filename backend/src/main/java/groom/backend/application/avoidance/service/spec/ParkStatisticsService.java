package groom.backend.application.avoidance.service.spec;

import groom.backend.application.avoidance.dto.response.CongestionStatistics;

import java.util.List;

public interface ParkStatisticsService {
  /**
   * 전체 인구 데이터를 기반으로 공원 혼잡도 통계를 집계한다.
   */
  public void aggregateAll();

  public List<CongestionStatistics> getCongestionStatistics(String areaCode);

  /**
   * 특정 공원에 대한 오늘 혼잡도 출력
   * @param areaCode
   * @return
   */
  public List<CongestionStatistics> getTodayCongestion(String areaCode);
}
