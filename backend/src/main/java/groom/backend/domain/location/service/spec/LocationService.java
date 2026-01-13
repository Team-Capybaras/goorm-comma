package groom.backend.domain.location.service.spec;

import groom.backend.domain.location.dto.request.GetLocationRequest;
import groom.backend.domain.location.dto.response.GetLocationResponse;

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
}
