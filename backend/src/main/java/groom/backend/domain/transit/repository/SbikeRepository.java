package groom.backend.domain.transit.repository;

import groom.backend.domain.transit.entity.Sbike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SbikeRepository extends JpaRepository<Sbike, String> {
    Optional<Sbike> findBySbikeSpotId(String sbikeSpotId);
}

