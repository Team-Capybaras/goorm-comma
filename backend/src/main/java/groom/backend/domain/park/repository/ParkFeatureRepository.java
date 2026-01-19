package groom.backend.domain.park.repository;

import groom.backend.domain.park.entity.ParkFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkFeatureRepository extends JpaRepository<ParkFeature, Long> {
  List<ParkFeature> findByAreaCode(String areaCode);
}
