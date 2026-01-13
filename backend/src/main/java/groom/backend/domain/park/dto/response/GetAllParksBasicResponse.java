package groom.backend.domain.park.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 전체 공원 기본 정보 조회 응답 DTO
 */
@Schema(
        name = "GetAllParksBasicResponse",
        description = "전체 공원 기본 정보 조회 응답(지도 마커 표시용)"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAllParksBasicResponse {
    @Schema(
            description = "공원 기본 정보 리스트",
            example = "[]"
    )
    private List<ParkBasicInfo> parks;

    /**
     * 공원 기본 정보
     */
    @Schema(
            name = "ParkBasicInfo",
            description = "공원 기본 정보"
    )
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ParkBasicInfo {
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
    }
}
