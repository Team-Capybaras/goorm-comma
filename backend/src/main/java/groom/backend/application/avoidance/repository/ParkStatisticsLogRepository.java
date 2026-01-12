package groom.backend.application.avoidance.repository;

import groom.backend.application.avoidance.entity.ParkStatisticsLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkStatisticsLogRepository extends JpaRepository<ParkStatisticsLog,Long> {
}
