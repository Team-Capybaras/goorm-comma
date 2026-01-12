package groom.backend.domain.publicdata.service;

import groom.backend.domain.publicdata.dto.SavePublicDataResponse;

/**
 * 공공 데이터 저장 서비스 스펙
 * 서울시 공공 API 결과를 DB에 저장합니다.
 */
public interface PublicDataService {
    /**
     * 서울시 공공 API를 호출하여 결과를 DB에 저장합니다.
     *
     * @param areaNm 핫스팟 장소명
     * @param startIndex 시작 인덱스
     * @param endIndex 종료 인덱스
     * @return 저장된 데이터 개수 정보
     */
    SavePublicDataResponse saveCityData(String areaNm, Integer startIndex, Integer endIndex);
}
