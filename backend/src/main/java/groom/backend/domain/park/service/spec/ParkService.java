package groom.backend.domain.park.service.spec;

import groom.backend.domain.park.dto.response.GetAllParksResponse;
import groom.backend.domain.park.dto.response.GetParksResponse;

/**
 * 공원 정보 조회 서비스 인터페이스
 */
public interface ParkService {
    /**
     * 커서 기반 페이지네이션으로 공원 리스트를 조회합니다.
     *
     * @param cursor 커서 (areaCode), 첫 페이지는 null
     * @param size 페이지 크기 (기본값: 10)
     * @return 공원 리스트 및 다음 페이지 정보
     */
    GetParksResponse getParks(String cursor, Integer size);

    /**
     * 전체 공원 리스트를 조회합니다.
     *
     * @return 전체 공원 리스트
     */
    GetAllParksResponse getAllParks();
}

