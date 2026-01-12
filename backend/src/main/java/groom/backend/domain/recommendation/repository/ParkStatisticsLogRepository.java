package groom.backend.domain.recommendation.repository;

import groom.backend.domain.recommendation.entity.ParkStatisticsLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkStatisticsLogRepository extends JpaRepository<ParkStatisticsLog,Long> {
}
