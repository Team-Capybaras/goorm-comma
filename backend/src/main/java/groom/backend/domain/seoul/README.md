# 서울시 공공 API 도메인 구조

서울시 공공 API 데이터를 도메인별로 분리하여 관리하는 구조입니다.

## 도메인 구조

```
seoul/
├── park/          # 공원 기본 정보
├── population/    # 인구 현황 및 예보
├── weather/       # 날씨 현황
├── parking/       # 주차장 정보 및 현황
├── tag/           # 태그 정보
└── transit/       # 대중교통 정보
```

## 도메인별 개요

### 1. Park 도메인
**경로**: `domain/seoul/park/`

공원(지역) 기본 정보를 관리하는 최상위 도메인입니다.

**엔티티:**
- `Park`: 공원 기본 정보
- `ParkTag`: 공원-태그 연결

**역할:**
- 모든 다른 도메인의 루트 역할
- `area_code`를 통한 데이터 통합

**상세**: [park/README.md](./park/README.md)

---

### 2. Population 도메인
**경로**: `domain/seoul/population/`

실시간 인구 현황 및 인구 예보 정보를 관리합니다.

**엔티티:**
- `LivePopStatus`: 실시간 인구 현황
- `PredPopStatus`: 인구 예보

**역할:**
- 실시간 인구 모니터링
- 혼잡도 분석
- 인구 예보 제공

**상세**: [population/README.md](./population/README.md)

---

### 3. Weather 도메인
**경로**: `domain/seoul/weather/`

날씨 현황 정보를 관리합니다.

**엔티티:**
- `WeatherStatus`: 날씨 현황

**역할:**
- 날씨 모니터링
- 대기질 관리
- 자외선 정보 제공

**상세**: [weather/README.md](./weather/README.md)

---

### 4. Parking 도메인
**경로**: `domain/seoul/parking/`

주차장 정보 및 현황, 전기차 충전소 정보를 관리합니다.

**엔티티:**
- `ParkingLot`: 주차장 정보
- `ParkingLotStatus`: 주차장 현황
- `ChargerStation`: 충전소 정보
- `ChargerDetail`: 충전기 상세 정보
- `ChargerStatus`: 충전기 상태

**역할:**
- 주차장 정보 관리
- 실시간 주차 현황 추적
- 주차장 검색
- 충전소 정보 관리
- 충전기 상태 추적

**상세**: [parking/README.md](./parking/README.md)

---

### 5. Tag 도메인
**경로**: `domain/seoul/tag/`

태그 정보를 관리합니다.

**엔티티:**
- `Tag`: 태그 정보

**역할:**
- 공원 분류
- 태그 기반 검색
- 태그 마스터 데이터 관리

**상세**: [tag/README.md](./tag/README.md)

---

### 6. Transit 도메인
**경로**: `domain/seoul/transit/`

대중교통 정보 및 공유 자전거 정보를 관리합니다.

**엔티티:**
- `SubwayStation`: 지하철역 정보
- `SubwayFacility`: 지하철 시설 정보
- `BusStation`: 버스 정류장 정보
- `Sbike`: 공유 자전거 정보
- `SbikeStatus`: 공유 자전거 현황

**역할:**
- 대중교통 정보 관리
- 접근성 정보 제공
- 공유 자전거 정보 관리
- 위치 기반 검색

**상세**: [transit/README.md](./transit/README.md)

---

## 도메인 간 관계

```
park (최상위)
├── population (인구 현황)
├── weather (날씨)
├── parking (주차장)
├── transit (대중교통)
└── park_tag → tag (태그)
```

모든 도메인은 `park` 도메인의 `Park` 엔티티를 참조하여 지역별 데이터를 관리합니다.

## 데이터 흐름

1. **API 호출**: 서울시 공공 API에서 데이터 수신
2. **DTO 매핑**: Response DTO로 구조화
3. **도메인별 저장**: 각 도메인의 엔티티로 변환하여 저장
4. **관계 설정**: `Park` 엔티티와의 관계 설정

## 도메인별 데이터 특성

| 도메인 | 데이터 타입 | 업데이트 빈도 | 주요 키 |
|--------|-----------|------------|--------|
| `park` | 정적 | 낮음 | `area_code` |
| `population` | 동적 | 높음 | `data_get_time`, `area_code` |
| `weather` | 동적 | 높음 | `data_get_time`, `area_code` |
| `parking` | 정적/동적 | 낮음/높음 | `prk_code` / `data_get_time`, `prk_code` / `station_id` / `charger_id`, `station_id` |
| `tag` | 정적 | 낮음 | `tag_id` |
| `transit` | 정적/동적 | 낮음/높음 | `sub_id`, `bus_stn_id` / `sbike_spot_id` / `data_get_time`, `sbike_spot_id` |

## 사용 가이드

각 도메인별 상세한 설명과 사용 예시는 각 도메인의 README.md 파일을 참조하세요.

- [Park 도메인](./park/README.md)
- [Population 도메인](./population/README.md)
- [Weather 도메인](./weather/README.md)
- [Parking 도메인](./parking/README.md)
- [Tag 도메인](./tag/README.md)
- [Transit 도메인](./transit/README.md)

