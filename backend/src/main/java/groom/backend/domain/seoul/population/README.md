# Population 도메인

실시간 인구 현황 및 인구 예보 정보를 관리하는 도메인입니다.

## 개요

`population` 도메인은 공원(지역)의 실시간 인구 정보와 미래 인구 예보 데이터를 관리합니다. 시간별 인구 현황을 추적하여 혼잡도 분석 및 예측에 활용할 수 있습니다.

## 엔티티

### 1. LivePopStatus (실시간 인구 현황)
**테이블**: `live_pop_status`

지역의 실시간 인구 정보를 저장합니다.

**주요 필드:**
- `data_get_time` (PK): 데이터 수집 시간
- `area_code` (PK, FK): 지역 코드 → `park.area_code`
- `area_pop_min`: 최소 인구 수
- `area_pop_max`: 최대 인구 수
- `area_congest_level`: 혼잡도 레벨 (여유, 보통, 붐빔, 매우붐빔)
- `area_congest_msg`: 혼잡도 메시지
- `replace_yn`: 교체 여부
- `pop_time`: 인구 수집 시간

**특징:**
- 복합키 사용 (`data_get_time`, `area_code`)
- 시간별 인구 현황 추적 가능
- 실시간 데이터로 업데이트 빈도 높음

**관계:**
- N:1: `Park` (FK: `area_code`)

---

### 2. PredPopStatus (인구 예보)
**테이블**: `pred_pop_status`

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
- 미래 혼잡도 예측에 활용

**관계:**
- N:1: `Park` (FK: `area_code`)

---

## 도메인 역할

`population` 도메인은 다음과 같은 역할을 합니다:

1. **실시간 인구 모니터링**: 현재 지역의 인구 현황 실시간 추적
2. **혼잡도 분석**: 혼잡도 레벨과 메시지를 통한 혼잡도 분석
3. **인구 예보**: 미래 시간대별 인구 예보 제공
4. **데이터 이력 관리**: 시간별 데이터 저장으로 인구 변화 추이 분석

## 사용 예시

```java
// 최신 실시간 인구 현황 조회
Optional<LivePopStatus> latest = livePopStatusRepository
    .findTopByAreaCodeOrderByDataGetTimeDesc("POI110");

// 특정 시간대의 인구 예보 조회
List<PredPopStatus> forecasts = predPopStatusRepository
    .findByAreaCodeAndForecastTimeBetween(
        "POI110", 
        startTime, 
        endTime
    );

// 혼잡도가 높은 지역 조회
List<LivePopStatus> crowded = livePopStatusRepository
    .findByAreaCongestLevelIn(
        Arrays.asList("붐빔", "매우붐빔")
    );
```

## 데이터 특성

- **업데이트 빈도**: 높음 (실시간)
- **데이터 타입**: 동적 데이터 (시간에 따라 변경)
- **보관 기간**: 시간별 데이터가 계속 쌓이므로 주기적 정리 필요

