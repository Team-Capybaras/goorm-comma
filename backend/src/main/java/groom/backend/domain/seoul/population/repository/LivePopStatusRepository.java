package groom.backend.domain.seoul.population.repository;

import groom.backend.domain.seoul.population.entity.LivePopStatus;
import groom.backend.domain.seoul.population.entity.LivePopStatusId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivePopStatusRepository extends JpaRepository<LivePopStatus, LivePopStatusId> {
}

