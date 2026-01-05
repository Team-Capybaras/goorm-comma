package groom.backend.domain.transit.repository;

import groom.backend.domain.transit.entity.SubwayFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubwayFacilityRepository extends JpaRepository<SubwayFacility, Integer> {
}

