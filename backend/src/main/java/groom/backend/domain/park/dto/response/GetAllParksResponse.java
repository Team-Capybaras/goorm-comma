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
    }
}

