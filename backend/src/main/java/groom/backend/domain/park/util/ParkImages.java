package groom.backend.domain.park.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 공원별 이미지 URL 정보 유틸리티 클래스
 */
public class ParkImages {
    
    /**
     * 공원별 이미지 URL 정보 (AREA_CD를 키로 사용)
     * 여러 이미지가 있는 경우 첫 번째 이미지 URL을 사용합니다.
     */
    private static final Map<String, String> IMAGES = new HashMap<>();
    
    static {
        // 공원별 이미지 URL 정보 초기화 (AREA_CD를 키로 사용)
        // 여러 이미지가 있는 경우 첫 번째 이미지 URL을 사용
        IMAGES.put("POI085", "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/park/POI085.jpg"); // 강서한강공원
        IMAGES.put("POI086", "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/park/POI086.jpg"); // 고척돔
        IMAGES.put("POI087", "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/park/POI087.jpg"); // 광나루한강공원
        IMAGES.put("POI088", "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/park/POI088.jpg"); // 광화문광장
        IMAGES.put("POI089", "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/park/POI089.jpg"); // 국립중앙박물관·용산가족공원
        IMAGES.put("POI090", "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/park/POI090.jpg"); // 난지한강공원
        IMAGES.put("POI091", "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/park/POI091.jpg"); // 남산공원
        IMAGES.put("POI092", "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/park/POI092.jpg"); // 노들섬
        IMAGES.put("POI093", "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/park/POI093.jpg"); // 뚝섬한강공원
        IMAGES.put("POI094", "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/park/POI094.jpg"); // 망원한강공원
        IMAGES.put("POI095", "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/park/POI095.jpg"); // 반포한강공원
        IMAGES.put("POI096", "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/park/POI096.jpg"); // 북서울꿈의숲
        IMAGES.put("POI098", "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/park/POI098.jpg"); // 서리풀공원·몽마르뜨공원
        IMAGES.put("POI099", "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/park/POI099.jpg"); // 서울광장
        IMAGES.put("POI100", "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/park/POI100.jpg"); // 서울대공원
        IMAGES.put("POI101", "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/park/POI101.jpg"); // 서울숲공원
        IMAGES.put("POI102", "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/park/POI102.jpg"); // 아차산
        IMAGES.put("POI103", "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/park/POI103.jpg"); // 양화한강공원
        IMAGES.put("POI104", "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/park/POI104.jpg"); // 어린이대공원
        IMAGES.put("POI105", "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/park/POI105.jpg"); // 여의도한강공원
        IMAGES.put("POI106", "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/park/POI106.jpg"); // 월드컵공원
        IMAGES.put("POI107", "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/park/POI107.jpg"); // 응봉산
        IMAGES.put("POI108", "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/park/POI108.jpg"); // 이촌한강공원
        IMAGES.put("POI109", "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/park/POI109.jpg"); // 잠실종합운동장
        IMAGES.put("POI110", "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/park/POI110.jpg"); // 잠실한강공원
        IMAGES.put("POI111", "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/park/POI111.jpg"); // 잠원한강공원
        IMAGES.put("POI112", "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/park/POI112.jpg"); // 청계산
        IMAGES.put("POI113", "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/park/POI113.jpg"); // 청와대
        IMAGES.put("POI123", "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/park/POI123.jpg"); // 보라매공원
        IMAGES.put("POI124", "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/park/POI124.jpg"); // 서대문독립공원
        IMAGES.put("POI125", "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/park/POI125.jpg"); // 안양천
        IMAGES.put("POI126", "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/park/POI126.jpg"); // 여의서로
        IMAGES.put("POI127", "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/park/POI127.jpg"); // 올림픽공원
        IMAGES.put("POI128", "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/park/POI128.jpg"); // 홍제폭포
    }
    
    /**
     * areaCode에 해당하는 공원 이미지 URL을 조회합니다.
     * 
     * @param areaCode 지역 코드
     * @return 공원 이미지 URL, 없으면 null
     */
    public static String getImageUrl(String areaCode) {
        return IMAGES.get(areaCode);
    }
    
    /**
     * 모든 areaCode 목록을 반환합니다.
     * 
     * @return areaCode Set
     */
    public static java.util.Set<String> getAllAreaCodes() {
        return IMAGES.keySet();
    }
}
