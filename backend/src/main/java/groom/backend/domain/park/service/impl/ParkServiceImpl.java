package groom.backend.domain.park.service.impl;

import groom.backend.common.exception.BusinessException;
import groom.backend.common.exception.ErrorCode;
import groom.backend.domain.park.dto.response.GetAllParksBasicResponse;
import groom.backend.domain.park.dto.response.GetParkSearchResponse;
import groom.backend.domain.park.entity.Park;
import groom.backend.domain.park.mapper.ParkMapper;
import groom.backend.domain.park.repository.ParkRepository;
import groom.backend.domain.park.service.spec.ParkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 공원 정보 조회 서비스 구현체
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ParkServiceImpl implements ParkService {
    private final ParkRepository parkRepository;
    private final ParkMapper parkMapper;

    /**
     * 전체 공원의 기본 정보를 조회합니다.
     * 공원명, 지역코드, 경도, 위도를 반환합니다.
     *
     * @return 전체 공원 기본 정보 리스트
     */
    @Override
    @Transactional(readOnly = true)
    public GetAllParksBasicResponse getAllParksBasic() {
        log.info("전체 공원 기본 정보 조회 시작");

        List<Park> parks = parkRepository.findAllByOrderByAreaCode();
        log.info("조회된 공원 수: {}", parks.size());

        List<GetAllParksBasicResponse.ParkBasicInfo> parkBasicInfoList = parks.stream()
                .map(parkMapper::toParkBasicInfoDto)
                .collect(Collectors.toList());

        GetAllParksBasicResponse response = GetAllParksBasicResponse.builder()
                .parks(parkBasicInfoList)
                .build();

        log.info("전체 공원 기본 정보 조회 완료 - 조회된 공원 수: {}", parkBasicInfoList.size());

        return response;
    }

    /**
     * 검색어로 공원을 검색합니다.
     * 공원명에 검색어가 포함된 공원 중 첫 번째 공원의 areaCode와 공원명을 반환합니다.
     * 상세 페이지로 이동하기 위한 정보를 제공합니다.
     *
     * @param searchKeyword 검색어
     * @return 검색된 공원 정보 (areaCode, areaName만 포함)
     * @throws BusinessException 검색어가 비어있거나 공원을 찾을 수 없는 경우
     */
    @Override
    @Transactional(readOnly = true)
    public GetParkSearchResponse searchParkByAreaName(String searchKeyword) {
        log.info("공원 검색 시작 - searchKeyword: {}", searchKeyword);

        if (searchKeyword == null || searchKeyword.trim().isEmpty()) {
            log.warn("검색어가 제공되지 않았습니다.");
            throw new BusinessException(ErrorCode.PARK_SEARCH_KEYWORD_EMPTY);
        }

        // 공원명에 검색어가 포함된 공원들 조회 (부분 일치)
        List<Park> parks = parkRepository.findByAreaNameContaining(searchKeyword.trim());
        
        if (parks.isEmpty()) {
            log.warn("검색된 공원이 없습니다 - searchKeyword: {}", searchKeyword);
            throw new BusinessException(ErrorCode.PARK_SEARCH_NOT_FOUND, 
                    "검색어 '" + searchKeyword + "'에 해당하는 공원을 찾을 수 없습니다.");
        }

        // 첫 번째 공원만 반환
        Park firstPark = parks.get(0);
        log.info("공원 검색 완료 - searchKeyword: {}, areaCode: {}, areaName: {}", 
                searchKeyword, firstPark.getAreaCode(), firstPark.getAreaName());

        // areaCode와 areaName만 포함하는 DTO로 변환
        return parkMapper.toParkSearchDto(firstPark);
    }
}
