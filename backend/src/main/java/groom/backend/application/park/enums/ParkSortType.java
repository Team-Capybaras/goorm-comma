package groom.backend.application.park.enums;

/**
 * 공원 리스트 정렬 타입
 */
public enum ParkSortType {
    /**
     * 기본 정렬 (areaCode 오름차순)
     */
    DEFAULT,
    
    /**
     * 혼잡도 낮은 순 정렬
     */
    LOW_CONGESTION,
    
    /**
     * 거리 가까운 순 정렬
     */
    BY_DISTANCE
}
