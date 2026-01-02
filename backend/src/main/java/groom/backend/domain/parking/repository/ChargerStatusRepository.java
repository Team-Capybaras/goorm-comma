package groom.backend.domain.parking.repository;

import groom.backend.domain.parking.entity.ChargerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChargerStatusRepository extends JpaRepository<ChargerStatus, Integer> {
}

