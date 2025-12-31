# 서울시 공공 API 데이터베이스 테이블 구조

이 문서는 서울시 공공 API에서 받아온 데이터를 저장하기 위한 데이터베이스 테이블 구조를 설명합니다.

## 전체 구조 개요

서울시 공공 API의 응답 데이터를 정규화하여 여러 테이블로 분리하여 저장합니다. 모든 테이블은 `park` 테이블을 중심으로 지역(`area_code`)별로 데이터를 관리합니다.

### 테이블 관계도

```
park (공원 기본 정보)
├── live_pop_status (실시간 인구 현황)
├── pred_pop_status (인구 예보)
├── parking_lot (주차장 정보)
│   └── parking_lot_status (주차장 현황)
├── subway_station (지하철역 정보)
│   └── subway_facility (지하철 시설 정보)
├── bus_station (버스 정류장)
├── weather_status (날씨 현황)
├── sbike (공유 자전거 정보)
│   └── sbike_status (공유 자전거 현황)
├── charger_station (충전소 정보)
│   └── charger_detail (충전기 상세)
│       └── charger_status (충전기 상태)
└── park_tag (공원 태그)
    └── tag (태그 정보)
```

## 테이블 상세 설명

### 1. park (공원 기본 정보)
공원(지역)의 기본 정보를 저장하는 최상위 테이블입니다.

**주요 필드:**
- `area_code` (PK): 지역 코드 (예: "POI110")
- `area_name`: 지역명 (예: "잠실한강공원")

**특징:**
- 모든 다른 테이블들이 참조하는 루트 테이블
- `area_code`를 통해 모든 관련 데이터를 조회 가능

**관계:**
- 1:N 관계: `live_pop_status`, `pred_pop_status`, `parking_lot`, `subway_station`, `bus_station`, `weather_status`, `sbike`, `charger_station`, `park_tag`

---

### 2. live_pop_status (실시간 인구 현황)
지역의 실시간 인구 정보를 저장합니다.

**주요 필드:**
- `data_get_time` (PK): 데이터 수집 시간
- `area_code` (PK, FK): 지역 코드 → `park.area_code`
- `area_pop_min`: 최소 인구 수
- `area_pop_max`: 최대 인구 수
- `area_congest_level`: 혼잡도 레벨 (여유, 보통, 붐빔, 매우붐빔)
- `area_congest_msg`: 혼잡도 메시지
- `replace_yn`: 교체 여부 (BOOLEAN)
- `pop_time`: 인구 수집 시간

**특징:**
- 복합키 사용 (`data_get_time`, `area_code`)
- 시간별 인구 현황 추적 가능

**관계:**
- N:1 관계: `park` (FK: `area_code`)

---

### 3. pred_pop_status (인구 예보)
실시간 인구 현황의 미래 예보 데이터를 저장합니다.

**주요 필드:**
- `data_get_time` (PK): 데이터 수집 시간
- `area_code` (PK, FK): 지역 코드 → `park.area_code`
- `forecast_time`: 예보 시간
- `forecase_congest_level`: 예보 혼잡도 레벨
- `forecast_pop_min`: 예보 최소 인구 수
- `forecast_pop_max`: 예보 최대 인구 수

**특징:**
- 복합키 사용 (`data_get_time`, `area_code`)
- 시간별 예보 데이터 저장

**관계:**
- N:1 관계: `park` (FK: `area_code`)

---

### 4. parking_lot (주차장 정보)
지역 주변 주차장의 기본 정보를 저장합니다.

**주요 필드:**
- `prk_code` (PK): 주차장 코드
- `prk_name`: 주차장명
- `prk_type`: 주차장 유형 (BP: 건물부설, NW: 노상)
- `capacity`: 수용 가능 대수
- `current_info_yn`: 현재 정보 여부 (BOOLEAN)
- `pay_yn`: 유료 여부 (BOOLEAN)
- `rates`: 기본 요금
- `time_rates`: 기본 시간 (분)
- `add_rates`: 추가 요금
- `add_time_rates`: 추가 시간 (분)
- `addr`: 주소
- `road_addr`: 도로명 주소
- `prk_x`: 경도 (DECIMAL(13,10))
- `prk_y`: 위도 (DECIMAL(13,10))
- `area_code` (FK): 지역 코드 → `park.area_code`

