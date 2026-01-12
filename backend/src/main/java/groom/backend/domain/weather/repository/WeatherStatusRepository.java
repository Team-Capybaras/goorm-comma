package groom.backend.domain.weather.repository;

import groom.backend.domain.weather.entity.WeatherStatus;
import groom.backend.domain.weather.entity.WeatherStatusId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeatherStatusRepository extends JpaRepository<WeatherStatus, WeatherStatusId> {

  /**
   * areacode에 대해, 가장 최근 날씨 데이터 반환
   * @param areaCode
   * @return
   */
  Optional<WeatherStatus> findTopByAreaCodeOrderByDataGetTimeDesc(String areaCode);
}

