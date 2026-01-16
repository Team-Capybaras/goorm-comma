package groom.backend.domain.park.service.spec;

import groom.backend.domain.park.dto.response.GetAllParksBasicResponse;
import groom.backend.domain.park.dto.response.GetParkSearchResponse;

/**
 * 공원 정보 조회 서비스 스펙
 */
public interface ParkService {
    /**
     * 전체 공원의 기본 정보를 조회합니다.
     * 공원명, 지역코드, 경도, 위도를 반환합니다.
     *
     * @return 전체 공원 기본 정보 리스트
     */
    GetAllParksBasicResponse getAllParksBasic();

    /**
     * 검색어로 공원을 검색합니다.
     * 공원명에 검색어가 포함된 공원 중 첫 번째 공원의 areaCode와 공원명을 반환합니다.
     * 상세 페이지로 이동하기 위한 정보를 제공합니다.
     *
     * @param searchKeyword 검색어
     * @return 검색된 공원 정보 (areaCode, areaName만 포함, 없으면 null)
     */
    GetParkSearchResponse searchParkByAreaName(String searchKeyword);
}
