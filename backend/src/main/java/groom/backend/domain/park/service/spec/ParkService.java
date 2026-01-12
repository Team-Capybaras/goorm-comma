package groom.backend.domain.park.service.spec;

import groom.backend.domain.park.dto.response.GetAllParksResponse;

/**
 * 공원 정보 조회 서비스 인터페이스
 */
public interface ParkService {
    /**
     * 전체 공원 리스트를 조회합니다.
     *
     * @return 전체 공원 리스트
     */
    GetAllParksResponse getAllParks();
}

