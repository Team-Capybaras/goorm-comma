# 로그 모니터링 시스템 사용 가이드

## 개요
이 시스템은 Alloy, Loki, Grafana를 사용하여 실시간 로그 모니터링을 제공합니다.

## 서비스 접속 정보

### Grafana

#### 로컬 환경
- URL: http://localhost:3001
- 사용자명: `admin`
- 비밀번호: `admin`

#### AWS 프로덕션 환경

Grafana는 nginx를 통해 접속할 수 있습니다.

**방법 1: nginx 리버스 프록시 (권장)**

- **URL**: `https://parkybara.deving.xyz/grafana/`
- **사용자명**: `admin`
- **비밀번호**: `admin`
- **장점**: 
  - HTTPS로 안전하게 접속
  - 포트 노출 불필요
  - 보안 그룹 설정 불필요
  - 도메인을 통한 접속 가능

**설정 방법:**

1. `.env.prod` 파일에 다음 내용 추가:
   ```bash
   GF_SERVER_ROOT_URL=https://parkybara.deving.xyz/grafana/
   ```

2. AWS 서버에서 컨테이너 재시작:
   ```bash
   # Grafana 컨테이너 재시작 (환경 변수 적용)
   docker-compose -f docker-compose.prod.yml up -d --force-recreate grafana
   
   # nginx 재시작 (설정 적용)
   docker-compose -f docker-compose.prod.yml restart nginx
   ```

3. 접속 테스트:
   - 브라우저에서 `https://parkybara.deving.xyz/grafana/` 접속
   - admin/admin으로 로그인

**방법 2: 직접 포트 접속**

- **URL**: `http://AWS_EC2_IP:3001`
- **사용자명**: `admin`
- **비밀번호**: `admin`
- **주의**: 
  - AWS 보안 그룹에서 3001 포트를 열어야 합니다
  - HTTP만 가능 (HTTPS는 별도 설정 필요)
  - 보안상 권장하지 않음

### Loki
- 로컬: http://localhost:3100
- AWS: 내부 네트워크에서만 접근 가능 (외부 노출 안 됨)

### Alloy UI
- 로컬: http://localhost:12345
- AWS: 내부 네트워크에서만 접근 가능 (외부 노출 안 됨)

## 실시간 로그 모니터링 확인 방법 (검증 완료)

### 전체 프로세스 확인

Backend 터미널에 찍힌 로그가 Grafana에서 실시간으로 보이는지 확인하는 방법입니다.

#### 1. 서버 실행

```bash
# Backend 서버 실행
./dev.sh back build

# Frontend 서버 실행
./dev.sh front build

# 로컬 배포 시
./local.sh deploy

# 로컬 배포 종료
./local.sh down    
```

#### 2. API 호출 및 로그 확인

**터미널에서 또는 스웨거 or Postman or URL을 통해서:**
```bash
# API 호출
curl http://localhost:8080/api/v1/parks/all
```

**Backend 터미널 로그 확인:**
```bash
# Backend 컨테이너 로그 확인
docker logs backend --tail 20 | grep "ParkServiceImpl"
```

예상 출력:
```
backend | 2026-01-26 02:15:15.223 [http-nio-0.0.0.0-8080-exec-7] INFO  g.b.d.p.service.impl.ParkServiceImpl - 전체 공원 기본 정보 조회 시작
backend | 2026-01-26 02:15:15.237 [http-nio-0.0.0.0-8080-exec-7] INFO  g.b.d.p.service.impl.ParkServiceImpl - 조회된 공원 수: 34
backend | 2026-01-26 02:15:15.239 [http-nio-0.0.0.0-8080-exec-7] INFO  g.b.d.p.service.impl.ParkServiceImpl - 전체 공원 기본 정보 조회 완료 - 조회된 공원 수: 34
```

#### 3. Grafana에서 실시간 확인

**방법 A: Explore 사용 (추천)**

1. **Grafana 접속**
   - http://localhost:3001 접속
   - 로그인: admin / admin

2. **Explore 메뉴 클릭**
   - 좌측 메뉴에서 "Explore" (돋보기 아이콘) 클릭

