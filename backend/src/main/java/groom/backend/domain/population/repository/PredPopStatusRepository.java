package groom.backend.domain.population.repository;

import groom.backend.domain.population.entity.PredPopStatus;
import groom.backend.domain.population.entity.PredPopStatusId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PredPopStatusRepository extends JpaRepository<PredPopStatus, PredPopStatusId> {
}

