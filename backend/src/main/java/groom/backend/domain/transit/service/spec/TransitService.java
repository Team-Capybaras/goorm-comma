package groom.backend.domain.transit.service.spec;

import groom.backend.domain.transit.dto.response.GetTransitResponse;

/**
 * 대중교통 정보 조회 서비스 스펙
 * 지하철역, 버스 정류장, 공유 자전거 정보를 조회합니다.
 */
public interface TransitService {
    /**
     * 지역 코드로 대중교통 정보를 조회합니다.
     *
     * @param areaCode 지역 코드
     * @return 대중교통 정보 (지하철역, 버스 정류장, 공유 자전거)
     */
    GetTransitResponse getTransitByAreaCode(String areaCode);
}

