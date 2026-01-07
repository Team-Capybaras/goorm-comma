package groom.backend.domain.population.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 인구 정보 조회 응답
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetPopulationResponse {
    private String areaCode;
    private String areaName;
    private LivePopulationInfo livePopulation;
    private List<PredictedPopulationInfo> predictedPopulations;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LivePopulationInfo {
        private LocalDateTime dataGetTime;
        private Integer areaPopMin;
        private Integer areaPopMax;
        private String areaCongestLevel;
        private String areaCongestMsg;
        private Boolean replaceYn;
        private LocalDateTime popTime;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PredictedPopulationInfo {
        private LocalDateTime dataGetTime;
        private LocalDateTime forecastTime;
        private String forecastCongestLevel;
        private Integer forecastPopMin;
        private Integer forecastPopMax;
    }
}