3. **데이터소스 선택**
   - 상단에서 "Loki" 선택

4. **쿼리 입력**
   ```
   {job="backend"} |= "ParkServiceImpl" |= "조회된 공원 수"
   ```
   또는 더 넓게:
   ```
   {job="backend"} |= "ParkServiceImpl"
   ```

5. **시간 범위 설정**
   - 우측 상단에서 "Last 1 minute" 또는 "Last 2 minutes" 선택
   - ⚠️ **로그가 많을 때는 최근 1-2분으로 좁게 설정**

6. **Live 모드 활성화**
   - 우측 상단의 "Live" 버튼 클릭 (실시간 업데이트)

7. **API 호출**
   ```bash
   curl http://localhost:8080/api/v1/parks/all
   ```

8. **결과 확인**
   - 몇 초 내에 Grafana에 로그가 나타납니다
   - Backend 터미널에 찍힌 로그와 동일한 내용이 표시됩니다
   - Live 모드이므로 자동으로 업데이트됩니다

**방법 B: 대시보드 사용**

1. **대시보드 접속**
   - 로컬: http://localhost:3001 접속
   - AWS: https://parkybara.deving.xyz/grafana/ 접속
   - 좌측 메뉴 → "Dashboards" 클릭
   - "Spring Boot Application Logs" 대시보드 선택

2. **API Request Logs 패널 확인**
   - "API Request Logs (ParkServiceImpl)" 패널에서 확인
   - 시간 범위를 최근 1-2분으로 설정

3. **API 호출**
   ```bash
   curl http://localhost:8080/api/v1/parks/all
   ```

4. **결과 확인**
   - 패널에 로그가 실시간으로 표시됩니다

#### 4. 검증 체크리스트

✅ **Backend 터미널에 로그가 찍힘**
```bash
docker logs backend --tail 20 | grep "ParkServiceImpl"
```

✅ **로그 파일에 기록됨**
```bash
docker exec backend tail -5 /app/logs/application.log | grep "ParkServiceImpl"
```

✅ **Alloy가 로그 파일을 읽음**
```bash
docker exec alloy tail -5 /app/logs/application.log | grep "ParkServiceImpl"
```

✅ **Loki에 수집됨**
```bash
curl -G "http://localhost:3100/loki/api/v1/query_range" \
  --data-urlencode 'query={job="backend"} |= "ParkServiceImpl"' \
  --data-urlencode 'limit=5'
```

✅ **Grafana에서 확인 가능**
- Explore 또는 대시보드에서 위 쿼리 실행 시 로그 표시

#### 5. 문제 해결

**로그가 Grafana에 나타나지 않을 때:**

1. **시간 범위 확인**
   - 시간 범위를 최근 1-2분으로 좁게 설정
   - Live 모드 활성화 확인

2. **Alloy 상태 확인**
   ```bash
   docker logs alloy | tail -20
   ```
   - 에러가 없어야 함
   - `tail routine: started` 메시지가 있어야 함

3. **볼륨 마운트 확인**
   ```bash
   # Backend와 Alloy가 같은 볼륨을 사용하는지 확인
   docker exec backend ls -la /app/logs/
   docker exec alloy ls -la /app/logs/
   ```
   - 두 컨테이너 모두 `/app/logs/application.log` 파일이 보여야 함

4. **로그 파일 동기화 확인**
   ```bash
   # Backend에서 로그 확인
   docker exec backend tail -1 /app/logs/application.log
   
   # Alloy에서 같은 로그 확인 (같아야 함)
   docker exec alloy tail -1 /app/logs/application.log
   ```
   - 두 명령어의 결과가 동일해야 함

5. **Alloy 재시작**
   ```bash
   docker restart alloy
   ```
   - 볼륨 동기화 문제가 있을 때 재시작

6. **Backend 재시작**
   ```bash
   docker restart backend
   ```
   - 볼륨 마운트가 제대로 되지 않았을 때 재시작

7. **Loki 직접 확인**
   ```bash
   curl -G "http://localhost:3100/loki/api/v1/label/job/values"
   ```
   - `backend`가 포함되어 있어야 함

#### 6. 실시간 모니터링 워크플로우

