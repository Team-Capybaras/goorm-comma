package groom.backend.domain.parking.repository;

import groom.backend.domain.parking.entity.ParkingLotStatus;
import groom.backend.domain.parking.entity.ParkingLotStatusId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingLotStatusRepository extends JpaRepository<ParkingLotStatus, ParkingLotStatusId> {

  /**
   * 단일 주차장 최신 현황 조회
   * @param prkCode
   * @return
   */
  ParkingLotStatus findTopByPrkCodeOrderByDataGetTimeDesc(Long prkCode);

  /**
   * native Query 기반 복수의 주차장에 대한 최신 현황 조회
   * @param prkCodes
   * @return
   */
  @Query(value = """
      SELECT pls.*
      FROM parking_lot_status pls
      JOIN (
          SELECT prk_code, MAX(data_get_time) AS max_time
          FROM parking_lot_status
          WHERE prk_code IN (:prkCodes)
          GROUP BY prk_code
      ) latest
        ON pls.prk_code = latest.prk_code
       AND pls.data_get_time = latest.max_time
      """, nativeQuery = true)
  List<ParkingLotStatus> findLatestStatusesByPrkCodes(@Param("prkCodes") List<Long> prkCodes);
}