**특징:**
- 주차장의 정적 정보 저장
- 좌표 정보 포함

**관계:**
- N:1 관계: `park` (FK: `area_code`)
- 1:N 관계: `parking_lot_status`

---

### 5. parking_lot_status (주차장 현황)
주차장의 실시간 현황 정보를 저장합니다.

**주요 필드:**
- `data_get_time` (PK): 데이터 수집 시간
- `prk_code` (PK, FK): 주차장 코드 → `parking_lot.prk_code`
- `current_prk_cnt`: 현재 주차 대수
- `current_prk_time`: 현재 주차 시간

**특징:**
- 복합키 사용 (`data_get_time`, `prk_code`)
- 시간별 주차장 현황 추적 가능

**관계:**
- N:1 관계: `parking_lot` (FK: `prk_code`)

---

### 6. subway_station (지하철역 정보)
지역 주변 지하철역의 기본 정보를 저장합니다.

**주요 필드:**
- `sub_id` (PK): 지하철역 ID
- `sub_stn_name`: 지하철역명
- `sub_stn_line`: 지하철 노선
- `addr`: 주소
- `road_addr`: 도로명 주소
- `sub_stn_x`: 경도 (DECIMAL(13,10))
- `sub_stn_y`: 위도 (DECIMAL(13,10))
- `area_code` (FK): 지역 코드 → `park.area_code`

**특징:**
- 지하철역의 정적 정보 저장
- 좌표 정보 포함

**관계:**
- N:1 관계: `park` (FK: `area_code`)
- 1:N 관계: `subway_facility`

---

### 7. subway_facility (지하철 시설 정보)
지하철역의 엘리베이터, 에스컬레이터 등 시설 정보를 저장합니다.

**주요 필드:**
- `sub_facility_info` (PK): 지하철 시설 정보 ID
- `elvtr_name`: 승강기명
- `operate_sector`: 운영 구간
- `install_position`: 설치 위치
- `use_yn`: 사용 가능 여부
- `elvtr_section`: 승강기 구분 (EV: 엘리베이터, ES: 에스컬레이터)
- `sub_id` (FK): 지하철역 ID → `subway_station.sub_id`

**특징:**
- 지하철역별 여러 시설 정보 저장 가능

**관계:**
- N:1 관계: `subway_station` (FK: `sub_id`)

---

### 8. bus_station (버스 정류장)
지역 주변 버스 정류장 정보를 저장합니다.

**주요 필드:**
- `bus_stn_id` (PK): 버스 정류장 ID
- `bus_ars_id`: 버스 ARS ID
- `bus_stn_name`: 버스 정류장명
- `bus_stn_x`: 경도 (DECIMAL(13,10))
- `bus_stn_y`: 위도 (DECIMAL(13,10))
- `area_code` (FK): 지역 코드 → `park.area_code`

**특징:**
- 버스 정류장의 정적 정보 저장
- 좌표 정보 포함

**관계:**
- N:1 관계: `park` (FK: `area_code`)

---

### 9. weather_status (날씨 현황)
지역의 날씨 정보를 저장합니다.

**주요 필드:**
- `data_get_time` (PK): 데이터 수집 시간
- `area_code` (PK, FK): 지역 코드 → `park.area_code`
- `weather_time`: 날씨 시간
- `temp`: 온도 (DECIMAL(5,2))
- `sensible_temp`: 체감 온도 (DECIMAL(5,2))
- `humidity`: 습도
- `wind_dirct`: 풍향
- `wind_spd`: 풍속 (DECIMAL(5,2))
- `precipitation`: 강수량
- `precpt_type`: 강수 유형
- `precpt_msg`: 강수 메시지
- `uv_index_level`: 자외선 지수 레벨
- `uv_index`: 자외선 지수
- `pm25_index`: 미세먼지(PM2.5) 지수
- `pm25`: 미세먼지(PM2.5) 수치
- `pm10_index`: 초미세먼지(PM10) 지수
- `pm10`: 초미세먼지(PM10) 수치
- `air_index`: 대기질 지수
- `air_index_level`: 대기질 지수 수치 (DECIMAL(5,2))
- `air_index_main`: 대기질 지수 주요 원인
- `air_msg`: 대기질 메시지
- `data_source`: 데이터 소스

