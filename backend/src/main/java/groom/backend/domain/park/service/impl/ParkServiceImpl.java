package groom.backend.domain.park.service.impl;

import groom.backend.domain.park.dto.response.GetAllParksResponse;
import groom.backend.domain.park.dto.response.GetParksResponse;
import groom.backend.domain.park.entity.Park;
import groom.backend.domain.park.repository.ParkRepository;
import groom.backend.domain.park.service.spec.ParkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 공원 정보 조회 서비스 구현체
 * 커서 기반 페이지네이션으로 공원 리스트를 조회합니다.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ParkServiceImpl implements ParkService {
    private final ParkRepository parkRepository;

    private static final int DEFAULT_SIZE = 10;
    private static final int MAX_SIZE = 100;

    /**
     * 커서 기반 페이지네이션으로 공원 리스트를 조회합니다.
     *
     * @param cursor 커서 (areaCode), 첫 페이지는 null
     * @param size 페이지 크기 (기본값: 10, 최대값: 100)
     * @return 공원 리스트 및 다음 페이지 정보
     */
    @Override
    @Transactional(readOnly = true)
    public GetParksResponse getParks(String cursor, Integer size) {
        // size 검증 및 기본값 설정
        int pageSize = (size == null || size <= 0) ? DEFAULT_SIZE : Math.min(size, MAX_SIZE);
        
        log.info("공원 리스트 조회 시작 - cursor: {}, size: {}", cursor, pageSize);

        // 커서 기반 조회 (cursor보다 큰 areaCode를 가진 공원들을 조회)
        // size + 1개 조회하여 다음 페이지 존재 여부 확인
        Pageable pageable = PageRequest.of(0, pageSize + 1);
        List<Park> parks;
        if (cursor == null || cursor.trim().isEmpty()) {
            // 첫 페이지: 빈 문자열보다 큰 areaCode를 가진 공원들을 조회
            parks = parkRepository.findFirstByAreaCodeGreaterThanOrderByAreaCode("", pageable);
        } else {
            // 다음 페이지: cursor보다 큰 areaCode를 가진 공원들을 조회
            parks = parkRepository.findFirstByAreaCodeGreaterThanOrderByAreaCode(cursor, pageable);
        }

        // 다음 페이지 존재 여부 확인 (size + 1개 조회했으므로)
        boolean hasNext = parks.size() > pageSize;
        if (hasNext) {
            parks = parks.subList(0, pageSize); // 마지막 하나 제거
        }

        // 다음 커서 설정
        String nextCursor = null;
        if (hasNext && !parks.isEmpty()) {
            nextCursor = parks.get(parks.size() - 1).getAreaCode();
        }

        // DTO 변환
        List<GetParksResponse.ParkInfo> parkInfoList = parks.stream()
                .map(park -> GetParksResponse.ParkInfo.builder()
                        .areaCode(park.getAreaCode())
                        .areaName(park.getAreaName())
                        .longitude(park.getLongitude())
                        .latitude(park.getLatitude())
                        .build())
                .collect(Collectors.toList());

        GetParksResponse response = GetParksResponse.builder()
                .parks(parkInfoList)
                .nextCursor(nextCursor)
                .hasNext(hasNext)
                .size(parkInfoList.size())
                .build();

        log.info("공원 리스트 조회 완료 - 조회된 공원 수: {}, 다음 페이지 존재: {}", 
                parkInfoList.size(), hasNext);
        
        return response;
    }

    /**
     * 전체 공원 리스트를 조회합니다.
     *
     * @return 전체 공원 리스트
     */
    @Override
    @Transactional(readOnly = true)
    public GetAllParksResponse getAllParks() {
        log.info("전체 공원 리스트 조회 시작");

        List<Park> parks = parkRepository.findAllByOrderByAreaCode();

        List<GetAllParksResponse.ParkInfo> parkInfoList = parks.stream()
                .map(park -> GetAllParksResponse.ParkInfo.builder()
                        .areaCode(park.getAreaCode())
                        .areaName(park.getAreaName())
                        .longitude(park.getLongitude())
                        .latitude(park.getLatitude())
                        .build())
                .collect(Collectors.toList());

        GetAllParksResponse response = GetAllParksResponse.builder()
                .parks(parkInfoList)
                .totalCount(parkInfoList.size())
                .build();

        log.info("전체 공원 리스트 조회 완료 - 총 공원 수: {}", parkInfoList.size());

        return response;
    }
}

