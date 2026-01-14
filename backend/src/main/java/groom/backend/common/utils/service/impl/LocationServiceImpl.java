package groom.backend.common.utils.service.impl;

import groom.backend.common.utils.dto.request.GetLocationRequest;
import groom.backend.common.utils.dto.response.GetLocationResponse;
import groom.backend.common.utils.dto.response.GetParksWithDistanceResponse;
import groom.backend.common.utils.mapper.LocationMapper;
import groom.backend.common.utils.service.spec.LocationService;
import groom.backend.domain.park.entity.Park;
import groom.backend.domain.park.repository.ParkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 위치 정보 조회 서비스 구현체
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationMapper locationMapper;
    private final ParkRepository parkRepository;

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

    /**
     * 현재 위치와 모든 공원 간의 거리를 계산합니다.
     *
     * @param request 위치 정보 요청 (경도, 위도)
     * @return 공원 정보와 거리 정보 응답
     */
    @Override
    @Transactional(readOnly = true)
    public GetParksWithDistanceResponse getParksWithDistance(GetLocationRequest request) {
        log.info("공원 거리 계산 시작 - 현재 위치 경도: {}, 위도: {}", request.getLongitude(), request.getLatitude());

        List<Park> parks = parkRepository.findAllByOrderByAreaCode();
        log.info("조회된 공원 수: {}", parks.size());

        List<GetParksWithDistanceResponse.ParkWithDistanceInfo> parkWithDistanceInfoList = parks.stream()
                .map(park -> locationMapper.toParkWithDistanceInfoDto(
                        park,
                        request.getLongitude(),
                        request.getLatitude()
                ))
                .collect(Collectors.toList());

        GetParksWithDistanceResponse response = GetParksWithDistanceResponse.builder()
                .currentLongitude(request.getLongitude())
                .currentLatitude(request.getLatitude())
                .parks(parkWithDistanceInfoList)
                .build();

        log.info("공원 거리 계산 완료 - 공원 수: {}", parkWithDistanceInfoList.size());

        return response;
    }
}
