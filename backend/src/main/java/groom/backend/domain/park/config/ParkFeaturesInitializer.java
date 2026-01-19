package groom.backend.domain.park.config;

import groom.backend.domain.enums.FeatureType;
import groom.backend.domain.park.entity.ParkFeature;
import groom.backend.domain.park.repository.ParkFeatureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class ParkFeaturesInitializer {

  public record Feature(FeatureType type, String description) {}

  public static final Map<String, List<Feature>> PARK_FEATURE_MAP =
          Map.ofEntries(

                  Map.entry("POI085", List.of(
                          new Feature(FeatureType.COURSE, "한강 바람 맞으며 차박·피크닉하기 좋아요"),
                          new Feature(FeatureType.FACILITY, "텐트 치기 좋고, 습지 관찰 포인트도 있어요"),
                          new Feature(FeatureType.NEARBY, "강서구 한강변 드라이브 코스로 이어져요")
                  )),

                  Map.entry("POI086", List.of(
                          new Feature(FeatureType.COURSE, "경기 관람이나 공연 일정에 맞춰 들르기 좋아요"),
                          new Feature(FeatureType.FACILITY, "야구장과 공연·이벤트, 주차장이 있어요"),
                          new Feature(FeatureType.NEARBY, "주변 상권에서 식사·카페 들르기 좋아요")
                  )),

                  Map.entry("POI087", List.of(
                          new Feature(FeatureType.COURSE, "한강 따라 걷고 쉬기 좋아요"),
                          new Feature(FeatureType.FACILITY, "수영장과 자전거, 모두의 놀이터가 있어요"),
                          new Feature(FeatureType.NEARBY, "광나루역 중심으로 동쪽 한강 접근이 쉬워요")
                  )),

                  Map.entry("POI088", List.of(
                          new Feature(FeatureType.COURSE, "도심 한가운데서 가볍게 걷기 좋아요"),
                          new Feature(FeatureType.FACILITY, "분수와 행사, 문화 콘텐츠를 볼 수 있어요"),
                          new Feature(FeatureType.NEARBY, "경복궁·세종문화회관과 함께 둘러요")
                  )),

                  Map.entry("POI089", List.of(
                          new Feature(FeatureType.COURSE, "박물관 관람 후 잔디길 걷기 좋아요"),
                          new Feature(FeatureType.FACILITY, "조각작품을 보며 천천히 산책할 수 있어요"),
                          new Feature(FeatureType.NEARBY, "이촌·서빙고 쪽 맛집/카페와 연결돼요")
                  )),

                  Map.entry("POI090", List.of(
                          new Feature(FeatureType.COURSE, "캠핑 분위기 내며 한강 즐기기 좋아요"),
                          new Feature(FeatureType.FACILITY, "캠핑장과 페스티벌, 자전거 코스가 있어요"),
                          new Feature(FeatureType.NEARBY, "하늘공원·월드컵경기장과 이어서 가기 좋아요")
                  )),

                  Map.entry("POI091", List.of(
                          new Feature(FeatureType.COURSE, "야경 보며 걷기 좋아요"),
                          new Feature(FeatureType.FACILITY, "전망 포인트가 있고, 단풍 시즌이 특히 좋아요"),
                          new Feature(FeatureType.NEARBY, "명동·남산 일대 코스로 묶기 좋아요")
                  )),

                  Map.entry("POI092", List.of(
                          new Feature(FeatureType.COURSE, "공연도 보고 피크닉도 하기 좋아요"),
                          new Feature(FeatureType.FACILITY, "공연·페스티벌과 피크닉존을 이용할 수 있어요"),
                          new Feature(FeatureType.NEARBY, "용산·여의도 이동이 편해요")
                  )),

                  Map.entry("POI093", List.of(
                          new Feature(FeatureType.COURSE, "가볍게 피크닉하기 좋아요"),
                          new Feature(FeatureType.FACILITY, "자전거 대여와 수영장, 썰매장을 이용할 수 있어요"),
                          new Feature(FeatureType.NEARBY, "성수·건대입구 라인과 함께 들러요")
                  )),

                  Map.entry("POI094", List.of(
                          new Feature(FeatureType.COURSE, "강변 따라 걷고 노을 보기 좋아요"),
                          new Feature(FeatureType.FACILITY, "자전거길이 잘 정리돼 있어요"),
                          new Feature(FeatureType.NEARBY, "망원시장 들렀다 산책하기 좋아요")
                  )),

                  Map.entry("POI095", List.of(
                          new Feature(FeatureType.COURSE, "반포 야경 코스로 좋아요"),
                          new Feature(FeatureType.FACILITY, "달빛무지개분수와 교량 조망이 유명해요"),
                          new Feature(FeatureType.NEARBY, "고터·서래마을과 같이 움직이기 좋아요")
                  )),

                  Map.entry("POI096", List.of(
                          new Feature(FeatureType.COURSE, "트래킹 겸 나들이하기 좋아요"),
                          new Feature(FeatureType.FACILITY, "전망대, 아트센터, 사슴방사장 등 볼거리가 많아요"),
                          new Feature(FeatureType.NEARBY, "강북권 대표 공원으로 코스 잡기 좋아요")
                  )),

                  Map.entry("POI098", List.of(
                          new Feature(FeatureType.COURSE, "숲길 걷고 전망대까지 오르기 좋아요"),
                          new Feature(FeatureType.FACILITY, "테마 산책길과 전망 포인트가 있어요"),
                          new Feature(FeatureType.NEARBY, "서래마을·중앙도서관 근처예요")
                  )),

                  Map.entry("POI099", List.of(
                          new Feature(FeatureType.COURSE, "광장 산책하며 잠깐 쉬기 좋아요"),
                          new Feature(FeatureType.FACILITY, "이벤트, 잔디밭, 스케이트장 시즌이 있어요"),
                          new Feature(FeatureType.NEARBY, "덕수궁·을지로까지 동선이 좋아요")
                  )),

                  Map.entry("POI100", List.of(
                          new Feature(FeatureType.COURSE, "하루 종일 둘러보기 좋아요"),
                          new Feature(FeatureType.FACILITY, "동물원, 테마가든, 썰매장으로 구성돼 있어요"),
                          new Feature(FeatureType.NEARBY, "현대미술관까지 하루 코스로 좋아요")
                  )),

                  Map.entry("POI101", List.of(
                          new Feature(FeatureType.COURSE, "도심 숲에서 러닝하기 좋아요"),
                          new Feature(FeatureType.FACILITY, "사슴, 호수, 단풍 명소가 있어요"),
                          new Feature(FeatureType.NEARBY, "성수 카페·쇼핑 코스와 잘 이어져요")
                  )),

                  Map.entry("POI102", List.of(
                          new Feature(FeatureType.COURSE, "트래킹 입문 코스로 좋아요"),
                          new Feature(FeatureType.FACILITY, "정상 전망과 일출 포인트가 있어요"),
                          new Feature(FeatureType.NEARBY, "광나루·사가정 쪽에서 접근해요")
                  )),

                  Map.entry("POI103", List.of(
                          new Feature(FeatureType.COURSE, "한강에서 피크닉하고 자전거 타기 좋아요"),
                          new Feature(FeatureType.FACILITY, "텐트존·그늘막존이 있어 쉬기 좋아요"),
                          new Feature(FeatureType.NEARBY, "선유도공원과 같이 묶어 보기 좋아요")
                  )),

                  Map.entry("POI104", List.of(
                          new Feature(FeatureType.COURSE, "아이와 하루 보내기 좋아요"),
                          new Feature(FeatureType.FACILITY, "동물원·놀이공원·식물원·음악분수가 있어요"),
                          new Feature(FeatureType.NEARBY, "건대입구 상권에서 식사하기 좋아요")
                  )),

                  Map.entry("POI105", List.of(
                          new Feature(FeatureType.COURSE, "자전거로 한강 코스 즐기기 좋아요"),
                          new Feature(FeatureType.FACILITY, "요트 마리나·분수·불꽃축제 등 볼거리가 있어요"),
                          new Feature(FeatureType.NEARBY, "더현대·여의도 코스와 이어져요")
                  )),

                  Map.entry("POI106", List.of(
                          new Feature(FeatureType.COURSE, "넓은 공원에서 피크닉하기 좋아요"),
                          new Feature(FeatureType.FACILITY, "메타세쿼이아길·캠핑장·전망대가 있어요"),
                          new Feature(FeatureType.NEARBY, "상암 쪽에서 장 보고 가기 좋아요")
                  )),

                  Map.entry("POI107", List.of(
                          new Feature(FeatureType.COURSE, "노을 보며 잠깐 걷기 좋아요"),
                          new Feature(FeatureType.FACILITY, "팔각정 전망 포인트가 있어요"),
                          new Feature(FeatureType.NEARBY, "서울숲과 가까워요")
                  )),

                  Map.entry("POI108", List.of(
                          new Feature(FeatureType.COURSE, "운동장 시설 이용하며 걷기 좋아요"),
                          new Feature(FeatureType.FACILITY, "인라인·테니스장 등 시설이 많아요"),
                          new Feature(FeatureType.NEARBY, "노들섬·용산역과 동선이 좋아요")
                  )),

                  Map.entry("POI109", List.of(
                          new Feature(FeatureType.COURSE, "스포츠 이벤트나 콘서트를 보러 가기 좋아요."),
                          new Feature(FeatureType.FACILITY, "야구와 공연 등 다양한 이벤트를 관람할 수 있어요."),
                          new Feature(FeatureType.NEARBY, "석촌호수·롯데타워·코엑스로 이어져요")
                  )),

                  Map.entry("POI110", List.of(
                          new Feature(FeatureType.COURSE, "강변 산책과 피크닉을 함께 좋아요"),
                          new Feature(FeatureType.FACILITY, "대여소·모래공원·자연학습장이 있어요"),
                          new Feature(FeatureType.NEARBY, "석촌호수까지 코스 잡기 좋아요")
                  )),

                  Map.entry("POI111", List.of(
                          new Feature(FeatureType.COURSE, "러닝·자전거 코스로 좋아요"),
                          new Feature(FeatureType.FACILITY, "수영장·눈썰매장 등 시즌 시설이 있어요"),
                          new Feature(FeatureType.NEARBY, "세빛섬·서래섬까지 이어져요")
                  )),

                  Map.entry("POI112", List.of(
                          new Feature(FeatureType.COURSE, "등산 초보자 코스로 좋아요"),
                          new Feature(FeatureType.FACILITY, "계곡·사찰·산림욕으로 쉬기 좋아요"),
                          new Feature(FeatureType.NEARBY, "서울랜드·대공원과 같이 가요")
                  )),

                  Map.entry("POI113", List.of(
                          new Feature(FeatureType.COURSE, "정원 산책하며 둘러보기 좋아요"),
                          new Feature(FeatureType.FACILITY, "역사적인 건물과 공간을 볼 수 있어요"),
                          new Feature(FeatureType.NEARBY, "경복궁·광화문 일대와 가까워요")
                  )),

                  Map.entry("POI123", List.of(
                          new Feature(FeatureType.COURSE, "맨발 산책·러닝하기 좋아요"),
                          new Feature(FeatureType.FACILITY, "연못, 클라이밍, 반려견 놀이터까지 있어요"),
                          new Feature(FeatureType.NEARBY, "보라매 일대 생활권과 가까워요")
                  )),

                  Map.entry("POI124", List.of(
                          new Feature(FeatureType.COURSE, "역사 산책 코스로 좋아요"),
                          new Feature(FeatureType.FACILITY, "체육시설·도서관 등 생활시설이 있어요"),
                          new Feature(FeatureType.NEARBY, "안산·신촌 쪽과 함께 움직이기 좋아요")
                  )),

                  Map.entry("POI125", List.of(
                          new Feature(FeatureType.COURSE, "긴 강변 코스로 걷기 좋아요"),
                          new Feature(FeatureType.FACILITY, "황토길·테니스장 등 운동 요소가 있어요"),
                          new Feature(FeatureType.NEARBY, "평촌에서 한강까지 이어지는 길이에요")
                  )),

                  Map.entry("POI126", List.of(
                          new Feature(FeatureType.COURSE, "여의도 산책 코스로 좋아요"),
                          new Feature(FeatureType.FACILITY, "봄 벚꽃과 가을 단풍 시즌이 좋아요"),
                          new Feature(FeatureType.NEARBY, "국회·KBS·더현대와 가까워요")
                  )),

                  Map.entry("POI127", List.of(
                          new Feature(FeatureType.COURSE, "산책·러닝 코스로 좋아요"),
                          new Feature(FeatureType.FACILITY, "나홀로나무·장미광장 포인트가 있어요"),
                          new Feature(FeatureType.NEARBY, "몽촌토성역 쪽에서 접근해요")
                  )),

                  Map.entry("POI128", List.of(
                          new Feature(FeatureType.COURSE, "폭포 따라 걷기 좋아요"),
                          new Feature(FeatureType.FACILITY, "수변 카페와 테라스에서 쉬기 좋아요"),
                          new Feature(FeatureType.NEARBY, "안산·홍제천 코스와 이어져요")
                  ))
          );

  private final ParkFeatureRepository parkFeatureRepository;


  @EventListener(ApplicationReadyEvent.class)
  @Order(4)
  @Transactional
  public void onApplicationReady() {
    log.info("=== 공원 설명 초기화 시작 ===");

    int updatedCount = 0;

    for (String areaCode : getAllAreaCodes()) {
      try {
        List<Feature> features = getFeatures(areaCode);

        List<ParkFeature> parkFeatures = parkFeatureRepository.findByAreaCode(areaCode);
        if (!parkFeatures.isEmpty()) continue;

        for (Feature feature : features) {
          ParkFeature parkFeature = ParkFeature.builder()
                  .type(feature.type)
                  .areaCode(areaCode)
                  .description(feature.description)
                  .build();
          parkFeatureRepository.save(parkFeature);
        }

        updatedCount++;

        log.debug("공원 설명 업데이트 완료 - areaCode: {}, addr: {}", areaCode, features);
      } catch (Exception e) {
        log.error("공원 설명 초기화 중 오류 발생 - areaCode: {}, ERROR: {}",
                areaCode, e.getMessage(), e);
      }
    }

    log.info("=== 공원 설명 초기화 완료 - 업데이트된 공원 수: {} ===", updatedCount);
  }

  public static List<Feature> getFeatures(String areaCode) {
    return PARK_FEATURE_MAP.get(areaCode);
  }

  public static Set<String> getAllAreaCodes() {
    return PARK_FEATURE_MAP.keySet();
  }
}
