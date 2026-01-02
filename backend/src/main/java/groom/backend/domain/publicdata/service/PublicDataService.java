package groom.backend.domain.publicdata.service;

import groom.backend.domain.seoul.service.SeoulService;
import groom.backend.interfaces.seoul.dto.response.SeoulCityDataResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 공공 데이터 저장 서비스
 * 서울시 공공 API 결과를 DB에 저장합니다.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PublicDataService {
    private final SeoulService seoulService;
    private final PublicDataSaveService publicDataSaveService;

    /**
     * 서울시 공공 API를 호출하여 결과를 DB에 저장합니다.
     * 
     * @param areaNm 핫스팟 장소명
     * @param startIndex 시작 인덱스
     * @param endIndex 종료 인덱스
     * @return 저장된 데이터 개수 정보
     */
    @Transactional
    public PublicDataSaveResult saveCityData(String areaNm, Integer startIndex, Integer endIndex) {
        log.info("공공 데이터 저장 시작 - AREA_NM: {}, START: {}, END: {}", areaNm, startIndex, endIndex);

        // 1. API 호출
        SeoulCityDataResponse response = seoulService.getCityDataByAreaNm(areaNm, startIndex, endIndex);

        // 2. API 결과 검증
        if (response == null || response.getCityData() == null) {
            log.warn("API 응답이 비어있습니다. - AREA_NM: {}", areaNm);
            return PublicDataSaveResult.empty();
        }

        // 3. DTO를 엔티티로 변환하여 저장
        return publicDataSaveService.saveAll(response.getCityData());
    }
}

