package groom.backend.domain.park.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 공원별 경도, 위도 정보 유틸리티 클래스
 */
public class ParkCoordinates {
    
    /**
     * 공원별 경도, 위도 정보 (AREA_CD를 키로 사용)
     */
    private static final Map<String, ParkCoordinate> COORDINATES = new HashMap<>();
    
    static {
        // 공원별 경도, 위도 정보 초기화 (AREA_CD를 키로 사용)
        COORDINATES.put("POI085", new ParkCoordinate(126.815256, 37.588272)); // 강서한강공원
        COORDINATES.put("POI086", new ParkCoordinate(126.866998, 37.497058)); // 고척돔
        COORDINATES.put("POI087", new ParkCoordinate(127.120071, 37.548939)); // 광나루한강공원
        COORDINATES.put("POI088", new ParkCoordinate(126.975966, 37.573793)); // 광화문광장
        COORDINATES.put("POI089", new ParkCoordinate(126.98397, 37.521267)); // 국립중앙박물관·용산가족공원
        COORDINATES.put("POI090", new ParkCoordinate(126.878076, 37.566974)); // 난지한강공원
        COORDINATES.put("POI091", new ParkCoordinate(126.98084, 37.554048)); // 남산공원
        COORDINATES.put("POI092", new ParkCoordinate(126.960096, 37.518681)); // 노들섬
        COORDINATES.put("POI093", new ParkCoordinate(127.069903, 37.529546)); // 뚝섬한강공원
        COORDINATES.put("POI094", new ParkCoordinate(126.898604, 37.552919)); // 망원한강공원
        COORDINATES.put("POI095", new ParkCoordinate(126.995963, 37.510827)); // 반포한강공원
        COORDINATES.put("POI096", new ParkCoordinate(127.04056, 37.62161)); // 북서울꿈의숲
        COORDINATES.put("POI098", new ParkCoordinate(126.999838, 37.490796)); // 서리풀공원·몽마르뜨공원
        COORDINATES.put("POI099", new ParkCoordinate(126.978046, 37.565772)); // 서울광장
        COORDINATES.put("POI100", new ParkCoordinate(127.017136, 37.42783)); // 서울대공원
        COORDINATES.put("POI101", new ParkCoordinate(127.037464, 37.544617)); // 서울숲공원
        COORDINATES.put("POI102", new ParkCoordinate(127.103634, 37.572188)); // 아차산
        COORDINATES.put("POI103", new ParkCoordinate(126.902297, 37.538471)); // 양화한강공원
        COORDINATES.put("POI104", new ParkCoordinate(127.08178, 37.549542)); // 어린이대공원
        COORDINATES.put("POI105", new ParkCoordinate(126.934754, 37.526906)); // 여의도한강공원
        COORDINATES.put("POI106", new ParkCoordinate(126.893597, 37.564061)); // 월드컵공원
        COORDINATES.put("POI107", new ParkCoordinate(127.029869, 37.54872)); // 응봉산
        COORDINATES.put("POI108", new ParkCoordinate(126.971273, 37.518894)); // 이촌한강공원
        COORDINATES.put("POI109", new ParkCoordinate(127.072213, 37.522129)); // 잠실종합운동장
        COORDINATES.put("POI110", new ParkCoordinate(127.086648, 37.517709)); // 잠실한강공원
        COORDINATES.put("POI111", new ParkCoordinate(127.009401, 37.521718)); // 잠원한강공원
        COORDINATES.put("POI112", new ParkCoordinate(127.041707, 37.414744)); // 청계산
        COORDINATES.put("POI113", new ParkCoordinate(126.979307, 37.583920)); // 청와대
        COORDINATES.put("POI123", new ParkCoordinate(126.919756, 37.493086)); // 보라매공원
        COORDINATES.put("POI124", new ParkCoordinate(126.955073, 37.575422)); // 서대문독립공원
        COORDINATES.put("POI125", new ParkCoordinate(126.880855, 37.522477)); // 안양천
        COORDINATES.put("POI126", new ParkCoordinate(126.919962, 37.533304)); // 여의서로
        COORDINATES.put("POI127", new ParkCoordinate(127.121494, 37.520865)); // 올림픽공원
        COORDINATES.put("POI128", new ParkCoordinate(126.937798, 37.581484)); // 홍제폭포
    }
    
    /**
     * 공원 좌표 정보를 담는 내부 클래스
     */
    public static class ParkCoordinate {
        private final Double longitude;
        private final Double latitude;
        
        public ParkCoordinate(Double longitude, Double latitude) {
            this.longitude = longitude;
            this.latitude = latitude;
        }
        
        public Double getLongitude() {
            return longitude;
        }
        
        public Double getLatitude() {
            return latitude;
        }
    }
    
    /**
     * areaCode에 해당하는 공원 좌표 정보를 조회합니다.
     * 
     * @param areaCode 지역 코드
     * @return 공원 좌표 정보, 없으면 null
     */
    public static ParkCoordinate getCoordinate(String areaCode) {
        return COORDINATES.get(areaCode);
    }
    
    /**
     * 모든 areaCode 목록을 반환합니다.
     * 
     * @return areaCode Set
     */
    public static java.util.Set<String> getAllAreaCodes() {
        return COORDINATES.keySet();
    }
}

