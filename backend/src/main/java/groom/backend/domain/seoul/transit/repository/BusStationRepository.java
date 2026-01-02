package groom.backend.domain.seoul.transit.repository;

import groom.backend.domain.seoul.transit.entity.BusStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusStationRepository extends JpaRepository<BusStation, Integer> {
    Optional<BusStation> findByBusStnId(Integer busStnId);
}

