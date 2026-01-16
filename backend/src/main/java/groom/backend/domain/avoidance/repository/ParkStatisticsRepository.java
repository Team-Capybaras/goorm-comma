package groom.backend.domain.avoidance.repository;

import groom.backend.domain.avoidance.entity.ParkStatistics;
import groom.backend.domain.avoidance.entity.ParkStatisticsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkStatisticsRepository extends JpaRepository<ParkStatistics, ParkStatisticsId> {
  List<ParkStatistics> findByAreaCode(String areaCode);
}
