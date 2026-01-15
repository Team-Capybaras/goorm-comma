package groom.backend.domain.park.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 공원별 이미지 URL 정보 유틸리티 클래스
 */
public class ParkImages {
    
    /**
     * 공원별 이미지 URL 리스트 정보 (AREA_CD를 키로 사용)
     * 각 공원마다 여러 이미지 URL을 저장합니다.
     */
    private static final Map<String, List<String>> IMAGES = new HashMap<>();
    
    static {
        // 공원별 이미지 URL 정보 초기화 (AREA_CD를 키로 사용)
        // 각 공원마다 실제 존재하는 이미지 URL만 리스트로 저장 (빈 값 제외)
        IMAGES.put("POI085", List.of(
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI085_01.jpg",
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI085_02.jpg"
        )); // 강서한강공원
        IMAGES.put("POI086", List.of(
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI086_01.jpg",
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI086_02.jpg"
        )); // 고척돔
        IMAGES.put("POI087", List.of(
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI087_01.jpg",
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI087_02.jpg"
        )); // 광나루한강공원
        IMAGES.put("POI088", List.of(
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI088_01.jpg",
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI088_02.jpg"
        )); // 광화문광장
        IMAGES.put("POI089", List.of(
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI089_01.jpg",
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI089_02.jpg",
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI089_03.jpg",
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI089_04.jpg"
        )); // 국립중앙박물관·용산가족공원
        IMAGES.put("POI090", List.of(
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI090_01.jpg",
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI090_02.jpg"
        )); // 난지한강공원
        IMAGES.put("POI091", List.of(
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI091_01.jpg",
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI091_02.jpg"
        )); // 남산공원
        IMAGES.put("POI092", List.of(
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI092_01.jpg",
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI092_02.jpg",
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI092_03.jpg",
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI092_04.jpg"
        )); // 노들섬
        IMAGES.put("POI093", List.of(
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI093_01.jpg",
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI093_02.jpg"
        )); // 뚝섬한강공원
        IMAGES.put("POI094", List.of(
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI094_01.jpg",
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI094_02.jpg"
        )); // 망원한강공원
        IMAGES.put("POI095", List.of(
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI095_01.jpg",
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI095_02.jpg"
        )); // 반포한강공원
        IMAGES.put("POI096", List.of(
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI096_01.jpg",
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI096_02.jpg"
        )); // 북서울꿈의숲
        IMAGES.put("POI098", List.of(
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI098_01.jpg",
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI098_02.jpg"
        )); // 서리풀공원·몽마르뜨공원
        IMAGES.put("POI099", List.of(
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI099_01.jpg",
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI099_02.jpg"
        )); // 서울광장
        IMAGES.put("POI100", List.of(
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI100_01.jpg",
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI100_02.jpg"
        )); // 서울대공원
        IMAGES.put("POI101", List.of(
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI101_01.jpg",
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI101_02.jpg"
        )); // 서울숲공원
        IMAGES.put("POI102", List.of(
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI102_01.jpg",
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI102_02.jpg"
        )); // 아차산
        IMAGES.put("POI103", List.of(
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI103_01.jpg",
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI103_02.jpg"
        )); // 양화한강공원
        IMAGES.put("POI104", List.of(
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI104_01.jpg",
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI104_02.jpg"
        )); // 어린이대공원
        IMAGES.put("POI105", List.of(
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI105_01.jpg",
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI105_02.jpg"
        )); // 여의도한강공원
        IMAGES.put("POI106", List.of(
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI106_01.jpg",
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI106_02.jpg"
        )); // 월드컵공원
        IMAGES.put("POI107", List.of(
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI107_01.jpg",
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI107_02.jpg"
        )); // 응봉산
        IMAGES.put("POI108", List.of(
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI108_01.jpg",
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI108_02.jpg",
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI108_03.jpg",
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI108_04.jpg"
        )); // 이촌한강공원
        IMAGES.put("POI109", List.of(
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI109_01.jpg",
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI109_02.jpg"
        )); // 잠실종합운동장
        IMAGES.put("POI110", List.of(
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI110_01.jpg",
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI110_02.jpg"
        )); // 잠실한강공원
        IMAGES.put("POI111", List.of(
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI111_01.jpg",
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI111_02.jpg"
        )); // 잠원한강공원
        IMAGES.put("POI112", List.of(
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI112_01.jpg",
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI112_02.jpg"
        )); // 청계산
        IMAGES.put("POI113", List.of(
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI113_01.jpg",
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI113_02.jpg"
        )); // 청와대
        IMAGES.put("POI123", List.of(
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI123_01.jpg",
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI123_02.jpg"
        )); // 보라매공원
        IMAGES.put("POI124", List.of(
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI124_01.jpg",
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI124_02.jpg"
        )); // 서대문독립공원
        IMAGES.put("POI125", List.of(
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI125_01.jpg",
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI125_02.jpg"
        )); // 안양천
        IMAGES.put("POI126", List.of(
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI126_01.jpg",
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI126_02.jpg"
        )); // 여의서로
        IMAGES.put("POI127", List.of(
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI127_01.jpg",
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI127_02.jpg"
        )); // 올림픽공원
        IMAGES.put("POI128", List.of(
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI128_01.jpg",
                "https://goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com/goorm-comma/POI128_02.jpg"
        )); // 홍제폭포
    }
    
    /**
     * areaCode에 해당하는 공원 이미지 URL 리스트를 조회합니다.
     * 
     * @param areaCode 지역 코드
     * @return 공원 이미지 URL 리스트, 없으면 빈 리스트
     */
    public static List<String> getImageUrls(String areaCode) {
        return IMAGES.getOrDefault(areaCode, Collections.emptyList());
    }
    
    /**
     * 모든 areaCode 목록을 반환합니다.
     * 
     * @return areaCode Set
     */
    public static Set<String> getAllAreaCodes() {
        return IMAGES.keySet();
    }
}
