# Parking 도메인

주차장 정보 및 현황을 관리하는 도메인입니다.

## 개요

`parking` 도메인은 공원(지역) 주변 주차장의 기본 정보와 실시간 현황을 관리합니다. 주차장의 정적 정보(위치, 요금, 수용 대수)와 동적 정보(현재 주차 대수)를 분리하여 관리합니다.

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

## 도메인 역할

`parking` 도메인은 다음과 같은 역할을 합니다:

1. **주차장 정보 관리**: 주차장의 기본 정보(위치, 요금, 수용 대수) 관리
2. **실시간 현황 추적**: 현재 주차 대수 및 주차 가능 여부 추적
3. **주차장 검색**: 위치 기반 주차장 검색 및 필터링
4. **데이터 이력 관리**: 시간별 데이터 저장으로 주차장 이용 패턴 분석

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
```

## 데이터 특성

- **ParkingLot**: 정적 데이터 (변경 빈도 낮음)
- **ParkingLotStatus**: 동적 데이터 (실시간 업데이트)
- **보관 기간**: 시간별 데이터가 계속 쌓이므로 주기적 정리 필요

