package groom.backend.domain.parking.service.spec;

import groom.backend.domain.parking.dto.response.ParkingLocationResponse;

/**
 * 주차장 및 전기차 충전소 등 편의시설 정보 제공 서비스
 */
public interface ParkingStatusService {
  /**
   * 전기차 충전소와 주차장의 위치 및 현재 현황 등의 요약 정보를 알려줍니다.
   */
  public ParkingLocationResponse getLocations();
}
