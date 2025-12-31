package groom.backend.domain.seoul.service;

import groom.backend.interfaces.seoul.SeoulApiClient;
import groom.backend.interfaces.seoul.dto.request.SeoulCityDataRequest;
import groom.backend.interfaces.seoul.dto.response.SeoulCityDataResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 서울시 공공 API 서비스
 * 핫스팟 장소 정보 조회 비즈니스 로직을 처리합니다.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SeoulService {
    private final SeoulApiClient seoulApiClient;

    /**
     * 핫스팟 장소명으로 서울시 공공 API를 조회합니다.
     * 
     * @param areaNm 핫스팟 장소명
     * @param startIndex 시작 인덱스 (기본값: 1)
     * @param endIndex 종료 인덱스 (기본값: 5)
     * @return 서울시 공공 API 응답
     */
    public SeoulCityDataResponse getCityDataByAreaNm(String areaNm, Integer startIndex, Integer endIndex) {
        SeoulCityDataRequest request = SeoulCityDataRequest.builder()
                .areaNm(areaNm)
                .startIndex(startIndex != null ? startIndex : 1)
                .endIndex(endIndex != null ? endIndex : 5)
                .build();

        log.info("서울시 공공 API 조회 요청 - AREA_NM: {}, START: {}, END: {}", 
                areaNm, request.getStartIndex(), request.getEndIndex());

        return seoulApiClient.getCityData(request);
    }
}

