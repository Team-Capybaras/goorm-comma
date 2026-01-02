# Parking 도메인

주차장 정보 및 현황을 관리하는 도메인입니다.

## 개요

`parking` 도메인은 공원(지역) 주변 주차장의 기본 정보와 실시간 현황, 그리고 전기차 충전소 정보를 관리합니다. 주차장의 정적 정보(위치, 요금, 수용 대수)와 동적 정보(현재 주차 대수)를 분리하여 관리하며, 충전소의 정보와 상태도 함께 관리합니다.

## 엔티티

### 1. ParkingLot (주차장 정보)
**테이블**: `parking_lot`

지역 주변 주차장의 기본 정보를 저장합니다.

**주요 필드:**
- `prk_code` (PK): 주차장 코드
- `prk_name`: 주차장명
- `prk_type`: 주차장 유형 (BP: 건물부설, NW: 노상)
- `capacity`: 수용 가능 대수
- `current_info_yn`: 현재 정보 여부
- `pay_yn`: 유료 여부
- `rates`: 기본 요금
- `time_rates`: 기본 시간 (분)
- `add_rates`: 추가 요금
- `add_time_rates`: 추가 시간 (분)
- `addr`: 주소
- `road_addr`: 도로명 주소
- `prk_x`: 경도
- `prk_y`: 위도
- `area_code` (FK): 지역 코드 → `park.area_code`

**특징:**
- 주차장의 정적 정보 저장
- 좌표 정보 포함
- 요금 정보 포함

**관계:**
- N:1: `Park` (FK: `area_code`)
- 1:N: `ParkingLotStatus`

---

### 2. ParkingLotStatus (주차장 현황)
**테이블**: `parking_lot_status`

주차장의 실시간 현황 정보를 저장합니다.

**주요 필드:**
- `data_get_time` (PK): 데이터 수집 시간
- `prk_code` (PK, FK): 주차장 코드 → `parking_lot.prk_code`
- `current_prk_cnt`: 현재 주차 대수
- `current_prk_time`: 현재 주차 시간

**특징:**
- 복합키 사용 (`data_get_time`, `prk_code`)
- 시간별 주차장 현황 추적 가능
- 실시간 데이터로 업데이트 빈도 높음

**관계:**
- N:1: `ParkingLot` (FK: `prk_code`)

---

### 3. ChargerStation (충전소 정보)
**테이블**: `charger_station`

지역 주변 전기차 충전소의 기본 정보를 저장합니다.

**주요 필드:**
- `station_id` (PK): 충전소 ID
- `station_name`: 충전소명
- `station_addr`: 충전소 주소
- `station_x`: 경도
- `station_y`: 위도
- `station_usetime`: 사용 시간
- `station_parkpay`: 주차 요금 여부
- `station_limit_detail`: 제한 상세
- `station_kind_detail`: 종류 상세
- `area_code` (FK): 지역 코드 → `park.area_code`

**특징:**
- 충전소의 정적 정보 저장
- 좌표 정보 포함
- 주차 요금 정보 포함

**관계:**
- N:1: `Park` (FK: `area_code`)
- 1:N: `ChargerDetail`

---

### 4. ChargerDetail (충전기 상세 정보)
**테이블**: `charger_detail`

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
- N:1: `ChargerStation` (FK: `station_id`)
- 1:N: `ChargerStatus`

---

### 5. ChargerStatus (충전기 상태)
**테이블**: `charger_status`

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
- N:1: `ChargerDetail` (FK: `charger_id`, `station_id`)

---

## 도메인 역할

`parking` 도메인은 다음과 같은 역할을 합니다:

1. **주차장 정보 관리**: 주차장의 기본 정보(위치, 요금, 수용 대수) 관리
2. **실시간 현황 추적**: 현재 주차 대수 및 주차 가능 여부 추적
3. **주차장 검색**: 위치 기반 주차장 검색 및 필터링
4. **충전소 정보 관리**: 전기차 충전소의 기본 정보 및 충전기 상세 정보 관리
5. **충전기 상태 추적**: 충전기의 실시간 사용 가능 여부 추적
6. **데이터 이력 관리**: 시간별 데이터 저장으로 주차장 및 충전소 이용 패턴 분석

## 사용 예시

```java
// 지역의 모든 주차장 조회
List<ParkingLot> parkingLots = parkingLotRepository
    .findByAreaCode("POI110");

// 주차장 정보와 최신 현황 조회
ParkingLot parkingLot = parkingLotRepository
    .findByPrkCode(prkCode);
ParkingLotStatus latestStatus = parkingLotStatusRepository
    .findTopByPrkCodeOrderByDataGetTimeDesc(prkCode);

// 주차 가능한 주차장 조회
List<ParkingLotStatus> available = parkingLotStatusRepository
    .findAvailableParkingLots(areaCode, currentTime);

// 지역의 모든 충전소 조회
List<ChargerStation> chargerStations = chargerStationRepository
    .findByAreaCode("POI110");

// 충전소 정보와 충전기 상세 조회
ChargerStation chargerStation = chargerStationRepository
    .findByStationId(stationId);
List<ChargerDetail> chargerDetails = chargerDetailRepository
    .findByStationId(stationId);

// 사용 가능한 충전기 조회
List<ChargerStatus> availableChargers = chargerStatusRepository
    .findByChargerStatus("사용가능");
```

## 데이터 특성

- **ParkingLot, ChargerStation**: 정적 데이터 (변경 빈도 낮음)
- **ParkingLotStatus, ChargerStatus**: 동적 데이터 (실시간 업데이트)
- **ChargerDetail**: 정적 데이터 (충전기 정보는 변경 빈도 낮음)
- **보관 기간**: 시간별 데이터가 계속 쌓이므로 주기적 정리 필요

