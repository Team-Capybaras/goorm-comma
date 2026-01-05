package groom.backend.domain.publicdata.scheduler;

import groom.backend.domain.park.entity.Park;
import groom.backend.domain.park.repository.ParkRepository;
import groom.backend.domain.publicdata.dto.SavePublicDataResponse;
import groom.backend.domain.publicdata.service.PublicDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 공공 데이터 자동 업데이트 스케줄러
 * 30분마다 저장된 모든 핫스팟 장소의 공공 데이터를 자동으로 업데이트합니다.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class PublicDataScheduler {
    private final PublicDataService publicDataService;
    private final ParkRepository parkRepository;

    /**
     * 30분마다 실행되는 공공 데이터 자동 업데이트 작업
     * 
     * 실행 시간: 매 시간의 0분과 30분 (예: 00:00, 00:30, 01:00, 01:30, ...)
     * 
     * 동작 방식:
     * 1. DB에서 저장된 모든 Park(핫스팟 장소) 조회
     * 2. 각 Park의 areaName을 사용하여 공공 API 호출
     * 3. API 응답 데이터를 DB에 저장/업데이트
     * 
     * @Scheduled 속성 설명:
     * - cron 표현식으로 매 30분마다 실행
     *   - 0: 초 (0초)
     *   - 30: 분 (30분 간격)
     *   - 시 (모든 시간)
     *   - 일 (모든 일)
     *   - 월 (모든 월)
     *   - 요일 (특정 요일 지정 안 함)
     * 
     * 대안:
     * - fixedRate = 1800000: 이전 실행 시작 시간부터 30분(1800000ms) 후 실행
     * - fixedDelay = 1800000: 이전 실행 완료 시간부터 30분 후 실행
     */
    @Scheduled(cron = "0 */30 * * * ?")
    public void updatePublicData() {
        log.info("=== 공공 데이터 자동 업데이트 시작 ===");
        
        try {
            // 1. DB에서 저장된 모든 핫스팟 장소 조회
            List<Park> parks = parkRepository.findAll();
            
            if (parks.isEmpty()) {
                log.warn("업데이트할 핫스팟 장소가 없습니다. 먼저 공공 데이터를 저장해주세요.");
                return;
            }
            
            log.info("총 {}개의 핫스팟 장소 데이터 업데이트 시작", parks.size());
            
            int successCount = 0;
            int failCount = 0;
            
            // 2. 각 핫스팟 장소에 대해 공공 API 호출 및 저장
            for (Park park : parks) {
                try {
                    String areaName = park.getAreaName();
                    String areaCode = park.getAreaCode();
                    
                    if (areaName == null || areaName.trim().isEmpty()) {
                        log.warn("핫스팟 장소명이 없어 스킵합니다. AREA_CODE: {}", areaCode);
                        failCount++;
                        continue;
                    }
                    
                    log.debug("핫스팟 장소 업데이트 시작 - AREA_CODE: {}, AREA_NAME: {}", areaCode, areaName);
                    
                    // 공공 API 호출 및 저장 (startIndex=1, endIndex=5로 고정)
                    SavePublicDataResponse response = publicDataService.saveCityData(areaName, 1, 5);
                    
                    if (response != null) {
                        successCount++;
                        log.debug("핫스팟 장소 업데이트 완료 - AREA_CODE: {}, AREA_NAME: {}, RESULT: {}", 
                                areaCode, areaName, response);
                    } else {
                        failCount++;
                        log.warn("핫스팟 장소 업데이트 실패 - AREA_CODE: {}, AREA_NAME: {}", areaCode, areaName);
                    }
                    
                } catch (Exception e) {
                    failCount++;
                    log.error("핫스팟 장소 업데이트 중 오류 발생 - AREA_CODE: {}, AREA_NAME: {}, ERROR: {}", 
                            park.getAreaCode(), park.getAreaName(), e.getMessage(), e);
                }
            }
            
            log.info("=== 공공 데이터 자동 업데이트 완료 ===");
            log.info("성공: {}개, 실패: {}개, 전체: {}개", successCount, failCount, parks.size());
            
        } catch (Exception e) {
            log.error("공공 데이터 자동 업데이트 작업 중 예상치 못한 오류 발생", e);
        }
    }
}

