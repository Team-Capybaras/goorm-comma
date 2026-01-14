package groom.backend.application.avoidance.service.spec;

import groom.backend.application.avoidance.dto.response.CongestionRecommendResult;
import groom.backend.domain.avoidance.enums.Weekday;

public interface CongestionRecommendService {
  /**
   * parkStatistics 기반 여러 정보를 취합하여 가장 적합한 시간대를 방문시간대로 추천한다.
   * @return
   */
  public CongestionRecommendResult recommend(String areaCode, Weekday weekday);
}
