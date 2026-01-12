package groom.backend.domain.park.dto.response;

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
        name = "GetParksResponse",
        description = "공원 리스트 조회 응답"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetParksResponse {
    @Schema(
            description = "공원 정보 리스트",
            example = "[]"
    )
    private List<ParkInfo> parks;

    @Schema(
            description = "다음 페이지 커서 (다음 페이지가 없으면 null)",
            example = "POI100"
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