**특징:**
- 복합키 사용 (`data_get_time`, `area_code`)
- 시간별 날씨 정보 추적 가능
- 온도, 습도, 대기질 등 종합적인 날씨 정보 저장

**관계:**
- N:1 관계: `park` (FK: `area_code`)

---

### 10. sbike (공유 자전거 정보)
지역 주변 공유 자전거의 기본 정보를 저장합니다.

**주요 필드:**
- `sbike_spot_id` (PK): 공유 자전거 스팟 ID
- `sbike_spot_name`: 공유 자전거 스팟명
- `sbike_capacity`: 공유 자전거 수용 대수
- `sbike_x`: 경도 (DECIMAL(13,10))
- `sbike_y`: 위도 (DECIMAL(13,10))
- `area_code` (FK): 지역 코드 → `park.area_code`

**특징:**
- 공유 자전거의 정적 정보 저장
- 좌표 정보 포함

**관계:**
- N:1 관계: `park` (FK: `area_code`)
- 1:N 관계: `sbike_status`

---

### 11. sbike_status (공유 자전거 현황)
공유 자전거의 실시간 현황 정보를 저장합니다.

**주요 필드:**
- `data_get_time` (PK): 데이터 수집 시간
- `sbike_spot_id` (PK, FK): 공유 자전거 스팟 ID → `sbike.sbike_spot_id`
- `sbike_parking_per`: 공유 자전거 주차 비율
- `sbike_parking_cnt`: 공유 자전거 주차 대수

**특징:**
- 복합키 사용 (`data_get_time`, `sbike_spot_id`)
- 시간별 공유 자전거 현황 추적 가능

**관계:**
- N:1 관계: `sbike` (FK: `sbike_spot_id`)

---

### 12. charger_station (충전소 정보)
지역 주변 전기차 충전소의 기본 정보를 저장합니다.

**주요 필드:**
- `station_id` (PK): 충전소 ID
- `station_name`: 충전소명
- `station_addr`: 충전소 주소
- `station_x`: 경도 (DECIMAL(13,10))
- `station_y`: 위도 (DECIMAL(13,10))
- `station_usetime`: 사용 시간
- `station_parkpay`: 주차 요금 여부 (BOOLEAN)
- `station_limit_detail`: 제한 상세
- `station_kind_detail`: 종류 상세
- `area_code` (FK): 지역 코드 → `park.area_code`

**특징:**
- 충전소의 정적 정보 저장
- 좌표 정보 포함

**관계:**
- N:1 관계: `park` (FK: `area_code`)
- 1:N 관계: `charger_detail`

---

### 13. charger_detail (충전기 상세 정보)
충전소의 충전기 상세 정보를 저장합니다.

**주요 필드:**
- `charger_id` (PK): 충전기 ID
- `station_id` (PK, FK): 충전소 ID → `charger_station.station_id`
- `charger_type`: 충전기 유형 (AC완속, DC급속 등)
- `charger_updated`: 충전기 업데이트 시간
- `charger_timestmap`: 충전기 타임스탬프
- `output`: 출력 (kW)
- `method`: 충전 방식 (단독, 동시 등)

**특징:**
- 복합키 사용 (`charger_id`, `station_id`)
- 충전소별 여러 충전기 정보 저장 가능

**관계:**
- N:1 관계: `charger_station` (FK: `station_id`)
- 1:N 관계: `charger_status`

---

### 14. charger_status (충전기 상태)
충전기의 실시간 상태 정보를 저장합니다.

**주요 필드:**
- `charter_stat_key` (PK): 충전기 상태 키
- `charger_status`: 충전기 상태 (사용가능, 사용중 등)
- `status_updated`: 상태 업데이트 시간
- `status_timestamp`: 상태 타임스탬프
- `data_get_time`: 데이터 수집 시간
- `charger_id` (FK): 충전기 ID → `charger_detail.charger_id`
- `station_id` (FK): 충전소 ID → `charger_detail.station_id`

