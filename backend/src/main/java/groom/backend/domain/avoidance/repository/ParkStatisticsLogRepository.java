package groom.backend.domain.avoidance.repository;

import groom.backend.domain.avoidance.entity.ParkStatisticsLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkStatisticsLogRepository extends JpaRepository<ParkStatisticsLog,Long> {
}
