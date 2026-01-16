package groom.backend.application.park.service.spec;

import groom.backend.application.park.dto.response.GetAllParksResponse;
import groom.backend.application.park.dto.response.GetParkResponse;
import java.util.List;

/**
 * 공원 정보 조회 애플리케이션 서비스 인터페이스
 * 여러 도메인(공원, 날씨, 인구)을 조합하여 공원 정보를 제공합니다.
 */
public interface ParkApplicationService {
    /**
     * 커서 기반 페이지네이션으로 공원 리스트를 조회합니다.
     *
     * @param cursor 커서 (areaCode), 첫 페이지는 null
     * @param size 페이지 크기 (기본값: 10, 최대값: 100)
     * @param longitude 현재 위치 경도 (거리 계산용, 선택)
     * @param latitude 현재 위치 위도 (거리 계산용, 선택)
     * @return 공원 리스트 및 다음 페이지 정보
     */
    GetAllParksResponse getParks(String cursor, Integer size, Double longitude, Double latitude);

    /**
     * areaCode로 특정 공원을 조회합니다.
     *
     * @param areaCode 지역 코드
     * @param longitude 현재 위치 경도 (거리 계산용, 선택)
     * @param latitude 현재 위치 위도 (거리 계산용, 선택)
     * @return 공원 정보
     */
    GetParkResponse getParkByAreaCode(String areaCode, Double longitude, Double latitude);

    /**
     * 혼잡도가 낮은 순으로 공원 리스트를 조회합니다 (커서 기반 페이지네이션).
     *
     * @param cursor 커서 (areaCode), 첫 페이지는 null
     * @param size 페이지 크기 (기본값: 10, 최대값: 100)
     * @param longitude 현재 위치 경도 (거리 계산용, 선택)
     * @param latitude 현재 위치 위도 (거리 계산용, 선택)
     * @return 공원 리스트 및 다음 페이지 정보 (혼잡도 낮은 순)
     */
    GetAllParksResponse getParksByLowCongestion(String cursor, Integer size, Double longitude, Double latitude);

    /**
     * 거리가 가까운 순으로 공원 리스트를 조회합니다 (커서 기반 페이지네이션).
     *
     * @param cursor 커서 (areaCode), 첫 페이지는 null
     * @param size 페이지 크기 (기본값: 10, 최대값: 100)
     * @param longitude 현재 위치 경도 (거리 계산용, 필수)
     * @param latitude 현재 위치 위도 (거리 계산용, 필수)
     * @return 공원 리스트 및 다음 페이지 정보 (거리 가까운 순)
     */
    GetAllParksResponse getParksByDistance(String cursor, Integer size, Double longitude, Double latitude);

    /**
     * 태그명으로 공원 리스트를 조회합니다 (커서 기반 페이지네이션).
     * 여러 태그명을 제공할 경우, 모든 태그를 모두 가지고 있는 공원만 조회됩니다 (AND 조건).
     *
     * @param tagNames 태그명 리스트
     * @param cursor 커서 (areaCode), 첫 페이지는 null
     * @param size 페이지 크기 (기본값: 10, 최대값: 100)
     * @param longitude 현재 위치 경도 (거리 계산용, 선택)
     * @param latitude 현재 위치 위도 (거리 계산용, 선택)
     * @return 공원 리스트 및 다음 페이지 정보
     */
    GetAllParksResponse getParksByTags(List<String> tagNames, String cursor, Integer size, Double longitude, Double latitude);
}

