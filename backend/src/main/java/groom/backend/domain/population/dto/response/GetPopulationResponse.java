package groom.backend.domain.population.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 인구 정보 조회 응답 DTO
 * 지역의 실시간 인구 현황 및 예보 정보를 포함합니다.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetPopulationResponse {
    /**
     * 지역 코드 (AREA_CODE)
     */
    private String areaCode;

    /**
     * 지역명
     */
    private String areaName;

    /**
     * 실시간 인구 현황 정보
     */
    private LivePopulationInfo livePopulation;

    /**
     * 인구 예보 정보 리스트 (시간대별 예보 데이터)
     */
    private List<PredictedPopulationInfo> predictedPopulations;

    /**
     * 실시간 인구 현황 정보
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LivePopulationInfo {
        /**
         * 데이터 수집 시간
         */
        private LocalDateTime dataGetTime;

        /**
         * 최소 인구 수
         */
        private Integer areaPopMin;

        /**
         * 최대 인구 수
         */
        private Integer areaPopMax;

        /**
         * 혼잡도 레벨 (여유, 보통, 붐빔, 매우붐빔)
         */
        private String areaCongestLevel;

        /**
         * 혼잡도 메시지
         */
        private String areaCongestMsg;

        /**
         * 교체 여부
         */
        private Boolean replaceYn;

        /**
         * 인구 수집 시간
         */
        private LocalDateTime popTime;
    }

    /**
     * 인구 예보 정보
     * 특정 시간대의 예상 인구 현황을 나타냅니다.
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PredictedPopulationInfo {
        /**
         * 데이터 수집 시간
         */
        private LocalDateTime dataGetTime;

        /**
         * 예보 시간 (예측된 시간대)
         */
        private LocalDateTime forecastTime;

        /**
         * 예보 혼잡도 레벨 (여유, 보통, 붐빔, 매우붐빔)
         */
        private String forecastCongestLevel;

        /**
         * 예보 최소 인구 수
         */
        private Integer forecastPopMin;

        /**
         * 예보 최대 인구 수
         */
        private Integer forecastPopMax;
    }
}

