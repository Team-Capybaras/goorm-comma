package groom.backend.domain.recommendation.repository;

import groom.backend.domain.recommendation.entity.ParkStatistics;
import groom.backend.domain.recommendation.entity.ParkStatisticsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkStatisticsRepository extends JpaRepository<ParkStatistics, ParkStatisticsId> {

}
