package groom.backend.domain.seoul.parking.repository;

import groom.backend.domain.seoul.parking.entity.ChargerDetail;
import groom.backend.domain.seoul.parking.entity.ChargerDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChargerDetailRepository extends JpaRepository<ChargerDetail, ChargerDetailId> {
}

