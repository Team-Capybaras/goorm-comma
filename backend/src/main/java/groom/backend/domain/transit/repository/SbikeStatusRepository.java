package groom.backend.domain.transit.repository;

import groom.backend.domain.transit.entity.SbikeStatus;
import groom.backend.domain.transit.entity.SbikeStatusId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SbikeStatusRepository extends JpaRepository<SbikeStatus, SbikeStatusId> {
}

