package groom.backend.domain.location.service.impl;

import groom.backend.domain.location.dto.request.GetLocationRequest;
import groom.backend.domain.location.dto.response.GetLocationResponse;
import groom.backend.domain.location.mapper.LocationMapper;
import groom.backend.domain.location.service.spec.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 위치 정보 조회 서비스 구현체
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationMapper locationMapper;

    /**
     * 현재 위치 정보를 받아 처리합니다.
     *
     * @param request 위치 정보 요청 (경도, 위도)
     * @return 위치 정보 응답
     */
    @Override
    @Transactional(readOnly = true)
    public GetLocationResponse getCurrentLocation(GetLocationRequest request) {
        log.info("현재 위치 정보 수신 - 경도: {}, 위도: {}", request.getLongitude(), request.getLatitude());

        GetLocationResponse response = locationMapper.toGetLocationResponseDto(request);

        log.info("현재 위치 정보 처리 완료 - 경도: {}, 위도: {}", response.getLongitude(), response.getLatitude());

        return response;
    }
}
