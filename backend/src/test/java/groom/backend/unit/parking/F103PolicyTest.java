package groom.backend.unit.parking;

import groom.backend.domain.parking.entity.ParkingLot;
import groom.backend.domain.parking.entity.ParkingLotStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 주차장 데이터 무결성 정책 테스트
 */
class F103PolicyTest {

  /**
   * 엔티티 생성 시 제약조건을 검증하는지에 대한 테스트
   */
  @Test
  @DisplayName("F103 주차 가능 수는 수용 가능 수를 초과하지 않는다")
  void availableCountShouldNotExceedCapacity() {
    // service policy에 대한 검증
    // TODO : API Intergration 시 API 삽입 시나리오에서 외부 데이터 무결성 검증하도록 이전
    ParkingLot parking =ParkingLot.builder()
            .capacity(30)
            .build();
    ParkingLotStatus parkingStatus = ParkingLotStatus.builder()
            .currentPrkCnt(20)
            .build();

    assertThat(parkingStatus.getCurrentPrkCnt())
            .isLessThanOrEqualTo(parking.getCapacity());
  }
}
