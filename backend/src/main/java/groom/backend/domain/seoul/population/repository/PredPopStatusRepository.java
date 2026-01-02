package groom.backend.domain.seoul.population.repository;

import groom.backend.domain.seoul.population.entity.PredPopStatus;
import groom.backend.domain.seoul.population.entity.PredPopStatusId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PredPopStatusRepository extends JpaRepository<PredPopStatus, PredPopStatusId> {
}

