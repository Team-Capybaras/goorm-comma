package groom.backend.domain.park.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 전체 공원 리스트 조회 응답 DTO
 */
@Schema(
        name = "GetAllParksResponse",
        description = "전체 공원 리스트 조회 응답"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAllParksResponse {
    @Schema(
            description = "전체 공원 정보 리스트",
            example = "[]"
    )
    private List<ParkInfo> parks;

    @Schema(
            description = "전체 공원 수",
            example = "35"
    )
    private Integer totalCount;

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
                example = "2.5"
        )
        private Float temp;

        @Schema(
                description = "강수 관련 메시지",
                example = "눈이 내리고 있습니다"
        )
        private String precptMsg;

        @Schema(
                description = "통합 대기환경 등급",
                example = "보통"
        )
        private String airIndex;

        @Schema(
                description = "혼잡도 레벨 (여유, 보통, 붐빔, 매우붐빔)",
                example = "보통"
        )
        private String areaCongestLevel;
    }
}

