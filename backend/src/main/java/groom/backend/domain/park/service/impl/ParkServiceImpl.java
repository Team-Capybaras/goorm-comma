package groom.backend.domain.park.service.impl;

import groom.backend.domain.park.dto.response.GetAllParksResponse;
import groom.backend.domain.park.entity.Park;
import groom.backend.domain.park.repository.ParkRepository;
import groom.backend.domain.park.service.spec.ParkService;
import groom.backend.domain.weather.entity.WeatherStatus;
import groom.backend.domain.weather.repository.WeatherStatusRepository;
import groom.backend.domain.population.entity.LivePopStatus;
import groom.backend.domain.population.repository.LivePopStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 공원 정보 조회 서비스 구현체
 * 전체 공원 리스트를 조회합니다.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ParkServiceImpl implements ParkService {
    private final ParkRepository parkRepository;
    private final WeatherStatusRepository weatherStatusRepository;
    private final LivePopStatusRepository livePopStatusRepository;

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
                .map(park -> {
                    // 날씨 정보 조회
                    Optional<WeatherStatus> weatherStatusOptional = 
                            weatherStatusRepository.findTopByAreaCodeOrderByDataGetTimeDesc(park.getAreaCode());
                    
                    // 인구 정보 조회
                    Optional<LivePopStatus> livePopStatusOptional = 
                            livePopStatusRepository.findLatestByAreaCode(park.getAreaCode());

                    GetAllParksResponse.ParkInfo.ParkInfoBuilder builder = GetAllParksResponse.ParkInfo.builder()
                            .areaCode(park.getAreaCode())
                            .areaName(park.getAreaName())
                            .longitude(park.getLongitude())
                            .latitude(park.getLatitude());

                    // 날씨 정보 설정
                    if (weatherStatusOptional.isPresent()) {
                        WeatherStatus weatherStatus = weatherStatusOptional.get();
                        builder.temp(weatherStatus.getTemp())
                                .precptMsg(weatherStatus.getPrecptMsg())
                                .airIndex(weatherStatus.getAirIndex());
                    }

                    // 인구 혼잡도 정보 설정
                    if (livePopStatusOptional.isPresent()) {
                        LivePopStatus livePopStatus = livePopStatusOptional.get();
                        builder.areaCongestLevel(livePopStatus.getAreaCongestLevel());
                    }

                    return builder.build();
                })
                .collect(Collectors.toList());

        GetAllParksResponse response = GetAllParksResponse.builder()
                .parks(parkInfoList)
                .totalCount(parkInfoList.size())
                .build();

        log.info("전체 공원 리스트 조회 완료 - 총 공원 수: {}", parkInfoList.size());

        return response;
    }
}

