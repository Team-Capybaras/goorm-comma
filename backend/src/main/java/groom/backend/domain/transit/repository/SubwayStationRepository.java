package groom.backend.domain.transit.repository;

import groom.backend.domain.transit.entity.SubwayStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubwayStationRepository extends JpaRepository<SubwayStation, Integer> {
    Optional<SubwayStation> findBySubId(Integer subId);
}

