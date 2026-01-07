package groom.backend.domain.transit.repository;

import groom.backend.domain.transit.entity.SbikeStatus;
import groom.backend.domain.transit.entity.SbikeStatusId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SbikeStatusRepository extends JpaRepository<SbikeStatus, SbikeStatusId> {
    /**
     * 공유 자전거 스팟 ID로 최신 현황을 조회합니다.
     */
    @Query("SELECT s FROM SbikeStatus s WHERE s.sbikeSpotId = :sbikeSpotId ORDER BY s.dataGetTime DESC")
    List<SbikeStatus> findLatestBySbikeSpotId(@Param("sbikeSpotId") String sbikeSpotId, Pageable pageable);
    
    /**
     * 공유 자전거 스팟 ID로 최신 현황을 조회합니다 (편의 메서드).
     */
    default Optional<SbikeStatus> findLatestBySbikeSpotId(String sbikeSpotId) {
        List<SbikeStatus> results = findLatestBySbikeSpotId(sbikeSpotId, Pageable.ofSize(1));
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
}

