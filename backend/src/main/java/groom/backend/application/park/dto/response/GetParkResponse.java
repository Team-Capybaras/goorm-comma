package groom.backend.application.park.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 특정 공원 조회 응답 DTO
 */
@Schema(
        name = "GetParkResponse",
        description = "특정 공원 조회 응답"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetParkResponse {
    @Schema(
            description = "공원 정보",
            name = "ParkInfo"
    )
    private ParkInfo park;

    /**
     * 공원 정보
     */
    @Schema(
            name = "ParkInfo",
            description = "공원 정보"
    )
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ParkInfo {
        @Schema(
                description = "지역 코드 (AREA_CODE)",
                example = "POI093"
        )
        private String areaCode;

        @Schema(
                description = "공원명",
                example = "뚝섬한강공원"
        )
        private String areaName;

        @Schema(
                description = "경도",
                example = "127.069903"
        )
        private Double longitude;

        @Schema(
                description = "위도",
                example = "37.529546"
        )
        private Double latitude;

        @Schema(
                description = "기온 (℃)",
                example = "2.5",
                nullable = true
        )
        private Float temp;

        @Schema(
                description = "강수 관련 메시지",
                example = "눈이 내리고 있습니다",
                nullable = true
        )
        private String precptMsg;

        @Schema(
                description = "통합 대기환경 등급",
                example = "보통",
                nullable = true
        )
        private String airIndex;

        @Schema(
                description = "혼잡도 레벨 (여유, 보통, 붐빔, 매우붐빔)",
                example = "보통",
                nullable = true
        )
        private String areaCongestLevel;

        @Schema(
                description = "현재 위치로부터의 직선거리 (km), 현재 위치가 제공되지 않으면 null",
                example = "2.5",
                nullable = true
        )
        private Double distance;

        @Schema(
                description = "이미지 URL 리스트",
                example = "[]",
                nullable = true
        )
        private List<String> images;

        @Schema(
                description = "태그 리스트",
                example = "null",
                nullable = true
        )
        private List<String> tags;
    }
}

