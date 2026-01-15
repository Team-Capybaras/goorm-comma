package groom.backend.domain.tag.config;

import groom.backend.domain.park.entity.ParkTag;
import groom.backend.domain.park.repository.ParkRepository;
import groom.backend.domain.park.repository.ParkTagRepository;
import groom.backend.domain.tag.entity.Tag;
import groom.backend.domain.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 태그 초기화 클래스
 * 서버 실행 시 태그 마스터 데이터와 공원별 태그 연결 데이터를 초기화합니다.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class TagInitializer {
    private final TagRepository tagRepository;
    private final ParkTagRepository parkTagRepository;
    private final ParkRepository parkRepository;

    /**
     * 태그 마스터 데이터
     */
    private static final Map<String, String> TAG_MASTER = Map.of(
            "한강뷰", "한강을 볼 수 있는 공원",
            "평지", "평평한 지형의 공원",
            "산책", "산책하기 좋은 공원",
            "피크닉", "피크닉하기 좋은 공원",
            "랜드마크", "대표적인 랜드마크 공원",
            "역사/문화", "역사적, 문화적 가치가 있는 공원",
            "산/숲", "산이나 숲이 있는 공원",
            "수변/폭포", "물가나 폭포가 있는 공원"
    );

    /**
     * 공원별 태그 매핑 데이터
     */
    private static final Map<String, List<String>> PARK_TAG_MAPPING = createParkTagMapping();

    /**
     * 공원별 태그 매핑 데이터 생성
     */
    private static Map<String, List<String>> createParkTagMapping() {
        Map<String, List<String>> mapping = new HashMap<>();
        mapping.put("POI085", List.of("한강뷰", "평지", "산책", "피크닉"));
        mapping.put("POI086", List.of("랜드마크"));
        mapping.put("POI087", List.of("한강뷰", "평지", "피크닉", "산책"));
        mapping.put("POI088", List.of("평지", "역사/문화", "랜드마크"));
        mapping.put("POI089", List.of("평지", "피크닉", "역사/문화"));
        mapping.put("POI090", List.of("한강뷰", "평지", "피크닉", "산책"));
        mapping.put("POI091", List.of("산/숲", "역사/문화", "랜드마크"));
        mapping.put("POI092", List.of("한강뷰", "평지", "피크닉"));
        mapping.put("POI093", List.of("한강뷰", "평지", "피크닉", "산책"));
        mapping.put("POI094", List.of("한강뷰", "평지", "피크닉", "산책"));
        mapping.put("POI095", List.of("한강뷰", "평지", "피크닉", "랜드마크"));
        mapping.put("POI096", List.of("산/숲", "피크닉"));
        mapping.put("POI098", List.of("산/숲"));
        mapping.put("POI099", List.of("평지", "역사/문화", "랜드마크"));
        mapping.put("POI100", List.of("산/숲", "평지", "랜드마크"));
        mapping.put("POI101", List.of("평지", "피크닉", "산책"));
        mapping.put("POI102", List.of("산/숲"));
        mapping.put("POI103", List.of("한강뷰", "평지", "피크닉", "산책"));
        mapping.put("POI104", List.of("평지", "랜드마크"));
        mapping.put("POI105", List.of("한강뷰", "평지", "피크닉", "산책"));
        mapping.put("POI106", List.of("평지", "피크닉"));
        mapping.put("POI107", List.of("산/숲"));
        mapping.put("POI108", List.of("한강뷰", "평지", "산책", "피크닉"));
        mapping.put("POI109", List.of("랜드마크"));
        mapping.put("POI110", List.of("한강뷰", "평지", "피크닉", "산책"));
        mapping.put("POI111", List.of("한강뷰", "평지", "산책", "피크닉"));
        mapping.put("POI112", List.of("산/숲"));
        mapping.put("POI113", List.of("랜드마크", "산/숲"));
        mapping.put("POI123", List.of("평지", "산책"));
        mapping.put("POI124", List.of("평지", "역사/문화", "랜드마크"));
        mapping.put("POI125", List.of("평지", "수변/폭포", "산책"));
        mapping.put("POI126", List.of("평지"));
        mapping.put("POI127", List.of("평지", "피크닉", "랜드마크"));
        mapping.put("POI128", List.of("평지", "수변/폭포", "랜드마크"));
        return mapping;
    }

    /**
     * 애플리케이션 시작 시 태그 데이터 초기화
     */
    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void onApplicationReady() {
        log.info("=== 태그 데이터 초기화 시작 ===");
        
        initializeTags();
        initializeParkTags();
        
        log.info("=== 태그 데이터 초기화 완료 ===");
    }

    /**
     * 태그 마스터 데이터 초기화
     */
    private void initializeTags() {
        log.info("태그 마스터 데이터 초기화 시작");
        
        int tagId = 1;
        for (Map.Entry<String, String> entry : TAG_MASTER.entrySet()) {
            String tagName = entry.getKey();
            String description = entry.getValue();
            
            // 이미 존재하는 태그인지 확인
            Optional<Tag> existingTag = tagRepository.findByTagName(tagName);
            if (existingTag.isPresent()) {
                log.debug("태그가 이미 존재합니다 - tagName: {}", tagName);
                continue;
            }
            
            // 태그 생성
            Tag tag = Tag.builder()
                    .tagId(tagId)
                    .tagName(tagName)
                    .description(description)
                    .build();
            
            tagRepository.save(tag);
            log.debug("태그 생성 완료 - tagId: {}, tagName: {}", tagId, tagName);
            tagId++;
        }
        
        log.info("태그 마스터 데이터 초기화 완료");
    }

    /**
     * 공원별 태그 연결 데이터 초기화
     */
    private void initializeParkTags() {
        log.info("공원별 태그 연결 데이터 초기화 시작");
        
        int createdCount = 0;
        for (Map.Entry<String, List<String>> entry : PARK_TAG_MAPPING.entrySet()) {
            String areaCode = entry.getKey();
            List<String> tagNames = entry.getValue();
            
            // 공원이 존재하는지 확인
            if (!parkRepository.existsById(areaCode)) {
                log.warn("공원 정보를 찾을 수 없습니다 - areaCode: {}", areaCode);
                continue;
            }
            
            // 기존 태그 연결 삭제 (중복 방지)
            parkTagRepository.deleteByAreaCode(areaCode);
            
            // 태그 연결 생성
            for (String tagName : tagNames) {
                Tag tag = tagRepository.findByTagName(tagName)
                        .orElse(null);
                
                if (tag == null) {
                    log.warn("태그를 찾을 수 없습니다 - tagName: {}, areaCode: {}", tagName, areaCode);
                    continue;
                }
                
                String parkTagKey = areaCode + "_" + tag.getTagId();
                
                ParkTag parkTag = ParkTag.builder()
                        .parkTagKey(parkTagKey)
                        .areaCode(areaCode)
                        .tagId(tag.getTagId())
                        .build();
                
                parkTagRepository.save(parkTag);
                createdCount++;
                log.debug("공원 태그 연결 생성 완료 - areaCode: {}, tagName: {}", areaCode, tagName);
            }
        }
        
        log.info("공원별 태그 연결 데이터 초기화 완료 - 생성된 연결 수: {}", createdCount);
    }
}
