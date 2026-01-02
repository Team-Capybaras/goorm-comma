# Transit 도메인

대중교통 정보를 관리하는 도메인입니다.

## 개요

`transit` 도메인은 공원(지역) 주변의 대중교통 정보를 관리합니다. 지하철역과 버스 정류장의 기본 정보를 저장하여 접근성 정보를 제공하며, 공유 자전거 정보도 함께 관리합니다.

## 엔티티

### 1. SubwayStation (지하철역 정보)
**테이블**: `subway_station`

지역 주변 지하철역의 기본 정보를 저장합니다.

**주요 필드:**
- `sub_id` (PK): 지하철역 ID
- `sub_stn_name`: 지하철역명
- `sub_stn_line`: 지하철 노선
- `addr`: 주소
- `road_addr`: 도로명 주소
- `sub_stn_x`: 경도
- `sub_stn_y`: 위도
- `area_code` (FK): 지역 코드 → `park.area_code`

**특징:**
- 지하철역의 정적 정보 저장
- 좌표 정보 포함
- 여러 노선이 있는 경우 여러 레코드로 저장

**관계:**
- N:1: `Park` (FK: `area_code`)
- 1:N: `SubwayFacility`

---

### 2. SubwayFacility (지하철 시설 정보)
**테이블**: `subway_facility`

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
- 접근성 정보 제공

**관계:**
- N:1: `SubwayStation` (FK: `sub_id`)

---

### 3. BusStation (버스 정류장 정보)
**테이블**: `bus_station`

지역 주변 버스 정류장 정보를 저장합니다.

**주요 필드:**
- `bus_stn_id` (PK): 버스 정류장 ID
- `bus_ars_id`: 버스 ARS ID
- `bus_stn_name`: 버스 정류장명
- `bus_stn_x`: 경도
- `bus_stn_y`: 위도
- `area_code` (FK): 지역 코드 → `park.area_code`

**특징:**
- 버스 정류장의 정적 정보 저장
- 좌표 정보 포함
- ARS ID를 통한 버스 정보 조회 가능

**관계:**
- N:1: `Park` (FK: `area_code`)

---

### 4. Sbike (공유 자전거 정보)
**테이블**: `sbike`

지역 주변 공유 자전거의 기본 정보를 저장합니다.

**주요 필드:**
- `sbike_spot_id` (PK): 공유 자전거 스팟 ID
- `sbike_spot_name`: 공유 자전거 스팟명
- `sbike_capacity`: 공유 자전거 수용 대수
- `sbike_x`: 경도
- `sbike_y`: 위도
- `area_code` (FK): 지역 코드 → `park.area_code`

**특징:**
- 공유 자전거의 정적 정보 저장
- 좌표 정보 포함

**관계:**
- N:1: `Park` (FK: `area_code`)
- 1:N: `SbikeStatus`

---

### 5. SbikeStatus (공유 자전거 현황)
**테이블**: `sbike_status`

공유 자전거의 실시간 현황 정보를 저장합니다.

**주요 필드:**
- `data_get_time` (PK): 데이터 수집 시간
- `sbike_spot_id` (PK, FK): 공유 자전거 스팟 ID → `sbike.sbike_spot_id`
- `sbike_parking_per`: 공유 자전거 주차 비율
- `sbike_parking_cnt`: 공유 자전거 주차 대수

**특징:**
- 복합키 사용 (`data_get_time`, `sbike_spot_id`)
- 시간별 공유 자전거 현황 추적 가능
- 실시간 데이터로 업데이트 빈도 높음

**관계:**
- N:1: `Sbike` (FK: `sbike_spot_id`)

---

## 도메인 역할

`transit` 도메인은 다음과 같은 역할을 합니다:

1. **대중교통 정보 관리**: 지하철역 및 버스 정류장의 기본 정보 관리
2. **접근성 정보 제공**: 공원으로의 대중교통 접근성 정보 제공
3. **시설 정보 관리**: 지하철역의 접근성 시설(엘리베이터, 에스컬레이터) 정보 관리
4. **공유 자전거 정보 관리**: 공유 자전거 스팟의 기본 정보 및 실시간 현황 관리
5. **위치 기반 검색**: 좌표 정보를 통한 근처 대중교통 및 공유 자전거 검색

## 사용 예시

```java
// 지역의 모든 지하철역 조회
List<SubwayStation> subwayStations = subwayStationRepository
    .findByAreaCode("POI110");

// 지하철역의 시설 정보 조회
List<SubwayFacility> facilities = subwayFacilityRepository
    .findBySubId(subId);

// 지역의 모든 버스 정류장 조회
List<BusStation> busStations = busStationRepository
    .findByAreaCode("POI110");

// 근처 대중교통 검색 (좌표 기반)
List<SubwayStation> nearbySubways = subwayStationRepository
    .findNearbyStations(latitude, longitude, radius);

// 지역의 모든 공유 자전거 스팟 조회
List<Sbike> sbikes = sbikeRepository
    .findByAreaCode("POI110");

// 공유 자전거 정보와 최신 현황 조회
Sbike sbike = sbikeRepository
    .findBySbikeSpotId(sbikeSpotId);
SbikeStatus latestStatus = sbikeStatusRepository
    .findTopBySbikeSpotIdOrderByDataGetTimeDesc(sbikeSpotId);

// 사용 가능한 공유 자전거 조회
List<SbikeStatus> available = sbikeStatusRepository
    .findAvailableSbikes(areaCode, currentTime);
```

## 데이터 특성

- **SubwayStation, BusStation, Sbike**: 정적 데이터 (변경 빈도 낮음)
- **SbikeStatus**: 동적 데이터 (실시간 업데이트)
- **보관 기간**: 시간별 데이터가 계속 쌓이므로 주기적 정리 필요
- **용도**: 접근성 정보 제공 및 위치 기반 검색

