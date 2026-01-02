package groom.backend.domain.seoul.transit.repository;

import groom.backend.domain.seoul.transit.entity.SbikeStatus;
import groom.backend.domain.seoul.transit.entity.SbikeStatusId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SbikeStatusRepository extends JpaRepository<SbikeStatus, SbikeStatusId> {
}

