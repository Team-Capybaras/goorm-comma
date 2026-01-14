package groom.backend.common.utils.service.spec;

import groom.backend.common.utils.dto.request.GetLocationRequest;
import groom.backend.common.utils.dto.response.GetLocationResponse;
import groom.backend.common.utils.dto.response.GetParksWithDistanceResponse;

/**
 * 위치 정보 조회 서비스 스펙
 */
public interface LocationService {
    /**
     * 현재 위치 정보를 받아 처리합니다.
     *
     * @param request 위치 정보 요청 (경도, 위도)
     * @return 위치 정보 응답
     */
    GetLocationResponse getCurrentLocation(GetLocationRequest request);

    /**
     * 현재 위치와 모든 공원 간의 거리를 계산합니다.
     *
     * @param request 위치 정보 요청 (경도, 위도)
     * @return 공원 정보와 거리 정보 응답
     */
    GetParksWithDistanceResponse getParksWithDistance(GetLocationRequest request);
}