```
1. API 호출 (curl)
   ↓
2. Backend 애플리케이션 로그 생성
   ↓
3. /app/logs/application.log 파일에 기록
   ↓
4. Alloy가 파일을 tail하여 읽음
   ↓
5. Alloy가 Loki로 로그 전송
   ↓
6. Loki에 로그 저장 및 인덱싱
   ↓
7. Grafana가 Loki에서 로그 조회
   ↓
8. Grafana 대시보드/Explore에 표시
```

**전체 소요 시간: 약 5-10초**

- Backend 로그 생성: 즉시
- 파일 기록: 즉시
- Alloy 수집: 1-2초
- Loki 저장: 1-2초
- Grafana 표시: 2-5초

#### 7. 실전 사용 예시

**시나리오: API 호출 후 로그 확인**

```bash
# 1. Grafana Explore 준비
# - http://localhost:3001 → Explore
# - 쿼리: {job="backend"} |= "ParkServiceImpl"
# - 시간: Last 1 minute
# - Live 모드 활성화

# 2. API 호출
curl http://localhost:8080/api/v1/parks/all

# 3. Grafana에서 확인 (5-10초 내)
# - "조회된 공원 수: 34" 로그가 나타남
# - Backend 터미널 로그와 동일한 내용
```

**시나리오: 에러 로그 모니터링**

```bash
# 1. Grafana Explore 준비
# - 쿼리: {job="backend"} |~ "ERROR|Exception"
# - 시간: Last 5 minutes
# - Live 모드 활성화

# 2. 에러 발생 API 호출 (예시)
curl http://localhost:8080/api/v1/parks/invalid

# 3. Grafana에서 에러 로그 확인
```

이 방법으로 Backend 터미널에 찍힌 모든 로그를 Grafana에서 실시간으로 확인할 수 있습니다.

---

## 검증 완료: 실시간 로그 모니터링 동작 확인

### 테스트 결과

**테스트 환경:**
- Backend: Spring Boot 애플리케이션
- Alloy: 로그 수집 에이전트
- Loki: 로그 저장소
- Grafana: 로그 시각화

**테스트 절차:**
1. `curl http://localhost:8080/api/v1/parks/all` API 호출
2. Backend 터미널에서 로그 확인
3. Grafana에서 동일한 로그 확인

**확인된 로그:**
```
Backend 터미널:
2026-01-26 02:15:15.223 [http-nio-0.0.0.0-8080-exec-7] INFO  g.b.d.p.service.impl.ParkServiceImpl - 전체 공원 기본 정보 조회 시작
2026-01-26 02:15:15.237 [http-nio-0.0.0.0-8080-exec-7] INFO  g.b.d.p.service.impl.ParkServiceImpl - 조회된 공원 수: 34
2026-01-26 02:15:15.239 [http-nio-0.0.0.0-8080-exec-7] INFO  g.b.d.p.service.impl.ParkServiceImpl - 전체 공원 기본 정보 조회 완료 - 조회된 공원 수: 34
```

**Grafana에서 확인:**
- Explore에서 쿼리 `{job="backend"} |= "ParkServiceImpl" |= "조회된 공원 수"` 실행 시
- 위 Backend 터미널 로그와 동일한 내용이 표시됨
- 실시간으로 수집되어 5-10초 내에 Grafana에 나타남

### 시스템 아키텍처

```
┌─────────────┐
│   Backend   │ → /app/logs/application.log (공유 볼륨)
└──────┬──────┘
       │
       │ (Docker Volume: app_logs)
       │
┌──────▼──────┐
│    Alloy    │ → tail /app/logs/application.log
└──────┬──────┘
       │
       │ (HTTP POST)
       │
┌──────▼──────┐
│    Loki     │ → 로그 저장 및 인덱싱
└──────┬──────┘
       │
       │ (LogQL Query)
       │
┌──────▼──────┐
│   Grafana   │ → 로그 시각화 및 대시보드
└─────────────┘
```

### 핵심 포인트

