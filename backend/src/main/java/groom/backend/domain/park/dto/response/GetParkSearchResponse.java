package groom.backend.domain.park.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 공원 검색 응답 DTO
 */
@Schema(
        name = "GetParkSearchResponse",
        description = "공원 검색 응답"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetParkSearchResponse {
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
}
