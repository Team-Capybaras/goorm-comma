package groom.backend.domain.park.service.impl;

import groom.backend.domain.park.dto.response.GetAllParksBasicResponse;
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
}
