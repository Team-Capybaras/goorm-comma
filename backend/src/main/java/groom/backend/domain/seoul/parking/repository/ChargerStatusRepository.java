package groom.backend.domain.seoul.parking.repository;

import groom.backend.domain.seoul.parking.entity.ChargerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChargerStatusRepository extends JpaRepository<ChargerStatus, Integer> {
}

