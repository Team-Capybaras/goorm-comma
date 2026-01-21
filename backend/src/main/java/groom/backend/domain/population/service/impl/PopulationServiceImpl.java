package groom.backend.domain.population.service.impl;

import groom.backend.common.exception.BusinessException;
import groom.backend.common.exception.ErrorCode;
import groom.backend.domain.park.entity.Park;
import groom.backend.domain.park.repository.ParkRepository;
import groom.backend.domain.population.dto.response.GetPopulationResponse;
import groom.backend.domain.population.entity.LivePopStatus;
import groom.backend.domain.population.repository.LivePopStatusRepository;
import groom.backend.domain.population.service.spec.PopulationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 인구 정보 조회 서비스 구현체
 * 실시간 인구 현황 및 예보 정보를 조회합니다.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PopulationServiceImpl implements PopulationService {
    private final LivePopStatusRepository livePopStatusRepository;
    private final ParkRepository parkRepository;

    /**
     * 지역 코드로 최신 인구 정보를 조회합니다.
     *
     * @param areaCode 지역 코드
     * @return 인구 정보 (실시간 현황 및 예보)
     * @throws BusinessException 지역 정보를 찾을 수 없을 때
     */
    @Override
    @Transactional(readOnly = true)
    public GetPopulationResponse getPopulationByAreaCode(String areaCode) {
        log.info("인구 정보 조회 시작 - AREA_CODE: {}", areaCode);

        // 입력값 검증
        if (areaCode == null || areaCode.trim().isEmpty()) {
            log.warn("지역 코드가 제공되지 않았습니다");
            throw new BusinessException(ErrorCode.MISSING_PARAMETER, "지역 코드(areaCode)는 필수입니다");
        }

        // 1. 지역 정보 조회
        Optional<Park> parkOptional = parkRepository.findByAreaCode(areaCode.trim());
        if (parkOptional.isEmpty()) {
            log.warn("지역 정보를 찾을 수 없습니다 - AREA_CODE: {}", areaCode);
            throw new BusinessException(ErrorCode.POPULATION_AREA_CODE_NOT_FOUND);
        }

        Park park = parkOptional.get();
        String areaName = park.getAreaName();

        // 2. 최신 실시간 인구 현황 조회
        Optional<LivePopStatus> livePopStatusOptional = livePopStatusRepository.findLatestByAreaCode(areaCode);
        GetPopulationResponse.LivePopulationInfo livePopulationInfo = null;
        if (livePopStatusOptional.isPresent()) {
            LivePopStatus livePopStatus = livePopStatusOptional.get();
            livePopulationInfo = GetPopulationResponse.LivePopulationInfo.builder()
                    .dataGetTime(livePopStatus.getDataGetTime())
                    .areaPopMin(livePopStatus.getAreaPopMin())
                    .areaPopMax(livePopStatus.getAreaPopMax())
                    .areaCongestLevel(livePopStatus.getAreaCongestLevel())
                    .areaCongestMsg(livePopStatus.getAreaCongestMsg())
                    .replaceYn(livePopStatus.getReplaceYn())
                    .popTime(livePopStatus.getPopTime())
                    .build();
            log.debug("실시간 인구 현황 조회 완료 - AREA_CODE: {}", areaCode);
        } else {
            log.debug("실시간 인구 현황 데이터 없음 - AREA_CODE: {}", areaCode);
        }

        GetPopulationResponse response = GetPopulationResponse.builder()
                .areaCode(areaCode)
                .areaName(areaName)
                .livePopulation(livePopulationInfo)
                .build();

        log.info("인구 정보 조회 완료 - AREA_CODE: {}", areaCode);
        return response;
    }
}

