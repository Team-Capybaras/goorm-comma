package groom.backend.domain.parking.repository;

import groom.backend.domain.parking.entity.ParkingLotStatus;
import groom.backend.domain.parking.entity.ParkingLotStatusId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingLotStatusRepository extends JpaRepository<ParkingLotStatus, ParkingLotStatusId> {
}

