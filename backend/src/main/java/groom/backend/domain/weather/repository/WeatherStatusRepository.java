package groom.backend.domain.weather.repository;

import groom.backend.domain.weather.entity.WeatherStatus;
import groom.backend.domain.weather.entity.WeatherStatusId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeatherStatusRepository extends JpaRepository<WeatherStatus, WeatherStatusId> {

  /**
   * areacode에 대해, 가장 최근 날씨 데이터 반환
   * @param areaCode
   * @return
   */
  Optional<WeatherStatus> findTopByAreaCodeOrderByDataGetTimeDesc(String areaCode);

  /**
   * 여러 areaCode에 대해 각각의 가장 최근 날씨 데이터를 배치로 조회합니다.
   * @param areaCodes 지역 코드 리스트
   * @return areaCode별 최신 날씨 정보 리스트
   */
  @Query("SELECT w FROM WeatherStatus w WHERE w.areaCode IN :areaCodes " +
         "AND (w.areaCode, w.dataGetTime) IN " +
         "(SELECT w2.areaCode, MAX(w2.dataGetTime) FROM WeatherStatus w2 WHERE w2.areaCode IN :areaCodes GROUP BY w2.areaCode)")
  List<WeatherStatus> findLatestByAreaCodes(@Param("areaCodes") List<String> areaCodes);
}

