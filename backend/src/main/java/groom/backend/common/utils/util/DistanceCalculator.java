package groom.backend.common.utils.util;

/**
 * 두 지점 간 거리 계산 유틸리티
 * Haversine 공식을 사용하여 지구상의 두 지점 간 직선거리를 계산합니다.
 */
public class DistanceCalculator {
    
    /**
     * 지구의 반지름 (킬로미터)
     */
    private static final double EARTH_RADIUS_KM = 6371.0;

    /**
     * 두 지점 간 직선거리를 계산합니다 (Haversine 공식).
     * 
     * @param lat1 첫 번째 지점의 위도
     * @param lon1 첫 번째 지점의 경도
     * @param lat2 두 번째 지점의 위도
     * @param lon2 두 번째 지점의 경도
     * @return 두 지점 간 거리 (킬로미터)
     */
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // 위도와 경도를 라디안으로 변환
        double lat1Rad = Math.toRadians(lat1);
        double lat2Rad = Math.toRadians(lat2);
        double deltaLatRad = Math.toRadians(lat2 - lat1);
        double deltaLonRad = Math.toRadians(lon2 - lon1);

        // Haversine 공식
        double a = Math.sin(deltaLatRad / 2) * Math.sin(deltaLatRad / 2)
                + Math.cos(lat1Rad) * Math.cos(lat2Rad)
                * Math.sin(deltaLonRad / 2) * Math.sin(deltaLonRad / 2);
        
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        
        // 거리 계산 (킬로미터)
        return EARTH_RADIUS_KM * c;
    }

    /**
     * 두 지점 간 직선거리를 계산합니다 (미터 단위).
     * 
     * @param lat1 첫 번째 지점의 위도
     * @param lon1 첫 번째 지점의 경도
     * @param lat2 두 번째 지점의 위도
     * @param lon2 두 번째 지점의 경도
     * @return 두 지점 간 거리 (미터)
     */
    public static double calculateDistanceInMeters(double lat1, double lon1, double lat2, double lon2) {
        return calculateDistance(lat1, lon1, lat2, lon2) * 1000;
    }
}
