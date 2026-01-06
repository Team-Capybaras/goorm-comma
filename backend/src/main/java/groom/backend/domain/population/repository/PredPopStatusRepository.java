package groom.backend.domain.population.repository;

import groom.backend.domain.population.entity.PredPopStatus;
import groom.backend.domain.population.entity.PredPopStatusId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PredPopStatusRepository extends JpaRepository<PredPopStatus, PredPopStatusId> {
    /**
     * 지역 코드로 최신 인구 예보를 조회합니다.
     */
    @Query("SELECT p FROM PredPopStatus p WHERE p.areaCode = :areaCode ORDER BY p.dataGetTime DESC")
    List<PredPopStatus> findLatestByAreaCode(@Param("areaCode") String areaCode, Pageable pageable);

    /**
     * 지역 코드로 최신 인구 예보를 조회합니다 (편의 메서드).
     */
    default Optional<PredPopStatus> findLatestByAreaCode(String areaCode) {
        List<PredPopStatus> results = findLatestByAreaCode(areaCode, Pageable.ofSize(1));
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
}

