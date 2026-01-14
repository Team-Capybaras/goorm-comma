package groom.backend.domain.publicdata.scheduler;

import groom.backend.domain.park.entity.Park;
import groom.backend.domain.park.repository.ParkRepository;
import groom.backend.domain.park.util.ParkCoordinates;
import groom.backend.domain.park.util.ParkImages;
import groom.backend.domain.publicdata.dto.SavePublicDataResponse;
import groom.backend.domain.publicdata.service.PublicDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 공공 데이터 자동 업데이트 스케줄러
 * 
 * 애플리케이션 시작 시 즉시 실행되고, 이후 30분마다 자동으로 업데이트합니다.
 * 
 * 업데이트 대상 공원 리스트:
 * - 강서한강공원, 고척돔, 광나루한강공원, 광화문광장, 국립중앙박물관·용산가족공원
 * - 난지한강공원, 남산공원, 노들섬, 뚝섬한강공원, 망원한강공원
 * - 반포한강공원, 북서울꿈의숲, 서리풀공원·몽마르뜨공원, 서울광장, 서울대공원
 * - 서울숲공원, 아차산, 양화한강공원, 어린이대공원, 여의도한강공원
 * - 월드컵공원, 응봉산, 이촌한강공원, 잠실종합운동장, 잠실한강공원
 * - 잠원한강공원, 청계산, 청와대, 보라매공원, 서대문독립공원
 * - 안양천, 여의서로, 올림픽공원, 홍제폭포
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class PublicDataScheduler {
    private final PublicDataService publicDataService;
    private final ParkRepository parkRepository;

    /**
     * 업데이트 대상 공원 리스트 (AREA_NM)
     * 공공 API 요청 시 이 이름들을 사용합니다.
     */
    private static final List<String> TARGET_AREA_NAMES = List.of(
            "강서한강공원",
            "고척돔",
            "광나루한강공원",
            "광화문광장",
            "국립중앙박물관·용산가족공원",
            "난지한강공원",
            "남산공원",
            "노들섬",
            "뚝섬한강공원",
            "망원한강공원",
            "반포한강공원",
            "북서울꿈의숲",
            "서리풀공원·몽마르뜨공원",
            "서울광장",
            "서울대공원",
            "서울숲공원",
            "아차산",
            "양화한강공원",
            "어린이대공원",
            "여의도한강공원",
            "월드컵공원",
            "응봉산",
            "이촌한강공원",
            "잠실종합운동장",
            "잠실한강공원",
            "잠원한강공원",
            "청계산",
            "청와대",
            "보라매공원",
            "서대문독립공원",
            "안양천",
            "여의서로",
            "올림픽공원",
            "홍제폭포"
    );

    /**
     * 애플리케이션 시작 시 즉시 실행되는 공공 데이터 업데이트 작업
     * 
     * ApplicationReadyEvent는 Spring Boot 애플리케이션이 완전히 시작된 후 발생하는 이벤트입니다.
     * 이 시점에 실행하면 모든 빈이 초기화되고 데이터베이스 연결도 준비된 상태입니다.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        log.info("=== 애플리케이션 시작 시 공공 데이터 초기 업데이트 시작 ===");
        
        // 공원 경도, 위도 정보 저장
        updateParkCoordinates();
        
        // 공원 이미지 URL 정보 저장
        updateParkImages();
        
        updatePublicData();
    }
    
    /**
     * 공원별 경도, 위도 정보를 DB에 저장합니다.
     * 서버 실행 시 한 번만 실행됩니다.
     */
    private void updateParkCoordinates() {
        log.info("공원 경도, 위도 정보 저장 시작");
        
        int updatedCount = 0;
        // 모든 공원 좌표 정보를 순회하며 저장
        for (String areaCode : ParkCoordinates.getAllAreaCodes()) {
            ParkCoordinates.ParkCoordinate coordinate = ParkCoordinates.getCoordinate(areaCode);
            if (coordinate == null) {
                continue;
            }
            
            try {
                Park park = parkRepository.findByAreaCode(areaCode).orElse(null);
                if (park != null) {
                    park.setLongitude(coordinate.getLongitude());
                    park.setLatitude(coordinate.getLatitude());
                    parkRepository.save(park);
                    updatedCount++;
                    log.debug("공원 좌표 업데이트 완료 - AREA_CODE: {}, 경도: {}, 위도: {}", 
                            areaCode, coordinate.getLongitude(), coordinate.getLatitude());
                } else {
                    log.warn("공원 정보를 찾을 수 없습니다 - AREA_CODE: {}", areaCode);
                }
            } catch (Exception e) {
                log.error("공원 좌표 업데이트 중 오류 발생 - AREA_CODE: {}, ERROR: {}", 
                        areaCode, e.getMessage(), e);
            }
        }
        
        log.info("공원 경도, 위도 정보 저장 완료 - 업데이트된 공원 수: {}", updatedCount);
    }

    /**
     * 공원별 이미지 URL 정보를 DB에 저장합니다.
     * 서버 실행 시 한 번만 실행됩니다.
     */
    private void updateParkImages() {
        log.info("공원 이미지 URL 정보 저장 시작");
        
        int updatedCount = 0;
        // 모든 공원 이미지 정보를 순회하며 저장
        for (String areaCode : ParkImages.getAllAreaCodes()) {
            String imageUrl = ParkImages.getImageUrl(areaCode);
            if (imageUrl == null) {
                continue;
            }
            
            try {
                Park park = parkRepository.findByAreaCode(areaCode).orElse(null);
                if (park != null) {
                    park.setImageUrl(imageUrl);
                    parkRepository.save(park);
                    updatedCount++;
                    log.debug("공원 이미지 URL 업데이트 완료 - AREA_CODE: {}, 이미지 URL: {}", 
                            areaCode, imageUrl);
                } else {
                    log.warn("공원 정보를 찾을 수 없습니다 - AREA_CODE: {}", areaCode);
                }
            } catch (Exception e) {
                log.error("공원 이미지 URL 업데이트 중 오류 발생 - AREA_CODE: {}, ERROR: {}", 
                        areaCode, e.getMessage(), e);
            }
        }
        
        log.info("공원 이미지 URL 정보 저장 완료 - 업데이트된 공원 수: {}", updatedCount);
    }

    /**
     * 30분마다 실행되는 공공 데이터 자동 업데이트 작업
     * 
     * 실행 시간: 매 시간의 0분과 30분 (예: 00:00, 00:30, 01:00, 01:30, ...)
     * 
     * cron 표현식 설명:
     *   - 0: 초 (0초)
     *   - *30: 분 (30분 간격, 즉 0분과 30분)
     *   - *: 시 (모든 시간)
     *   - *: 일 (모든 일)
     *   - *: 월 (모든 월)
     *   - ?: 요일 (특정 요일 지정 안 함)
     * 
     * 동작 방식:
     * 1. 하드코딩된 공원 리스트(TARGET_AREA_NAMES)를 순회
     * 2. 각 공원명(AREA_NM)을 사용하여 공공 API 호출
     * 3. API 응답 데이터를 DB에 저장/업데이트
     */
    @Scheduled(cron = "0 */30 * * * ?")
    public void scheduledUpdate() {
        log.info("=== 스케줄러에 의한 공공 데이터 자동 업데이트 시작 ===");
        updatePublicData();
    }

    /**
     * 공공 데이터 업데이트 공통 로직
     * 
     * 하드코딩된 공원 리스트를 순회하며 각 공원의 공공 데이터를 업데이트합니다.
     */
    private void updatePublicData() {
        log.info("총 {}개의 공원 데이터 업데이트 시작", TARGET_AREA_NAMES.size());
        
        int successCount = 0;
        int failCount = 0;
        List<String> failedAreaNames = new ArrayList<>();
        List<String> failedReasons = new ArrayList<>();
        
        // 각 공원에 대해 공공 API 호출 및 저장
        for (String areaName : TARGET_AREA_NAMES) {
            try {
                log.debug("공원 데이터 업데이트 시작 - AREA_NAME: {}", areaName);
                
                // 공공 API 호출 및 저장 (startIndex=1, endIndex=5로 고정)
                SavePublicDataResponse response = publicDataService.saveCityData(areaName, 1, 5);
                
                if (response != null) {
                    successCount++;
                    log.debug("공원 데이터 업데이트 완료 - AREA_NAME: {}, RESULT: {}", areaName, response);
                } else {
                    failCount++;
                    failedAreaNames.add(areaName);
                    failedReasons.add("API 응답이 null입니다");
                    log.warn("공원 데이터 업데이트 실패 - AREA_NAME: {}", areaName);
                }
                
            } catch (Exception e) {
                failCount++;
                failedAreaNames.add(areaName);
                failedReasons.add(e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName());
                log.error("공원 데이터 업데이트 중 오류 발생 - AREA_NAME: {}, ERROR: {}", 
                        areaName, e.getMessage(), e);
            }
        }
        
        log.info("=== 공공 데이터 자동 업데이트 완료 ===");
        log.info("성공: {}개, 실패: {}개, 전체: {}개", successCount, failCount, TARGET_AREA_NAMES.size());
        
        // 실패한 공원 정보 상세 로깅
        if (failCount > 0) {
            log.warn("=== 실패한 공원 목록 ({}개) ===", failCount);
            for (int i = 0; i < failedAreaNames.size(); i++) {
                log.warn("  - {}: {}", failedAreaNames.get(i), failedReasons.get(i));
            }
        }
    }
}