1. **공유 볼륨 사용**: Backend와 Alloy가 같은 Docker 볼륨(`app_logs`)을 사용하여 로그 파일 공유
2. **실시간 수집**: Alloy가 파일을 tail하여 새 로그를 실시간으로 수집
3. **빠른 전파**: Backend 로그 생성 → Loki 저장 → Grafana 표시까지 약 5-10초
4. **동일한 내용**: Backend 터미널 로그와 Grafana 로그가 100% 동일

### 주의사항

- Alloy가 재시작되면 tail이 처음부터 시작할 수 있으므로, 최신 로그만 확인하려면 시간 범위를 좁게 설정
- 로그 파일이 매우 클 경우, Grafana에서 시간 범위를 최근 1-2분으로 설정하는 것을 권장
- Live 모드를 활성화하면 실시간으로 새 로그가 자동 업데이트됨

## 특정 API 호출 로그 확인 방법

로그가 많아서 특정 API 호출에 대한 로그만 보고 싶을 때 사용하는 방법입니다.

### 1. 기본 방법 (추천) - 로그가 많을 때

**Grafana에서:**
1. Explore 메뉴 클릭
2. 데이터소스: Loki 선택
3. 쿼리 입력: `{job="backend"} |= "ParkServiceImpl" |= "조회된 공원 수"`
   - 또는: `{job="backend"} |= "ParkServiceImpl" |~ "전체 공원"`
4. 시간 범위: **최근 1분** 또는 **최근 2분**으로 설정 (매우 좁게!)
5. **Live 버튼 활성화** (우측 상단) - 필수!
6. Run query 클릭

**터미널에서:**
```bash
curl http://localhost:8080/api/v1/parks/all
```

**결과:**
- 몇 초 내에 Grafana에 해당 API 호출 관련 로그만 표시됩니다
- Live 모드이므로 실시간으로 업데이트됩니다
- 시간 범위를 좁게 설정하면 불필요한 로그가 필터링됩니다

### 2. 다양한 필터 쿼리 (로그가 많을 때 사용)

#### 가장 정확한 필터 (추천)
```
{job="backend"} |= "ParkServiceImpl" |= "조회된 공원 수"
```
- ParkServiceImpl 클래스이면서 "조회된 공원 수" 메시지가 포함된 로그만

#### ParkServiceImpl 클래스 로그만 보기
```
{job="backend"} |= "ParkServiceImpl"
```
- ⚠️ 로그가 많으면 시간 범위를 최근 1-2분으로 좁게 설정

#### URL 기반 필터
```
{job="backend"} |= "parks/all"
```

#### 특정 메시지가 포함된 로그
```
{job="backend"} |~ "조회된 공원 수"
```

#### 여러 조건 조합 (더 정확하게)
```
{job="backend"} |= "ParkServiceImpl" |~ "전체 공원"
```
- ParkServiceImpl이면서 "전체 공원"이 포함된 로그만

### 3. 쿼리 문법 설명

- `{job="backend"}`: Backend 서비스의 로그만 선택
- `|= "문자열"`: 로그 내용에 해당 문자열이 포함된 경우만 선택 (대소문자 구분)
- `|~ "정규식"`: 정규식 패턴과 일치하는 로그만 선택
- `!= "문자열"`: 해당 문자열이 포함되지 않은 로그만 선택

### 4. 실시간 로그 확인 절차 (로그가 많을 때)

1. **Grafana 준비**
   - http://localhost:3001 접속
   - Explore → Loki 데이터소스 선택
   - 쿼리 입력: `{job="backend"} |= "ParkServiceImpl" |= "조회된 공원 수"`
   - ⚠️ **시간 범위: 최근 1분 또는 최근 2분** (매우 좁게!)
   - **Live 버튼 활성화** (필수!)
   - Run query 클릭

2. **API 호출**
   ```bash
   curl http://localhost:8080/api/v1/parks/all
   ```

3. **로그 확인**
   - 몇 초 내에 Grafana에 로그가 나타납니다
   - Live 모드이므로 자동으로 업데이트됩니다
   - 시간 범위가 좁아서 불필요한 로그가 필터링됩니다

### 5. 시간 범위 설정 팁 (로그가 많을 때)

