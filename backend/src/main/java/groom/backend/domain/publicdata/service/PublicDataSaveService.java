package groom.backend.domain.publicdata.service;

import groom.backend.domain.park.entity.Park;
import groom.backend.domain.park.repository.ParkRepository;
import groom.backend.domain.population.entity.LivePopStatus;
import groom.backend.domain.population.entity.LivePopStatusId;
import groom.backend.domain.population.entity.PredPopStatus;
import groom.backend.domain.population.entity.PredPopStatusId;
import groom.backend.domain.population.repository.LivePopStatusRepository;
import groom.backend.domain.population.repository.PredPopStatusRepository;
import groom.backend.domain.weather.entity.WeatherStatus;
import groom.backend.domain.weather.entity.WeatherStatusId;
import groom.backend.domain.weather.repository.WeatherStatusRepository;
import groom.backend.domain.parking.entity.*;
import groom.backend.domain.parking.repository.*;
import groom.backend.domain.transit.entity.*;
import groom.backend.domain.transit.repository.*;
import groom.backend.interfaces.seoul.dto.response.CityDataDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 공공 데이터 저장 서비스
 * 엔티티를 DB에 저장합니다.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PublicDataSaveService {
    private final PublicDataMapper publicDataMapper;
    private final ParkRepository parkRepository;
    private final LivePopStatusRepository livePopStatusRepository;
    private final PredPopStatusRepository predPopStatusRepository;
    private final WeatherStatusRepository weatherStatusRepository;
    private final ParkingLotRepository parkingLotRepository;
    private final ParkingLotStatusRepository parkingLotStatusRepository;
    private final SubwayStationRepository subwayStationRepository;
    private final SubwayFacilityRepository subwayFacilityRepository;
    private final BusStationRepository busStationRepository;
    private final SbikeRepository sbikeRepository;
    private final SbikeStatusRepository sbikeStatusRepository;
    private final ChargerStationRepository chargerStationRepository;
    private final ChargerDetailRepository chargerDetailRepository;
    private final ChargerStatusRepository chargerStatusRepository;

    /**
     * CityDataDto의 모든 데이터를 DB에 저장합니다.
     */
    @Transactional
    public PublicDataSaveResult saveAll(CityDataDto cityData) {
        LocalDateTime dataGetTime = LocalDateTime.now();
        String areaCode = cityData.getAreaCd();
        String areaName = cityData.getAreaNm();

        log.info("공공 데이터 저장 시작 - AREA_CODE: {}, AREA_NAME: {}", areaCode, areaName);

        PublicDataSaveResult result = new PublicDataSaveResult();

        // 1. Park 저장 (없으면 생성, 있으면 업데이트)
        savePark(areaCode, areaName);
        result.setParkSaved(true);

        // 2. LivePopStatus 저장 (없으면 생성, 있으면 업데이트)
        if (cityData.getLivePpltnStts() != null && cityData.getLivePpltnStts().getLivePpltnStts() != null) {
            LivePopStatus newLivePopStatus = publicDataMapper.toLivePopStatus(
                    areaCode, 
                    cityData.getLivePpltnStts().getLivePpltnStts(), 
                    dataGetTime
            );
            if (newLivePopStatus != null) {
                saveLivePopStatus(newLivePopStatus);
                result.setLivePopStatusSaved(true);
                log.debug("LivePopStatus 저장 완료 - AREA_CODE: {}", areaCode);
            }
        }

        // 3. PredPopStatus 저장 (없으면 생성, 있으면 업데이트)
        if (cityData.getLivePpltnStts() != null && 
            cityData.getLivePpltnStts().getLivePpltnStts() != null &&
            cityData.getLivePpltnStts().getLivePpltnStts().getFcstPpltn() != null) {
            List<PredPopStatus> predPopStatusList = publicDataMapper.toPredPopStatusList(
                    areaCode,
                    cityData.getLivePpltnStts().getLivePpltnStts().getFcstPpltn(),
                    dataGetTime
            );
            if (!predPopStatusList.isEmpty()) {
                savePredPopStatusList(predPopStatusList);
                result.setPredPopStatusSaved(true);
                result.setPredPopStatusCount(predPopStatusList.size());
                log.debug("PredPopStatus 저장 완료 - AREA_CODE: {}, COUNT: {}", areaCode, predPopStatusList.size());
            }
        }

        // 4. WeatherStatus 저장 (없으면 생성, 있으면 업데이트)
        if (cityData.getWeatherStts() != null && cityData.getWeatherStts().getWeatherStts() != null) {
            WeatherStatus newWeatherStatus = publicDataMapper.toWeatherStatus(
                    areaCode,
                    cityData.getWeatherStts().getWeatherStts(),
                    dataGetTime
            );
            if (newWeatherStatus != null) {
                saveWeatherStatus(newWeatherStatus);
                result.setWeatherStatusSaved(true);
                log.debug("WeatherStatus 저장 완료 - AREA_CODE: {}", areaCode);
            }
        }

        // 5. 주차장 정보 및 현황 저장
        if (cityData.getPrkStts() != null && cityData.getPrkStts().getPrkStts() != null) {
            int savedCount = saveParkingLots(areaCode, cityData.getPrkStts().getPrkStts(), dataGetTime);
            if (savedCount > 0) {
                result.setParkingLotSaved(true);
                result.setParkingLotCount(savedCount);
                log.debug("ParkingLot 저장 완료 - AREA_CODE: {}, COUNT: {}", areaCode, savedCount);
            }
        }

        // 6. 지하철역 정보 및 시설 저장
        if (cityData.getSubStts() != null && cityData.getSubStts().getSubStts() != null) {
            saveSubwayStations(areaCode, cityData.getSubStts().getSubStts());
            result.setSubwayStationSaved(true);
            log.debug("SubwayStation 저장 완료 - AREA_CODE: {}", areaCode);
        }

        // 7. 버스 정류장 저장
        if (cityData.getBusStnStts() != null && cityData.getBusStnStts().getBusStnStts() != null) {
            int savedCount = saveBusStations(areaCode, cityData.getBusStnStts().getBusStnStts());
            if (savedCount > 0) {
                result.setBusStationSaved(true);
                result.setBusStationCount(savedCount);
                log.debug("BusStation 저장 완료 - AREA_CODE: {}, COUNT: {}", areaCode, savedCount);
            }
        }

        // 8. 공유 자전거 정보 및 현황 저장
        if (cityData.getSbikeStts() != null && cityData.getSbikeStts().getSbikeStts() != null) {
            saveSbikes(areaCode, cityData.getSbikeStts().getSbikeStts(), dataGetTime);
            result.setSbikeSaved(true);
            log.debug("Sbike 저장 완료 - AREA_CODE: {}", areaCode);
        }

        // 9. 충전소 정보 및 상태 저장
        if (cityData.getChargerStts() != null && cityData.getChargerStts().getChargerStts() != null) {
            saveChargerStations(areaCode, cityData.getChargerStts().getChargerStts(), dataGetTime);
            result.setChargerStationSaved(true);
            log.debug("ChargerStation 저장 완료 - AREA_CODE: {}", areaCode);
        }

        log.info("공공 데이터 저장 완료 - AREA_CODE: {}, RESULT: {}", areaCode, result);
        return result;
    }

    /**
     * Park 저장 (없으면 생성, 있으면 업데이트)
     */
    private Park savePark(String areaCode, String areaName) {
        Park park = parkRepository.findByAreaCode(areaCode)
                .orElse(publicDataMapper.toPark(areaCode, areaName));
        
        // 이름이 변경되었을 수 있으므로 업데이트
        if (areaName != null && !areaName.equals(park.getAreaName())) {
            park.setAreaName(areaName);
        }
        
        return parkRepository.save(park);
    }

    /**
     * LivePopStatus 저장 (없으면 생성, 있으면 업데이트)
     */
    private void saveLivePopStatus(LivePopStatus newStatus) {
        LivePopStatusId id = new LivePopStatusId(newStatus.getDataGetTime(), newStatus.getAreaCode());
        Optional<LivePopStatus> existing = livePopStatusRepository.findById(id);
        
        if (existing.isPresent()) {
            // 기존 데이터 업데이트
            LivePopStatus existingStatus = existing.get();
            existingStatus.setAreaPopMin(newStatus.getAreaPopMin());
            existingStatus.setAreaPopMax(newStatus.getAreaPopMax());
            existingStatus.setAreaCongestLevel(newStatus.getAreaCongestLevel());
            existingStatus.setAreaCongestMsg(newStatus.getAreaCongestMsg());
            existingStatus.setReplaceYn(newStatus.getReplaceYn());
            existingStatus.setPopTime(newStatus.getPopTime());
            livePopStatusRepository.save(existingStatus);
            log.debug("LivePopStatus 업데이트 - AREA_CODE: {}, DATA_GET_TIME: {}", 
                    newStatus.getAreaCode(), newStatus.getDataGetTime());
        } else {
            // 새 데이터 저장
            livePopStatusRepository.save(newStatus);
            log.debug("LivePopStatus 생성 - AREA_CODE: {}, DATA_GET_TIME: {}", 
                    newStatus.getAreaCode(), newStatus.getDataGetTime());
        }
    }

    /**
     * PredPopStatus 리스트 저장 (없으면 생성, 있으면 업데이트)
     * 주의: 현재 엔티티 구조상 같은 dataGetTime과 areaCode 조합은 하나만 저장됩니다.
     * 여러 예보가 있는 경우 마지막 것만 저장됩니다.
     */
    private void savePredPopStatusList(List<PredPopStatus> newStatusList) {
        if (newStatusList.isEmpty()) {
            return;
        }
        
        // 같은 dataGetTime과 areaCode 조합이므로 마지막 예보만 저장
        // (엔티티 구조상 복합키가 dataGetTime과 areaCode만 사용)
        PredPopStatus lastStatus = newStatusList.get(newStatusList.size() - 1);
        PredPopStatusId id = new PredPopStatusId(lastStatus.getDataGetTime(), lastStatus.getAreaCode());
        Optional<PredPopStatus> existing = predPopStatusRepository.findById(id);
        
        if (existing.isPresent()) {
            // 기존 데이터 업데이트 (마지막 예보로)
            PredPopStatus existingStatus = existing.get();
            existingStatus.setForecastTime(lastStatus.getForecastTime());
            existingStatus.setForecastCongestLevel(lastStatus.getForecastCongestLevel());
            existingStatus.setForecastPopMin(lastStatus.getForecastPopMin());
            existingStatus.setForecastPopMax(lastStatus.getForecastPopMax());
            predPopStatusRepository.save(existingStatus);
        } else {
            // 새 데이터 저장 (마지막 예보만)
            predPopStatusRepository.save(lastStatus);
        }
        
        log.warn("PredPopStatus: 엔티티 구조상 같은 dataGetTime과 areaCode 조합은 하나만 저장됩니다. " +
                "총 {}개의 예보 중 마지막 것만 저장되었습니다.", newStatusList.size());
    }

    /**
     * WeatherStatus 저장 (없으면 생성, 있으면 업데이트)
     */
    private void saveWeatherStatus(WeatherStatus newStatus) {
        WeatherStatusId id = new WeatherStatusId(newStatus.getDataGetTime(), newStatus.getAreaCode());
        Optional<WeatherStatus> existing = weatherStatusRepository.findById(id);
        
        if (existing.isPresent()) {
            // 기존 데이터 업데이트
            WeatherStatus existingStatus = existing.get();
            existingStatus.setWeatherTime(newStatus.getWeatherTime());
            existingStatus.setTemp(newStatus.getTemp());
            existingStatus.setSensibleTemp(newStatus.getSensibleTemp());
            existingStatus.setHumidity(newStatus.getHumidity());
            existingStatus.setWindDirct(newStatus.getWindDirct());
            existingStatus.setWindSpd(newStatus.getWindSpd());
            existingStatus.setPrecipitation(newStatus.getPrecipitation());
            existingStatus.setPrecptType(newStatus.getPrecptType());
            existingStatus.setPrecptMsg(newStatus.getPrecptMsg());
            existingStatus.setUvIndexLevel(newStatus.getUvIndexLevel());
            existingStatus.setUvIndex(newStatus.getUvIndex());
            existingStatus.setPm25Index(newStatus.getPm25Index());
            existingStatus.setPm25(newStatus.getPm25());
            existingStatus.setPm10Index(newStatus.getPm10Index());
            existingStatus.setPm10(newStatus.getPm10());
            existingStatus.setAirIndex(newStatus.getAirIndex());
            existingStatus.setAirIndexLevel(newStatus.getAirIndexLevel());
            existingStatus.setAirIndexMain(newStatus.getAirIndexMain());
            existingStatus.setAirMsg(newStatus.getAirMsg());
            existingStatus.setDataSource(newStatus.getDataSource());
            weatherStatusRepository.save(existingStatus);
            log.debug("WeatherStatus 업데이트 - AREA_CODE: {}, DATA_GET_TIME: {}", 
                    newStatus.getAreaCode(), newStatus.getDataGetTime());
        } else {
            // 새 데이터 저장
            weatherStatusRepository.save(newStatus);
            log.debug("WeatherStatus 생성 - AREA_CODE: {}, DATA_GET_TIME: {}", 
                    newStatus.getAreaCode(), newStatus.getDataGetTime());
        }
    }

    /**
     * 주차장 정보 및 현황 저장
     */
    private int saveParkingLots(String areaCode, List<groom.backend.interfaces.seoul.dto.response.ParkingStatusItemDto> items, LocalDateTime dataGetTime) {
        int savedCount = 0;
        
        for (groom.backend.interfaces.seoul.dto.response.ParkingStatusItemDto item : items) {
            ParkingLot parkingLot = publicDataMapper.toParkingLot(areaCode, item);
            if (parkingLot == null) {
                continue;
            }

            // 주차장 정보 저장 (없으면 생성, 있으면 업데이트)
            Optional<ParkingLot> existing = parkingLotRepository.findByPrkCode(parkingLot.getPrkCode());
            if (existing.isPresent()) {
                ParkingLot existingLot = existing.get();
                existingLot.setPrkName(parkingLot.getPrkName());
                existingLot.setPrkType(parkingLot.getPrkType());
                existingLot.setCapacity(parkingLot.getCapacity());
                existingLot.setCurrentInfoYn(parkingLot.getCurrentInfoYn());
                existingLot.setPayYn(parkingLot.getPayYn());
                existingLot.setRates(parkingLot.getRates());
                existingLot.setTimeRates(parkingLot.getTimeRates());
                existingLot.setAddRates(parkingLot.getAddRates());
                existingLot.setAddTimeRates(parkingLot.getAddTimeRates());
                existingLot.setAddr(parkingLot.getAddr());
                existingLot.setRoadAddr(parkingLot.getRoadAddr());
                existingLot.setPrkX(parkingLot.getPrkX());
                existingLot.setPrkY(parkingLot.getPrkY());
                parkingLotRepository.save(existingLot);
            } else {
                parkingLotRepository.save(parkingLot);
            }

            // 주차장 현황 저장 (curPrkYn이 Y인 경우만)
            ParkingLotStatus status = publicDataMapper.toParkingLotStatus(parkingLot.getPrkCode(), item, dataGetTime);
            if (status != null) {
                saveParkingLotStatus(status);
            }

            savedCount++;
        }

        return savedCount;
    }

    /**
     * ParkingLotStatus 저장 (없으면 생성, 있으면 업데이트)
     */
    private void saveParkingLotStatus(ParkingLotStatus newStatus) {
        ParkingLotStatusId id = new ParkingLotStatusId(newStatus.getDataGetTime(), newStatus.getPrkCode());
        Optional<ParkingLotStatus> existing = parkingLotStatusRepository.findById(id);
        
        if (existing.isPresent()) {
            ParkingLotStatus existingStatus = existing.get();
            existingStatus.setCurrentPrkCnt(newStatus.getCurrentPrkCnt());
            existingStatus.setCurrentPrkTime(newStatus.getCurrentPrkTime());
            parkingLotStatusRepository.save(existingStatus);
        } else {
            parkingLotStatusRepository.save(newStatus);
        }
    }

    /**
     * 지하철역 정보 및 시설 저장
     */
    private void saveSubwayStations(String areaCode, groom.backend.interfaces.seoul.dto.response.SubwayStatusDetailDto detail) {
        SubwayStation subwayStation = publicDataMapper.toSubwayStation(areaCode, detail);
        if (subwayStation == null) {
            return;
        }

        // 지하철역 정보 저장 (없으면 생성, 있으면 업데이트)
        Optional<SubwayStation> existing = subwayStationRepository.findBySubId(subwayStation.getSubId());
        if (existing.isPresent()) {
            SubwayStation existingStation = existing.get();
            existingStation.setSubStnName(subwayStation.getSubStnName());
            existingStation.setSubStnLine(subwayStation.getSubStnLine());
            existingStation.setAddr(subwayStation.getAddr());
            existingStation.setRoadAddr(subwayStation.getRoadAddr());
            existingStation.setSubStnX(subwayStation.getSubStnX());
            existingStation.setSubStnY(subwayStation.getSubStnY());
            subwayStationRepository.save(existingStation);
        } else {
            subwayStationRepository.save(subwayStation);
        }

        // 지하철 시설 정보 저장
        if (detail.getSubFaciinfo() != null) {
            List<SubwayFacility> facilities = publicDataMapper.toSubwayFacilityList(subwayStation.getSubId(), detail.getSubFaciinfo());
            for (SubwayFacility facility : facilities) {
                Optional<SubwayFacility> existingFacility = subwayFacilityRepository.findById(facility.getSubFacilityInfo());
                if (existingFacility.isPresent()) {
                    SubwayFacility existingFac = existingFacility.get();
                    existingFac.setElvtrName(facility.getElvtrName());
                    existingFac.setOperateSector(facility.getOperateSector());
                    existingFac.setInstallPosition(facility.getInstallPosition());
                    existingFac.setUseYn(facility.getUseYn());
                    existingFac.setElvtrSection(facility.getElvtrSection());
                    subwayFacilityRepository.save(existingFac);
                } else {
                    subwayFacilityRepository.save(facility);
                }
            }
        }
    }

    /**
     * 버스 정류장 저장
     */
    private int saveBusStations(String areaCode, List<groom.backend.interfaces.seoul.dto.response.BusStationStatusItemDto> items) {
        int savedCount = 0;
        
        for (groom.backend.interfaces.seoul.dto.response.BusStationStatusItemDto item : items) {
            BusStation busStation = publicDataMapper.toBusStation(areaCode, item);
            if (busStation == null) {
                continue;
            }

            Optional<BusStation> existing = busStationRepository.findByBusStnId(busStation.getBusStnId());
            if (existing.isPresent()) {
                BusStation existingStation = existing.get();
                existingStation.setBusArsId(busStation.getBusArsId());
                existingStation.setBusStnName(busStation.getBusStnName());
                existingStation.setBusStnX(busStation.getBusStnX());
                existingStation.setBusStnY(busStation.getBusStnY());
                busStationRepository.save(existingStation);
            } else {
                busStationRepository.save(busStation);
            }
            savedCount++;
        }

        return savedCount;
    }

    /**
     * 공유 자전거 정보 및 현황 저장
     */
    private void saveSbikes(String areaCode, groom.backend.interfaces.seoul.dto.response.SharedBikeStatusDetailDto detail, LocalDateTime dataGetTime) {
        Sbike sbike = publicDataMapper.toSbike(areaCode, detail);
        if (sbike == null) {
            return;
        }

        // 공유 자전거 정보 저장 (없으면 생성, 있으면 업데이트)
        Optional<Sbike> existing = sbikeRepository.findBySbikeSpotId(sbike.getSbikeSpotId());
        if (existing.isPresent()) {
            Sbike existingSbike = existing.get();
            existingSbike.setSbikeSpotName(sbike.getSbikeSpotName());
            existingSbike.setSbikeCapacity(sbike.getSbikeCapacity());
            existingSbike.setSbikeX(sbike.getSbikeX());
            existingSbike.setSbikeY(sbike.getSbikeY());
            sbikeRepository.save(existingSbike);
        } else {
            sbikeRepository.save(sbike);
        }

        // 공유 자전거 현황 저장
        SbikeStatus status = publicDataMapper.toSbikeStatus(sbike.getSbikeSpotId(), detail, dataGetTime);
        if (status != null) {
            saveSbikeStatus(status);
        }
    }

    /**
     * SbikeStatus 저장 (없으면 생성, 있으면 업데이트)
     */
    private void saveSbikeStatus(SbikeStatus newStatus) {
        SbikeStatusId id = new SbikeStatusId(newStatus.getDataGetTime(), newStatus.getSbikeSpotId());
        Optional<SbikeStatus> existing = sbikeStatusRepository.findById(id);
        
        if (existing.isPresent()) {
            SbikeStatus existingStatus = existing.get();
            existingStatus.setSbikeParkingPer(newStatus.getSbikeParkingPer());
            existingStatus.setSbikeParkingCnt(newStatus.getSbikeParkingCnt());
            sbikeStatusRepository.save(existingStatus);
        } else {
            sbikeStatusRepository.save(newStatus);
        }
    }

    /**
     * 충전소 정보 및 상태 저장
     */
    private void saveChargerStations(String areaCode, groom.backend.interfaces.seoul.dto.response.ChargerStatusDetailDto detail, LocalDateTime dataGetTime) {
        ChargerStation chargerStation = publicDataMapper.toChargerStation(areaCode, detail);
        if (chargerStation == null) {
            return;
        }

        // 충전소 정보 저장 (없으면 생성, 있으면 업데이트)
        Optional<ChargerStation> existing = chargerStationRepository.findByStationId(chargerStation.getStationId());
        if (existing.isPresent()) {
            ChargerStation existingStation = existing.get();
            existingStation.setStationName(chargerStation.getStationName());
            existingStation.setStationAddr(chargerStation.getStationAddr());
            existingStation.setStationX(chargerStation.getStationX());
            existingStation.setStationY(chargerStation.getStationY());
            existingStation.setStationUsetime(chargerStation.getStationUsetime());
            existingStation.setStationParkpay(chargerStation.getStationParkpay());
            existingStation.setStationLimitDetail(chargerStation.getStationLimitDetail());
            existingStation.setStationKindDetail(chargerStation.getStationKindDetail());
            chargerStationRepository.save(existingStation);
        } else {
            chargerStationRepository.save(chargerStation);
        }

        // 충전기 상세 정보 및 상태 저장
        if (detail.getChargerDetails() != null && detail.getChargerDetails().getChargerDetails() != null) {
            groom.backend.interfaces.seoul.dto.response.ChargerDetailItemDto chargerDetailItem = detail.getChargerDetails().getChargerDetails();
            
            ChargerDetail chargerDetail = publicDataMapper.toChargerDetail(chargerStation.getStationId(), chargerDetailItem);
            if (chargerDetail != null) {
                saveChargerDetail(chargerDetail);
                
                // 충전기 상태 저장
                ChargerStatus chargerStatus = publicDataMapper.toChargerStatus(
                        chargerDetail.getChargerId(),
                        chargerStation.getStationId(),
                        chargerDetailItem,
                        dataGetTime
                );
                if (chargerStatus != null) {
                    saveChargerStatus(chargerStatus);
                }
            }
        }
    }

    /**
     * ChargerDetail 저장 (없으면 생성, 있으면 업데이트)
     */
    private void saveChargerDetail(ChargerDetail newDetail) {
        ChargerDetailId id = new ChargerDetailId(newDetail.getChargerId(), newDetail.getStationId());
        Optional<ChargerDetail> existing = chargerDetailRepository.findById(id);
        
        if (existing.isPresent()) {
            ChargerDetail existingDetail = existing.get();
            existingDetail.setChargerType(newDetail.getChargerType());
            existingDetail.setChargerUpdated(newDetail.getChargerUpdated());
            existingDetail.setChargerTimestamp(newDetail.getChargerTimestamp());
            existingDetail.setOutput(newDetail.getOutput());
            existingDetail.setMethod(newDetail.getMethod());
            chargerDetailRepository.save(existingDetail);
        } else {
            chargerDetailRepository.save(newDetail);
        }
    }

    /**
     * ChargerStatus 저장 (없으면 생성, 있으면 업데이트)
     */
    private void saveChargerStatus(ChargerStatus newStatus) {
        Optional<ChargerStatus> existing = chargerStatusRepository.findById(newStatus.getChargerStatKey());
        
        if (existing.isPresent()) {
            ChargerStatus existingStatus = existing.get();
            existingStatus.setChargerStatus(newStatus.getChargerStatus());
            existingStatus.setStatusUpdated(newStatus.getStatusUpdated());
            existingStatus.setStatusTimestamp(newStatus.getStatusTimestamp());
            existingStatus.setDataGetTime(newStatus.getDataGetTime());
            chargerStatusRepository.save(existingStatus);
        } else {
            chargerStatusRepository.save(newStatus);
        }
    }
}

