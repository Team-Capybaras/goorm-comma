package groom.backend.application.avoidance.dto.response;

import groom.backend.domain.avoidance.enums.Weekday;

/**
 * 통계 조회시 이용
 * 내부 DTO
 */
public record CongestionStatistics(Weekday weekday,
         int hour,
         int popMeanMin,
         int popMeanMax) {
}
