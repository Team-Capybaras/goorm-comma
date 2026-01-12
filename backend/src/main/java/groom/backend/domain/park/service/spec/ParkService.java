package groom.backend.domain.park.service.spec;

import groom.backend.domain.park.dto.response.GetAllParksResponse;

/**
 * 공원 정보 조회 서비스 인터페이스
 */
public interface ParkService {
    /**
     * 커서 기반 페이지네이션으로 공원 리스트를 조회합니다.
     *
     * @param cursor 커서 (areaCode), 첫 페이지는 null
     * @param size 페이지 크기 (기본값: 10, 최대값: 100)
     * @return 공원 리스트 및 다음 페이지 정보
     */
    GetAllParksResponse getParks(String cursor, Integer size);
}

