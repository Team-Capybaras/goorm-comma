package groom.backend.domain.seoul.parking.repository;

import groom.backend.domain.seoul.parking.entity.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {
    Optional<ParkingLot> findByPrkCode(Long prkCode);
}

