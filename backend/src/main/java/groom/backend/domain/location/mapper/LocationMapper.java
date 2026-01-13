package groom.backend.domain.location.mapper;

import groom.backend.domain.location.dto.request.GetLocationRequest;
import groom.backend.domain.location.dto.response.GetLocationResponse;
import org.springframework.stereotype.Component;

/**
 * 위치 정보 DTO 간 변환을 담당하는 매퍼
 */
@Component
public class LocationMapper {

    /**
     * GetLocationRequest를 GetLocationResponse DTO로 변환합니다.
     *
     * @param request 위치 정보 요청
     * @return 위치 정보 응답 DTO
     */
    public GetLocationResponse toGetLocationResponseDto(GetLocationRequest request) {
        if (request == null) {
            return null;
        }

        return GetLocationResponse.builder()
                .longitude(request.getLongitude())
                .latitude(request.getLatitude())
                .message("위치 정보를 성공적으로 수신했습니다")
                .build();
    }
}
