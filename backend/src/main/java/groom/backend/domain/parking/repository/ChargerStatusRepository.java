package groom.backend.domain.parking.repository;

import groom.backend.domain.parking.entity.ChargerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChargerStatusRepository extends JpaRepository<ChargerStatus, Integer> {
  /**
   * native Query 기반 복수의 충전기에 대한 최신 현황 조회
   * DISTINCT ON는 내부적으로 정렬된 이후 첫번째 행을 반환. 따라서 가장 최신건만 조회됨.
   * @param stationIds
   * @param chargerIds
   * @return
   */
  @Query(value = """
    SELECT DISTINCT ON (cs.station_id, cs.charger_id)
           cs.*
    FROM charger_status cs
    JOIN unnest(:stationIds, :chargerIds)
         AS t(station_id, charger_id)
      ON cs.station_id = t.station_id
     AND cs.charger_id = t.charger_id
    ORDER BY cs.station_id, cs.charger_id, cs.data_get_time DESC
    """, nativeQuery = true)
  List<ChargerStatus> findLatestByChargerKeys(
          @Param("stationIds") List<String> stationIds,
          @Param("chargerIds") List<Integer> chargerIds
  );


}

