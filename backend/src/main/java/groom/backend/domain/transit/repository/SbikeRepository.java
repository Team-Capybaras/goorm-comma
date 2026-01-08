package groom.backend.domain.transit.repository;

import groom.backend.domain.transit.entity.Sbike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SbikeRepository extends JpaRepository<Sbike, String> {
    Optional<Sbike> findBySbikeSpotId(String sbikeSpotId);
    
    /**
     * 지역 코드로 공유 자전거 목록을 조회합니다.
     */
    java.util.List<Sbike> findByAreaCode(String areaCode);
}

