package groom.backend.domain.transit.repository;

import groom.backend.domain.transit.entity.BusStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusStationRepository extends JpaRepository<BusStation, Integer> {
    Optional<BusStation> findByBusStnId(Integer busStnId);
    
    /**
     * 지역 코드로 버스 정류장 목록을 조회합니다.
     */
    java.util.List<BusStation> findByAreaCode(String areaCode);
}

