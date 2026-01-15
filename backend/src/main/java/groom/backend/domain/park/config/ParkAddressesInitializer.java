package groom.backend.domain.park.config;

import groom.backend.domain.park.entity.Park;
import groom.backend.domain.park.repository.ParkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class ParkAddressesInitializer {


  private static final Map<String, String> ADDRESS_MAP = new HashMap<>();

  static {
    ADDRESS_MAP.put("POI085", "서울 강서구 방화동 2-32");          // 강서한강공원
    ADDRESS_MAP.put("POI086", "서울 구로구 고척동 63-6");          // 고척돔
    ADDRESS_MAP.put("POI087", "서울 강동구 암사동 659-1");        // 광나루한강공원
    ADDRESS_MAP.put("POI088", "서울 종로구 세종로 81-3");         // 광화문광장
    ADDRESS_MAP.put("POI089", "서울 용산구 용산동6가 168-6");     // 국립중앙박물관·용산가족공원
    ADDRESS_MAP.put("POI090", "서울 마포구 상암동 487-116");      // 난지한강공원
    ADDRESS_MAP.put("POI091", "서울 중구 예장동 산5-85");         // 남산공원
    ADDRESS_MAP.put("POI092", "서울 용산구 이촌동 302-146");      // 노들섬
    ADDRESS_MAP.put("POI093", "서울 광진구 자양동 704-1");        // 뚝섬한강공원
    ADDRESS_MAP.put("POI094", "서울 마포구 망원동 205-4");        // 망원한강공원
    ADDRESS_MAP.put("POI095", "서울 서초구 반포동 115-5");        // 반포한강공원
    ADDRESS_MAP.put("POI096", "서울 강북구 번동 90");             // 북서울꿈의숲
    ADDRESS_MAP.put("POI098", "서울 서초구 반포동 산59-1");       // 서리풀공원·몽마르뜨공원
    ADDRESS_MAP.put("POI099", "서울 중구 을지로1가 50-1");        // 서울광장
    ADDRESS_MAP.put("POI100", "경기 과천시 막계동 159-1");        // 서울대공원
    ADDRESS_MAP.put("POI101", "서울 성동구 성수동1가 685-20");    // 서울숲공원
    ADDRESS_MAP.put("POI102", "경기 구리시 아천동");              // 아차산
    ADDRESS_MAP.put("POI103", "서울 영등포구 양화동 1-4");        // 양화한강공원
    ADDRESS_MAP.put("POI104", "서울 광진구 능동 18");             // 어린이대공원
    ADDRESS_MAP.put("POI105", "서울 영등포구 여의도동 8");        // 여의도한강공원
    ADDRESS_MAP.put("POI106", "서울 마포구 상암동 481-6");        // 월드컵공원
    ADDRESS_MAP.put("POI107", "서울 성동구 금호동4가 173-9");     // 응봉산
    ADDRESS_MAP.put("POI108", "서울 용산구 이촌동 302-17");       // 이촌한강공원
    ADDRESS_MAP.put("POI109", "서울 송파구 잠실동 10");           // 잠실종합운동장
    ADDRESS_MAP.put("POI110", "서울 송파구 잠실동 1-1");          // 잠실한강공원
    ADDRESS_MAP.put("POI111", "서울 서초구 잠원동");              // 잠원한강공원
    ADDRESS_MAP.put("POI112", "경기 과천시 막계동");              // 청계산
    ADDRESS_MAP.put("POI113", "서울특별시 종로구 청와대로 1");    // 청와대
    ADDRESS_MAP.put("POI123", "서울 동작구 신대방동 722");        // 보라매공원
    ADDRESS_MAP.put("POI124", "서울 서대문구 현저동 101");        // 서대문독립공원
    ADDRESS_MAP.put("POI125", "경기 군포시 당정동");              // 안양천
    ADDRESS_MAP.put("POI126", "서울 영등포구 여의도동");          // 여의서로
    ADDRESS_MAP.put("POI127", "서울 송파구 방이동 88");           // 올림픽공원
    ADDRESS_MAP.put("POI128", "서울 서대문구 연희동 170-181");    // 홍제폭포
  }


  private final ParkRepository parkRepository;

  @EventListener(ApplicationReadyEvent.class)
  @Order(3)
  @Transactional
  public void onApplicationReady() {
    log.info("=== 공원 지번 주소 초기화 시작 ===");

    int updatedCount = 0;

    for (String areaName : getAllAreaNames()) {
      try {
        String address = getAddress(areaName);

        Park park = parkRepository.findByAreaCode(areaName).orElse(null);
        if (park == null) {
          log.warn("공원을 찾을 수 없습니다 - areaName: {}", areaName);
          continue;
        }

        park.setParkAddr(address);
        parkRepository.save(park);
        updatedCount++;

        log.debug("공원 주소 업데이트 완료 - areaName: {}, addr: {}", areaName, address);
      } catch (Exception e) {
        log.error("공원 주소 초기화 중 오류 발생 - areaName: {}, ERROR: {}",
                areaName, e.getMessage(), e);
      }
    }

    log.info("=== 공원 지번 주소 초기화 완료 - 업데이트된 공원 수: {} ===", updatedCount);
  }

  public static String getAddress(String areaName) {
    return ADDRESS_MAP.get(areaName);
  }

  public static Set<String> getAllAreaNames() {
    return ADDRESS_MAP.keySet();
  }
}
