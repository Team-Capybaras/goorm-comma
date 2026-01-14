package groom.backend.application.avoidance.service.spec;

import groom.backend.application.avoidance.dto.response.CongestionStatistics;

import java.util.List;

public interface ParkStatisticsService {
  /**
   * 전체 인구 데이터를 기반으로 공원 혼잡도 통계를 집계한다.
   */
  public void aggregateAll();

  public List<CongestionStatistics> getCongestionStatistics(String areaCode);
}