**특징:**
- 충전기의 실시간 상태 추적
- 복합 외래키 사용 (`charger_id`, `station_id`)

**관계:**
- N:1 관계: `charger_detail` (FK: `charger_id`, `station_id`)

---

### 15. tag (태그 정보)
공원을 분류하기 위한 태그 정보를 저장합니다.

**주요 필드:**
- `tag_id` (PK): 태그 ID
- `tag_name`: 태그명
- `description`: 설명

**특징:**
- 공원 분류를 위한 태그 마스터 데이터

**관계:**
- 1:N 관계: `park_tag`

---

### 16. park_tag (공원 태그 연결)
공원과 태그의 다대다 관계를 저장합니다.

**주요 필드:**
- `park_tag_key` (PK): 공원 태그 키
- `tag_id` (FK): 태그 ID → `tag.tag_id`
- `area_code` (FK): 지역 코드 → `park.area_code`

**특징:**
- 공원과 태그의 다대다 관계를 표현하는 연결 테이블
- 하나의 공원에 여러 태그, 하나의 태그가 여러 공원에 적용 가능

**관계:**
- N:1 관계: `tag` (FK: `tag_id`)
- N:1 관계: `park` (FK: `area_code`)

---

## 데이터 저장 전략

### 복합키 사용 테이블
다음 테이블들은 복합키를 사용하여 시간별 데이터를 추적합니다:
- `live_pop_status`: (`data_get_time`, `area_code`)
- `pred_pop_status`: (`data_get_time`, `area_code`)
- `parking_lot_status`: (`data_get_time`, `prk_code`)
- `weather_status`: (`data_get_time`, `area_code`)
- `sbike_status`: (`data_get_time`, `sbike_spot_id`)
- `charger_detail`: (`charger_id`, `station_id`)

### 정적 정보 vs 동적 정보
- **정적 정보**: 한 번 저장되면 자주 변경되지 않는 정보
  - `park`, `parking_lot`, `subway_station`, `bus_station`, `sbike`, `charger_station`
  
- **동적 정보**: 시간에 따라 자주 변경되는 정보
  - `live_pop_status`, `pred_pop_status`, `parking_lot_status`, `weather_status`, `sbike_status`, `charger_status`

### NULL 값 처리
- 모든 필드는 `nullable = true`로 설정되어 있어 빈 데이터도 저장 가능합니다.
- API 응답에서 빈 문자열("")이나 null 값이 올 수 있는 필드들은 모두 nullable로 설정했습니다.

### BaseEntity 상속
모든 엔티티는 `BaseEntity`를 상속받아 다음 필드를 자동으로 가집니다:
- `id`: PK (단일키 테이블의 경우 별도 정의)
- `version`: 낙관적 잠금용 버전
- `createdAt`: 생성 시간
- `updatedAt`: 수정 시간
- `deletedAt`: 삭제 시간 (소프트 삭제용)

---

## 사용 예시

### 데이터 저장 흐름
1. API 호출 → JSON 응답 받기
2. JSON 파싱하여 각 엔티티 객체 생성
3. `park` 테이블에 공원 정보 저장 (없는 경우)
4. 각 테이블에 데이터 저장
5. 관계 설정 (외래키 연결)
6. 트랜잭션으로 일괄 저장

### 조회 예시
```sql
-- 특정 지역의 모든 정보 조회
SELECT * FROM park WHERE area_code = 'POI110';

-- 실시간 인구 현황 조회
SELECT lps.* 
FROM live_pop_status lps
WHERE lps.area_code = 'POI110'
ORDER BY lps.data_get_time DESC
LIMIT 1;

-- 주차장 정보와 현황 조회
SELECT pl.*, pls.current_prk_cnt, pls.current_prk_time
FROM parking_lot pl
LEFT JOIN parking_lot_status pls ON pl.prk_code = pls.prk_code
WHERE pl.area_code = 'POI110'
ORDER BY pls.data_get_time DESC;
```

