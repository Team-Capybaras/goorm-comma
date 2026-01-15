package groom.backend.domain.park.config;

import groom.backend.domain.park.entity.Park;
import groom.backend.domain.park.repository.ParkRepository;
import groom.backend.domain.park.util.ParkCoordinates;
import groom.backend.domain.park.util.ParkImages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 공원 이미지 초기화 클래스
 * 서버 실행 시 공원별 이미지 URL을 초기화합니다.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ParkImageInitializer {
    private final ParkRepository parkRepository;

    /**
     * 애플리케이션 시작 시 공원 이미지 URL 초기화
     * @Order(2)로 설정하여 PublicDataScheduler(공원 데이터 생성) 이후에 실행되도록 합니다.
     */
    @EventListener(ApplicationReadyEvent.class)
    @Order(2)
    @Transactional
    public void onApplicationReady() {
        log.info("=== 공원 이미지 URL 초기화 시작 ===");
        
        initializeParkImages();
        
        log.info("=== 공원 이미지 URL 초기화 완료 ===");
    }

    /**
     * 공원별 이미지 URL 초기화
     */
    private void initializeParkImages() {
        log.info("공원별 이미지 URL 초기화 시작");
        
        int updatedCount = 0;
        // 모든 공원 이미지 정보를 순회하며 저장
        for (String areaCode : ParkImages.getAllAreaCodes()) {
            List<String> imageUrls = ParkImages.getImageUrls(areaCode);
            if (imageUrls == null || imageUrls.isEmpty()) {
                continue;
            }
            
            try {
                // 공원이 존재하는지 확인하고, 없으면 생성
                Park park = parkRepository.findByAreaCode(areaCode).orElse(null);
                
                if (park == null) {
                    // 공원이 없으면 생성 (이름은 나중에 PublicDataScheduler가 업데이트)
                    ParkCoordinates.ParkCoordinate coordinate = ParkCoordinates.getCoordinate(areaCode);
                    park = Park.builder()
                            .areaCode(areaCode)
                            .areaName(areaCode) // 임시 이름, 나중에 업데이트됨
                            .longitude(coordinate != null ? coordinate.getLongitude() : null)
                            .latitude(coordinate != null ? coordinate.getLatitude() : null)
                            .imageUrls(new ArrayList<>())
                            .build();
                    park = parkRepository.save(park);
                    log.info("공원 생성 완료 - areaCode: {}", areaCode);
                }
                
                // @ElementCollection을 사용할 때는 기존 리스트를 클리어하고 새로 추가하는 것이 안전합니다
                if (park.getImageUrls() == null) {
                    park.setImageUrls(new ArrayList<>());
                } else {
                    park.getImageUrls().clear();
                }
                park.getImageUrls().addAll(imageUrls);
                parkRepository.save(park);
                updatedCount++;
                log.info("공원 이미지 URL 초기화 완료 - areaCode: {}, 이미지 개수: {}", 
                        areaCode, imageUrls.size());
            } catch (Exception e) {
                log.error("공원 이미지 URL 초기화 중 오류 발생 - areaCode: {}, ERROR: {}", 
                        areaCode, e.getMessage(), e);
            }
        }
        
        log.info("공원별 이미지 URL 초기화 완료 - 업데이트된 공원 수: {}", updatedCount);
    }
}
