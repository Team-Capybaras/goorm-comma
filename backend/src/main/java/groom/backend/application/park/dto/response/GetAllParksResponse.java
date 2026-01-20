package groom.backend.application.park.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 공원 리스트 조회 응답 DTO (커서 기반 페이지네이션)
 */
@Schema(
        name = "GetAllParksResponse",
        description = "공원 리스트 조회 응답 (커서 기반 페이지네이션)"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAllParksResponse {
    @Schema(
            description = "공원 정보 리스트 (빈 배열일 수 있음)",
            example = "[]"
    )
    private List<ParkInfo> parks;

    @Schema(
            description = "다음 페이지 커서 (다음 페이지가 없으면 null)",
            example = "POI100",
            nullable = true
    )
    private String nextCursor;

    @Schema(
            description = "다음 페이지 존재 여부",
            example = "true"
    )
    private Boolean hasNext;

    @Schema(
            description = "조회된 공원 수",
            example = "10"
    )
    private Integer size;

    @Schema(
            description = "전체 공원 수",
            example = "34"
    )
    private Integer totalCount;

    @Schema(
            description = "태그에 해당하는 전체 공원 수 (by-tags API 전용)",
            example = "15",
            nullable = true
    )
    private Integer count;

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

        @Schema(
                description = "여유 예상 시간 (시), 예: \"14시 여유 예상\"",
                example = "14시 여유 예상",
                nullable = true
        )
        private String recommendedVisitHour;
    }
}

