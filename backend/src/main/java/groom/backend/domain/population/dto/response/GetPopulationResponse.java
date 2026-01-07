package groom.backend.domain.population.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(
        name = "GetPopulationResponse",
        description = "인구 정보 조회 응답"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetPopulationResponse {
    @Schema(
            description = "지역 코드 (AREA_CODE)",
            example = "POI093"
    )
    private String areaCode;

    @Schema(
            description = "공원명" +
                    "(강서한강공원, 고척돔, 광나루한강공원, 광화문광장, 국립중앙박물관·용산가족공원, " +
                    " 난지한강공원, 남산공원, 노들섬, 뚝섬한강공원, 망원한강공원, 반포한강공원, 북서울꿈의숲, " +
                    "서리풀공원·몽마르뜨공원, 서울광장, 서울대공원, 서울숲공원, 아차산, 양화한강공원, 어린이대공원, " +
                    "여의도한강공원, 월드컵공원, 응봉산, 이촌한강공원, 잠실종합운동장, 잠실한강공원, 잠원한강공원, " +
                    "청계산, 청와대, 보라매공원, 서대문독립공원, 안양천, 여의서로, 올림픽공원, 홍제폭포) ",
            example = "뚝섬한강공원"
    )
    private String areaName;

    @Schema(
            description = "실시간 인구 현황 정보"
    )
    private LivePopulationInfo livePopulation;

    @Schema(
            description = "인구 예보 정보 리스트 (시간대별 예보 데이터)"
    )
    private List<PredictedPopulationInfo> predictedPopulations;

    /**
     * 실시간 인구 현황 정보
     */
    @Schema(
            name = "LivePopulationInfo",
            description = "실시간 인구 현황 정보"
    )
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LivePopulationInfo {
        @Schema(
                description = "데이터 수집 시간",
                example = "2026-01-07T10:50:00"
        )
        private LocalDateTime dataGetTime;

        @Schema(
                description = "최소 인구 수",
                example = "1000"
        )
        private Integer areaPopMin;

        @Schema(
                description = "최대 인구 수",
                example = "5000"
        )
        private Integer areaPopMax;

        @Schema(
                description = "혼잡도 레벨 (여유, 보통, 붐빔, 매우붐빔)",
                example = "보통"
        )
        private String areaCongestLevel;

        @Schema(
                description = "혼잡도 메시지",
                example = "보통 혼잡"
        )
        private String areaCongestMsg;

        @Schema(
                description = "교체 여부",
                example = "false"
        )
        private Boolean replaceYn;

        @Schema(
                description = "인구 수집 시간",
                example = "2026-01-07T10:50:00"
        )
        private LocalDateTime popTime;
    }

    /**
     * 인구 예보 정보
     * 특정 시간대의 예상 인구 현황을 나타냅니다.
     */
    @Schema(
            name = "PredictedPopulationInfo",
            description = "인구 예보 정보"
    )
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PredictedPopulationInfo {
        @Schema(
                description = "데이터 수집 시간",
                example = "2026-01-07T10:50:00"
        )
        private LocalDateTime dataGetTime;

        @Schema(
                description = "예보 시간 (예측된 시간대)",
                example = "2026-01-07T12:00:00"
        )
        private LocalDateTime forecastTime;

        @Schema(
                description = "예보 혼잡도 레벨 (여유, 보통, 붐빔, 매우붐빔)",
                example = "붐빔"
        )
        private String forecastCongestLevel;

        @Schema(
                description = "예보 최소 인구 수",
                example = "84000"
        )
        private Integer forecastPopMin;

        @Schema(
                description = "예보 최대 인구 수",
                example = "86000"
        )
        private Integer forecastPopMax;
    }
}
