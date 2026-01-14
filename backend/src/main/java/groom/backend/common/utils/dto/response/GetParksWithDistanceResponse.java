package groom.backend.common.utils.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 공원 정보와 거리 정보 조회 응답 DTO
 */
@Schema(
        name = "GetParksWithDistanceResponse",
        description = "공원 정보와 거리 정보 조회 응답"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetParksWithDistanceResponse {
    @Schema(
            description = "현재 위치 경도",
            example = "127.069903"
    )
    private Double currentLongitude;

    @Schema(
            description = "현재 위치 위도",
            example = "37.529546"
    )
    private Double currentLatitude;

    @Schema(
            description = "공원 정보 리스트 (거리 정보 포함)",
            example = "[]"
    )
    private List<ParkWithDistanceInfo> parks;

    /**
     * 공원 정보와 거리 정보
     */
    @Schema(
            name = "ParkWithDistanceInfo",
            description = "공원 정보와 거리 정보"
    )
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ParkWithDistanceInfo {
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
                description = "공원 경도",
                example = "127.069903"
        )
        private Double longitude;

        @Schema(
                description = "공원 위도",
                example = "37.529546"
        )
        private Double latitude;

        @Schema(
                description = "현재 위치로부터의 직선거리 (킬로미터)",
                example = "2.5"
        )
        private Double distance;
    }
}