### JPA 조회 예시
```java
// 특정 지역의 최신 실시간 인구 현황 조회
@Query("SELECT lps FROM LivePopStatus lps " +
       "WHERE lps.areaCode = :areaCode " +
       "ORDER BY lps.dataGetTime DESC")
Optional<LivePopStatus> findLatestByAreaCode(@Param("areaCode") String areaCode);

// 주차장 정보와 최신 현황 조회
@Query("SELECT pl FROM ParkingLot pl " +
       "LEFT JOIN FETCH pl.parkingLotStatuses pls " +
       "WHERE pl.areaCode = :areaCode " +
       "ORDER BY pls.dataGetTime DESC")
List<ParkingLot> findParkingLotsWithLatestStatus(@Param("areaCode") String areaCode);
```

---

## 주의사항

1. **외래키 제약조건**: 현재는 외래키 제약조건을 설정하지 않았습니다. 필요시 `@JoinColumn` 어노테이션을 추가하여 설정할 수 있습니다.

2. **인덱스**: 자주 조회되는 필드(`area_code`, `data_get_time` 등)에 인덱스를 추가하는 것을 권장합니다.
   ```sql
   CREATE INDEX idx_live_pop_status_area_code ON live_pop_status(area_code);
   CREATE INDEX idx_live_pop_status_data_get_time ON live_pop_status(data_get_time);
   ```

3. **데이터 타입**: 
   - 좌표 정보는 `DECIMAL(13,10)` 타입 사용
   - 시간 정보는 `TIMESTAMP` 타입 사용
   - 불린 값은 `BOOLEAN` 타입 사용

4. **트랜잭션**: 여러 테이블에 데이터를 저장할 때는 트랜잭션을 사용하여 데이터 일관성을 보장해야 합니다.

5. **데이터 중복**: 시간별 데이터가 계속 쌓이므로, 오래된 데이터는 주기적으로 정리하는 전략이 필요할 수 있습니다.

6. **복합키 클래스**: 복합키를 사용하는 엔티티는 별도의 ID 클래스를 생성해야 하며, `Serializable`을 구현해야 합니다.

---

## 테이블별 데이터 특성

| 테이블명 | 데이터 특성 | 업데이트 빈도 | 주요 키 |
|---------|-----------|------------|--------|
| `park` | 정적 | 낮음 | `area_code` |
| `live_pop_status` | 동적 | 높음 (실시간) | `data_get_time`, `area_code` |
| `pred_pop_status` | 동적 | 높음 (예보) | `data_get_time`, `area_code` |
| `parking_lot` | 정적 | 낮음 | `prk_code` |
| `parking_lot_status` | 동적 | 높음 (실시간) | `data_get_time`, `prk_code` |
| `subway_station` | 정적 | 낮음 | `sub_id` |
| `subway_facility` | 정적 | 낮음 | `sub_facility_info` |
| `bus_station` | 정적 | 낮음 | `bus_stn_id` |
| `weather_status` | 동적 | 높음 (실시간) | `data_get_time`, `area_code` |
| `sbike` | 정적 | 낮음 | `sbike_spot_id` |
| `sbike_status` | 동적 | 높음 (실시간) | `data_get_time`, `sbike_spot_id` |
| `charger_station` | 정적 | 낮음 | `station_id` |
| `charger_detail` | 정적 | 낮음 | `charger_id`, `station_id` |
| `charger_status` | 동적 | 높음 (실시간) | `charter_stat_key` |
| `tag` | 정적 | 낮음 | `tag_id` |
| `park_tag` | 정적 | 낮음 | `park_tag_key` |

---

## 데이터 모델링 원칙

1. **정규화**: 데이터 중복을 최소화하여 정규화된 구조로 설계
2. **시간 추적**: 동적 데이터는 시간 정보를 포함하여 이력 관리
3. **관계 명확화**: 외래키를 통한 명확한 관계 정의
4. **확장성**: 새로운 데이터 타입 추가 시 기존 구조에 영향 최소화
5. **조회 효율성**: 자주 조회되는 데이터는 인덱스 고려

