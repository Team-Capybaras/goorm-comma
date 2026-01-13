package groom.backend.domain.park.service.spec;

import groom.backend.domain.park.dto.response.GetAllParksBasicResponse;

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
}
