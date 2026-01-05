package groom.backend.domain.seoul.service;

import groom.backend.interfaces.seoul.dto.response.SeoulCityDataResponse;

/**
 * 서울시 공공 API 서비스 스펙
 * 핫스팟 장소 정보 조회 비즈니스 로직을 처리합니다.
 */
public interface SeoulService {
    /**
     * 핫스팟 장소명으로 서울시 공공 API를 조회합니다.
     *
     * @param areaNm 핫스팟 장소명
     * @param startIndex 시작 인덱스 (기본값: 1)
     * @param endIndex 종료 인덱스 (기본값: 5)
     * @return 서울시 공공 API 응답
     */
    SeoulCityDataResponse getCityDataByAreaNm(String areaNm, Integer startIndex, Integer endIndex);
}
