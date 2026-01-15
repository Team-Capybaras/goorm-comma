package groom.backend.application.avoidance.dto.response;

import groom.backend.domain.avoidance.enums.Weekday;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 모델 예측 결과 및 추천 시간대
 * 내부 DTO
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CongestionRecommendResult {
  private Weekday weekday;               // 기준 요일
  private int recommendedHour;           // 추천 시간 (09-22시 사이)
  private String message;       // 추천 텍스트 메시지
  private List<CongestionPredResult> predCongestions; // 혼잡도 예측
}