- **최근 1분**: ⭐ 로그가 많을 때 추천! 특정 API 호출 로그만 확인
- **최근 2분**: 최근 활동만 확인하고 싶을 때
- **최근 5분**: 일반적인 실시간 모니터링
- **최근 15분**: 최근 활동 확인
- **최근 1시간**: 더 넓은 범위의 로그 확인 (로그가 많으면 부적합)
- **Live 모드**: ⭐ 필수! 실시간으로 새 로그가 자동 표시됨

### 6. 문제 해결

#### 로그가 표시되지 않을 때

1. **시간 범위 확인**
   - 로그가 많으면: 시간 범위를 **매우 좁게** 설정 (최근 1-2분)
   - 로그가 적으면: 시간 범위를 넓게 설정 (예: 최근 1시간)
   - Live 모드 활성화 (필수!)

2. **쿼리 확인**
   - 기본 쿼리로 먼저 확인: `{job="backend"}`
   - 필터를 점진적으로 추가
   - 여러 필터 조합: `{job="backend"} |= "ParkServiceImpl" |= "조회된 공원 수"`

#### 로그가 너무 많아서 안 보일 때

1. **시간 범위를 최근 1분으로 설정**
2. **더 구체적인 필터 사용**
   ```
   {job="backend"} |= "ParkServiceImpl" |= "조회된 공원 수"
   ```
3. **Live 모드만 사용**
   - Live 버튼 활성화
   - API 호출 후 실시간으로만 확인
4. **특정 시간대만 확인**
   - 시간 범위를 수동으로 설정 (예: 14:40:00 ~ 14:41:00)

## 전체 로그 확인

모든 로그를 확인하려면:
```
{job="backend"}
```

시간 범위를 넓게 설정하면 더 많은 로그를 볼 수 있습니다.

## Grafana 대시보드 사용 방법

### Spring Boot 로그 대시보드

Grafana에 Spring Boot 애플리케이션 로그를 위한 대시보드가 자동으로 로드됩니다.

**대시보드 접속:**
1. Grafana 접속
   - 로컬: http://localhost:3001
   - AWS: https://parkybara.deving.xyz/grafana/
   - 로그인: admin/admin
2. 좌측 메뉴에서 **Dashboards** 클릭
3. **Spring Boot Application Logs** 대시보드 선택

**대시보드 패널:**
- **Log Volume Over Time**: 시간별 로그 볼륨 그래프
- **Log Level Distribution**: 로그 레벨별 분포 (INFO, ERROR, WARN, DEBUG)
- **Recent Logs**: 최근 로그 전체 보기
- **API Request Logs (ParkServiceImpl)**: ParkServiceImpl 관련 로그만 필터링
- **Error Logs**: 에러 로그만 필터링

### 특정 API 호출 로그 확인 (대시보드 사용)

1. **대시보드에서 확인**
   - Spring Boot Application Logs 대시보드 열기
   - "API Request Logs (ParkServiceImpl)" 패널 확인
   - 시간 범위를 최근 1-5분으로 설정
   - 새로고침 버튼 클릭 또는 자동 새로고침 활성화

2. **API 호출**
   ```bash
   curl http://localhost:8080/api/v1/parks/all
   ```

3. **로그 확인**
   - 몇 초 내에 "API Request Logs (ParkServiceImpl)" 패널에 로그가 표시됩니다
   - 로그가 잘리지 않고 전체 내용이 표시됩니다

### Explore에서 직접 확인

대시보드 대신 Explore에서 직접 확인하려면:

1. **Explore 메뉴** 클릭
2. **데이터소스**: Loki 선택
3. **쿼리 입력**: `{job="backend"} |= "ParkServiceImpl" |= "조회된 공원 수"`
4. **시간 범위**: 최근 1분
5. **Live 버튼** 활성화
6. **Run query** 클릭

## 참고사항

- 로그는 Backend에서 생성되면 몇 초 내에 Loki에 수집됩니다
- Live 모드를 활성화하면 실시간으로 업데이트됩니다
- 시간 범위를 좁게 설정하면 특정 시점의 로그만 볼 수 있습니다
- Grafana 대시보드를 사용하면 여러 패널에서 로그를 한눈에 볼 수 있습니다
