# Weather 도메인

날씨 현황 정보를 관리하는 도메인입니다.

## 개요

`weather` 도메인은 공원(지역)의 날씨 정보를 관리합니다. 온도, 습도, 강수량, 대기질 등 종합적인 날씨 정보를 시간별로 추적하여 저장합니다.

## 엔티티

### WeatherStatus (날씨 현황)
**테이블**: `weather_status`

지역의 날씨 정보를 저장합니다.

**주요 필드:**
- `data_get_time` (PK): 데이터 수집 시간
- `area_code` (PK, FK): 지역 코드 → `park.area_code`
- `weather_time`: 날씨 시간
- `temp`: 온도
- `sensible_temp`: 체감 온도
- `humidity`: 습도
- `wind_dirct`: 풍향
- `wind_spd`: 풍속
- `precipitation`: 강수량
- `precpt_type`: 강수 유형
- `precpt_msg`: 강수 메시지
- `uv_index_level`: 자외선 지수 레벨
- `uv_index`: 자외선 지수
- `pm25_index`, `pm25`: 미세먼지(PM2.5) 지수 및 수치
- `pm10_index`, `pm10`: 초미세먼지(PM10) 지수 및 수치
- `air_index`: 대기질 지수
- `air_index_level`: 대기질 지수 수치
- `air_index_main`: 대기질 지수 주요 원인
- `air_msg`: 대기질 메시지
- `data_source`: 데이터 소스

**특징:**
- 복합키 사용 (`data_get_time`, `area_code`)
- 시간별 날씨 정보 추적 가능
- 온도, 습도, 대기질 등 종합적인 날씨 정보 포함

**관계:**
- N:1: `Park` (FK: `area_code`)

---

## 도메인 역할

`weather` 도메인은 다음과 같은 역할을 합니다:

1. **날씨 모니터링**: 현재 지역의 날씨 정보 실시간 추적
2. **대기질 관리**: 미세먼지, 초미세먼지, 대기질 지수 관리
3. **자외선 정보**: 자외선 지수를 통한 건강 정보 제공
4. **데이터 이력 관리**: 시간별 데이터 저장으로 날씨 변화 추이 분석

## 사용 예시

```java
// 최신 날씨 현황 조회
Optional<WeatherStatus> latest = weatherStatusRepository
    .findTopByAreaCodeOrderByDataGetTimeDesc("POI110");

// 특정 시간대의 날씨 정보 조회
List<WeatherStatus> weathers = weatherStatusRepository
    .findByAreaCodeAndDataGetTimeBetween(
        "POI110", 
        startTime, 
        endTime
    );

// 대기질이 나쁜 지역 조회
List<WeatherStatus> badAir = weatherStatusRepository
    .findByAirIndexIn(
        Arrays.asList("나쁨", "매우나쁨")
    );
```

## 데이터 특성

- **업데이트 빈도**: 높음 (실시간)
- **데이터 타입**: 동적 데이터 (시간에 따라 변경)
- **보관 기간**: 시간별 데이터가 계속 쌓이므로 주기적 정리 필요

