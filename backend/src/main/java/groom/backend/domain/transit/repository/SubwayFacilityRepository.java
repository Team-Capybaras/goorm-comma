package groom.backend.domain.transit.repository;

import groom.backend.domain.transit.entity.SubwayFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubwayFacilityRepository extends JpaRepository<SubwayFacility, Integer> {
    /**
     * 지하철역 ID로 시설 목록을 조회합니다.
     */
    java.util.List<SubwayFacility> findBySubId(Integer subId);
}

