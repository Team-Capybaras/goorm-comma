package groom.backend.domain.transit.repository;

import groom.backend.domain.transit.entity.BusStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusStationRepository extends JpaRepository<BusStation, Integer> {
    Optional<BusStation> findByBusStnId(Integer busStnId);
}

