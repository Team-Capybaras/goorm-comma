package groom.backend.domain.population.repository;

import groom.backend.domain.population.entity.LivePopStatus;
import groom.backend.domain.population.entity.LivePopStatusId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivePopStatusRepository extends JpaRepository<LivePopStatus, LivePopStatusId> {
}

