package groom.backend.domain.seoul.park.repository;

import groom.backend.domain.seoul.park.entity.Park;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParkRepository extends JpaRepository<Park, String> {
    Optional<Park> findByAreaCode(String areaCode);
}

