package groom.backend.application.park.service.spec;

import groom.backend.application.park.dto.response.GetAllParksResponse;
import groom.backend.application.park.dto.response.GetParkResponse;
import groom.backend.application.park.enums.ParkSortType;
import java.util.List;

/**
 * 공원 정보 조회 애플리케이션 서비스 인터페이스
 * 여러 도메인(공원, 날씨, 인구)을 조합하여 공원 정보를 제공합니다.
 */
public interface ParkApplicationService {
    /**
     * 커서 기반 페이지네이션으로 공원 리스트를 조회합니다.
     * 필터(tagNames)와 정렬(sort)을 함께 사용할 수 있습니다.
     *
     * @param cursor 커서 (areaCode), 첫 페이지는 null
     * @param size 페이지 크기 (기본값: 10, 최대값: 100)
     * @param sort 정렬 타입 (기본값: DEFAULT)
     * @param tagNames 태그명 리스트 (필터링용, 선택)
     * @param longitude 현재 위치 경도 (거리 계산 및 BY_DISTANCE 정렬용, BY_DISTANCE 정렬 시 필수)
     * @param latitude 현재 위치 위도 (거리 계산 및 BY_DISTANCE 정렬용, BY_DISTANCE 정렬 시 필수)
     * @return 공원 리스트 및 다음 페이지 정보
     */
    GetAllParksResponse getParks(String cursor, Integer size, ParkSortType sort, List<String> tagNames, Double longitude, Double latitude);

    /**
     * areaCode로 특정 공원을 조회합니다.
     *
     * @param areaCode 지역 코드
     * @param longitude 현재 위치 경도 (거리 계산용, 선택)
     * @param latitude 현재 위치 위도 (거리 계산용, 선택)
     * @return 공원 정보
     */
    GetParkResponse getParkByAreaCode(String areaCode, Double longitude, Double latitude);
}

