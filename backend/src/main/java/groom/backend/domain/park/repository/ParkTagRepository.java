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

    /**
     * 태그명 리스트로 공원의 areaCode를 조회합니다.
     * 제공된 모든 태그명을 모두 가지고 있는 공원만 조회됩니다 (AND 조건).
     *
     * @param tagNames 태그명 리스트
     * @param tagCount 태그명 개수
     * @return areaCode 리스트
     */
    @Query("SELECT pt.areaCode FROM ParkTag pt JOIN pt.tag t WHERE t.tagName IN :tagNames GROUP BY pt.areaCode HAVING COUNT(DISTINCT t.tagName) = :tagCount ORDER BY pt.areaCode ASC")
    List<String> findAreaCodesByTagNames(@Param("tagNames") List<String> tagNames, @Param("tagCount") Long tagCount);

    /**
     * 여러 areaCode에 대해 각각의 태그 정보를 배치로 조회합니다.
     * @param areaCodes 지역 코드 리스트
     * @return areaCode별 태그 정보 리스트
     */
    @Query("SELECT pt FROM ParkTag pt JOIN FETCH pt.tag WHERE pt.areaCode IN :areaCodes")
    List<ParkTag> findByAreaCodesWithTag(@Param("areaCodes") List<String> areaCodes);
}
