package groom.backend.domain.population.repository;

import groom.backend.domain.population.entity.PredPopStatus;
import groom.backend.domain.population.entity.PredPopStatusId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PredPopStatusRepository extends JpaRepository<PredPopStatus, PredPopStatusId> {
    /**
     * 지역 코드로 최신 dataGetTime의 모든 인구 예보를 조회합니다.
     * 같은 dataGetTime에 대한 모든 예보 시간대를 반환합니다.
     */
    @Query("SELECT p FROM PredPopStatus p WHERE p.areaCode = :areaCode " +
           "AND p.dataGetTime = (SELECT MAX(p2.dataGetTime) FROM PredPopStatus p2 WHERE p2.areaCode = :areaCode) " +
           "ORDER BY p.forecastTime ASC")
    List<PredPopStatus> findAllLatestByAreaCode(@Param("areaCode") String areaCode);
}

