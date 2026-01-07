package groom.backend.domain.population.service.spec;

import groom.backend.domain.population.dto.response.GetPopulationResponse;

/**
 * 인구 정보 조회 서비스 스펙
 * 실시간 인구 현황 및 예보 정보를 조회합니다.
 */
public interface PopulationService {
    /**
     * 지역 코드로 최신 인구 정보를 조회합니다.
     *
     * @param areaCode 지역 코드
     * @return 인구 정보 (실시간 현황 및 예보)
     */
    GetPopulationResponse getPopulationByAreaCode(String areaCode);
}

