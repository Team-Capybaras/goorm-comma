package groom.backend.domain.seoul.transit.repository;

import groom.backend.domain.seoul.transit.entity.SubwayStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubwayStationRepository extends JpaRepository<SubwayStation, Integer> {
    Optional<SubwayStation> findBySubId(Integer subId);
}

