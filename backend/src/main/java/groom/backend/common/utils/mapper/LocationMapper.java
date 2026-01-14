package groom.backend.common.utils.mapper;

import groom.backend.common.utils.dto.request.GetLocationRequest;
import groom.backend.common.utils.dto.response.GetLocationResponse;
import groom.backend.common.utils.dto.response.GetParksWithDistanceResponse;
import groom.backend.common.utils.util.DistanceCalculator;
import groom.backend.domain.park.entity.Park;
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

    /**
     * Park 엔티티를 ParkWithDistanceInfo DTO로 변환합니다.
     * 현재 위치와 공원 간의 거리를 계산하여 포함합니다.
     *
     * @param park 공원 엔티티
     * @param currentLongitude 현재 위치 경도
     * @param currentLatitude 현재 위치 위도
     * @return 공원 정보와 거리 정보 DTO
     */
    public GetParksWithDistanceResponse.ParkWithDistanceInfo toParkWithDistanceInfoDto(
            Park park,
            Double currentLongitude,
            Double currentLatitude
    ) {
        if (park == null) {
            return null;
        }

        // 거리 계산 (공원의 경도, 위도가 null이 아닌 경우에만)
        // 소수점 첫째자리까지 반올림
        Double distance = null;
        if (park.getLongitude() != null && park.getLatitude() != null
                && currentLongitude != null && currentLatitude != null) {
            double calculatedDistance = DistanceCalculator.calculateDistance(
                    currentLatitude,
                    currentLongitude,
                    park.getLatitude(),
                    park.getLongitude()
            );
            // 소수점 첫째자리까지 반올림
            distance = Math.round(calculatedDistance * 10.0) / 10.0;
        }

        return GetParksWithDistanceResponse.ParkWithDistanceInfo.builder()
                .areaCode(park.getAreaCode())
                .areaName(park.getAreaName())
                .longitude(park.getLongitude())
                .latitude(park.getLatitude())
                .distance(distance)
                .build();
    }
}
