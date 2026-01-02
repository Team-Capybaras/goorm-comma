package groom.backend.domain.seoul.weather.repository;

import groom.backend.domain.seoul.weather.entity.WeatherStatus;
import groom.backend.domain.seoul.weather.entity.WeatherStatusId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherStatusRepository extends JpaRepository<WeatherStatus, WeatherStatusId> {
}

