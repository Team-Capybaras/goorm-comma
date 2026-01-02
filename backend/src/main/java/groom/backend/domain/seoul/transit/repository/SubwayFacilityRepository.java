package groom.backend.domain.seoul.transit.repository;

import groom.backend.domain.seoul.transit.entity.SubwayFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubwayFacilityRepository extends JpaRepository<SubwayFacility, Integer> {
}

