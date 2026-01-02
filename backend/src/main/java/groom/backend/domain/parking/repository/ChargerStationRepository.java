package groom.backend.domain.parking.repository;

import groom.backend.domain.parking.entity.ChargerStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChargerStationRepository extends JpaRepository<ChargerStation, String> {
    Optional<ChargerStation> findByStationId(String stationId);
}

