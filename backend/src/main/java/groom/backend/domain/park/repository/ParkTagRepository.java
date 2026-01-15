package groom.backend.domain.park.repository;

import groom.backend.domain.park.entity.ParkTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkTagRepository extends JpaRepository<ParkTag, String> {
    /**
     * 지역 코드로 공원 태그 목록을 조회합니다.
     *
     * @param areaCode 지역 코드
     * @return 공원 태그 목록
     */
    List<ParkTag> findByAreaCode(String areaCode);

    /**
     * 지역 코드로 공원 태그 목록을 조회합니다 (태그 정보 포함).
     * JOIN FETCH를 사용하여 N+1 문제를 방지합니다.
     *
     * @param areaCode 지역 코드
     * @return 공원 태그 목록 (태그 정보 포함)
     */
    @Query("SELECT pt FROM ParkTag pt JOIN FETCH pt.tag WHERE pt.areaCode = :areaCode")
    List<ParkTag> findByAreaCodeWithTag(@Param("areaCode") String areaCode);

    /**
     * 지역 코드로 공원 태그를 모두 삭제합니다.
     *
     * @param areaCode 지역 코드
     */
    void deleteByAreaCode(String areaCode);
}
