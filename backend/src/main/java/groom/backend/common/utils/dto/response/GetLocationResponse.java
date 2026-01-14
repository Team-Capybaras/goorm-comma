package groom.backend.common.utils.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 현재 위치 조회 응답 DTO
 */
@Schema(
        name = "GetLocationResponse",
        description = "현재 위치 조회 응답"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetLocationResponse {
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
            description = "위치 정보 수신 성공 메시지",
            example = "위치 정보를 성공적으로 수신했습니다"
    )
    private String message;
}
