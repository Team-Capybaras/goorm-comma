package groom.backend.domain.location.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 현재 위치 조회 요청 DTO
 */
@Schema(
        name = "GetLocationRequest",
        description = "현재 위치 조회 요청"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetLocationRequest {
    @Schema(
            description = "경도",
            example = "127.069903"
    )
    @NotNull(message = "경도는 필수입니다")
    private Double longitude;

    @Schema(
            description = "위도",
            example = "37.529546"
    )
    @NotNull(message = "위도는 필수입니다")
    private Double latitude;
}
