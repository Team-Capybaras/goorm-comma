package groom.backend.domain.parking.repository;

import groom.backend.domain.parking.entity.ChargerDetail;
import groom.backend.domain.parking.entity.ChargerDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChargerDetailRepository extends JpaRepository<ChargerDetail, ChargerDetailId> {
}

