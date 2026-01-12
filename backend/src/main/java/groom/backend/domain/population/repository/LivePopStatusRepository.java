package groom.backend.domain.population.repository;

import groom.backend.domain.population.entity.LivePopStatus;
import groom.backend.domain.population.entity.LivePopStatusId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivePopStatusRepository extends JpaRepository<LivePopStatus, LivePopStatusId> {
    /**
     * 지역 코드로 최신 실시간 인구 현황을 조회합니다.
     */
    @Query("SELECT l FROM LivePopStatus l WHERE l.areaCode = :areaCode ORDER BY l.dataGetTime DESC")
    List<LivePopStatus> findLatestByAreaCode(@Param("areaCode") String areaCode, Pageable pageable);

    /**
     * 지역 코드로 최신 실시간 인구 현황을 조회합니다 (편의 메서드).
     */
    default Optional<LivePopStatus> findLatestByAreaCode(String areaCode) {
        List<LivePopStatus> results = findLatestByAreaCode(areaCode, Pageable.ofSize(1));
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
}

