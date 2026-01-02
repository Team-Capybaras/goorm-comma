package groom.backend.domain.seoul.parking.repository;

import groom.backend.domain.seoul.parking.entity.ParkingLotStatus;
import groom.backend.domain.seoul.parking.entity.ParkingLotStatusId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingLotStatusRepository extends JpaRepository<ParkingLotStatus, ParkingLotStatusId> {
}

