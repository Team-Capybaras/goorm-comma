package groom.backend.domain.population.repository;

import groom.backend.domain.population.entity.LivePopStatus;
import groom.backend.domain.population.entity.LivePopStatusId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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

    List<LivePopStatus> findByAreaCodeAndDataGetTimeBetween(
            String areaCode,
            LocalDateTime start,
            LocalDateTime end
    );

    /**
     * 여러 areaCode에 대해 각각의 가장 최근 인구 현황을 배치로 조회합니다.
     * @param areaCodes 지역 코드 리스트
     * @return areaCode별 최신 인구 현황 리스트
     */
    @Query("SELECT l FROM LivePopStatus l WHERE l.areaCode IN :areaCodes " +
           "AND (l.areaCode, l.dataGetTime) IN " +
           "(SELECT l2.areaCode, MAX(l2.dataGetTime) FROM LivePopStatus l2 WHERE l2.areaCode IN :areaCodes GROUP BY l2.areaCode)")
    List<LivePopStatus> findLatestByAreaCodes(@Param("areaCodes") List<String> areaCodes);
}

