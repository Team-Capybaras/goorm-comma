# Backend 프로젝트

Spring Boot 기반 백엔드 애플리케이션입니다.

## 📊 실시간 로그 모니터링 시스템 (Alloy + Loki + Grafana)

이 프로젝트는 **Alloy**, **Loki**, **Grafana**를 사용하여 실시간으로 로그를 수집하고 시각화합니다.

### 시스템 아키텍처

```
Spring Boot 애플리케이션 (로그 생성)
    ↓
Alloy (로그 수집 에이전트)
    ↓
Loki (로그 집계 시스템)
    ↓
Grafana (로그 시각화)
```

### 구성 요소

- **Alloy**: Docker 컨테이너 로그를 수집하여 Loki로 전송하는 에이전트
- **Loki**: 로그를 효율적으로 저장하고 인덱싱하는 로그 집계 시스템
- **Grafana**: 로그를 시각화하고 대시보드를 제공하는 도구

### 실행 방법

#### 1. 모니터링 시스템 시작

프로젝트 루트 디렉토리에서 실행: 백엔드 서버 실행

```bash
./dev.sh back build
docker-compose -f docker-compose.backend-dev.yml up -d
```

또는 특정 서비스만 시작:

```bash
# Loki 시작
docker-compose -f docker-compose.backend-dev.yml up -d loki

# Grafana 시작
docker-compose -f docker-compose.backend-dev.yml up -d grafana

# Alloy 시작
docker-compose -f docker-compose.backend-dev.yml up -d alloy
```

#### 2. 서비스 접속 정보

- **Grafana**: http://localhost:3001
  - 사용자명: `admin`
  - 비밀번호: `admin`
- **Loki API**: http://localhost:3100
- **Alloy UI**: http://localhost:12345

### Grafana에서 로그 확인하기

#### 1. 기본 사용법

1. **Grafana 접속**
   - 브라우저에서 http://localhost:3001 접속
   - 로그인: `admin` / `admin`

2. **Explore 메뉴 이동**
   - 왼쪽 사이드바에서 **Explore** (나침반 아이콘) 클릭

3. **데이터소스 선택**
   - 상단 드롭다운에서 **Loki** 선택 (자동으로 설정되어 있을 거임)

4. **로그 쿼리 실행**
   - 오른쪽에 작게 써있는 code 눌러서 직접 작성
   - 쿼리 입력창에 다음 중 하나 입력:
   ```
   {job="backend"}
   ```
   또는
   ```
   {job="docker"}
   ```
   - **Run query** 버튼 클릭 or shift + enter

5. **실시간 로그 확인**
   - 우측 상단의 **Live** 버튼을 활성화하면 실시간으로 로그가 스트리밍됩니다

#### 2. LogQL 쿼리 예시

**모든 백엔드 로그**
```
{job="backend"}
```

**에러 로그만 필터링**
```
{job="backend"} |= "ERROR"
```

**특정 컨테이너 로그**
```
{container="backend"}
```

**정규식으로 필터링**
```
{job="backend"} |~ "Exception|Error"
```

**로그 라인 수 제한**
```
{job="backend"} | limit 100
```

**시간 범위 지정**
```
{job="backend"} [5m]
```

### 설정 파일

- **Alloy 설정**: `backend/monitoring/alloy/config.alloy`
  - Docker 컨테이너 로그 수집 설정
  - Loki 전송 설정

- **Grafana 프로비저닝**: `backend/monitoring/grafana/provisioning/`
  - Loki 데이터소스 자동 설정
  - 대시보드 프로비저닝 설정

### 수집되는 로그

현재 다음 컨테이너의 로그가 수집됩니다:
- `backend`: Spring Boot 애플리케이션 로그
- `db`: PostgreSQL 데이터베이스 로그
- `redis`: Redis 로그
- `frontend`: Frontend 애플리케이션 로그
- `nginx`: Nginx 웹 서버 로그

### 문제 해결

#### 로그가 보이지 않는 경우

1. **컨테이너 상태 확인**
   ```bash
   docker ps --filter "name=alloy" --filter "name=loki" --filter "name=grafana"
   ```
   모든 컨테이너가 `Up` 상태여야 합니다.

2. **Alloy 로그 확인**
   ```bash
   docker logs alloy
   ```
   에러 메시지가 있는지 확인합니다.

3. **Loki 연결 확인**
   ```bash
   curl http://localhost:3100/ready
   ```
   `ready`가 반환되면 정상입니다.

4. **Loki에 수집된 레이블 확인**
   ```bash
   curl http://localhost:3100/loki/api/v1/label/job/values
   ```
   수집된 job 레이블 목록을 확인할 수 있습니다.

5. **로그 수집 대기 시간**
   - Alloy가 컨테이너를 발견하고 로그를 수집하는 데 몇 분이 걸릴 수 있습니다.
   - Backend API를 호출하여 로그를 생성한 후 1-2분 정도 기다려보세요.
   ```bash
   curl http://localhost:8080/api/swagger-ui.html
   ```

6. **Grafana에서 직접 확인**
   - Grafana Explore에서 `{job=~".+"}` 쿼리로 모든 job의 로그를 확인할 수 있습니다.
   - Live 모드를 활성화하면 실시간으로 로그가 나타나는지 확인할 수 있습니다.

#### Grafana에서 Loki 데이터소스가 보이지 않는 경우

1. **Grafana 컨테이너 재시작**
   ```bash
   docker restart grafana
   ```

2. **프로비저닝 파일 확인**
   - `backend/monitoring/grafana/provisioning/datasources/loki.yml` 파일이 올바르게 마운트되었는지 확인

#### Alloy가 로그를 수집하지 않는 경우

1. **Docker 소켓 권한 확인**
   ```bash
   docker exec alloy ls -la /var/run/docker.sock
   ```
   소켓 파일이 존재하고 접근 가능해야 합니다.

2. **설정 파일 확인**
   ```bash
   docker exec alloy cat /etc/alloy/config.alloy
   ```
   설정이 올바른지 확인합니다.

3. **Alloy 재시작**
   ```bash
   docker restart alloy
   ```
   설정 변경 후 재시작이 필요합니다.

4. **컨테이너 로그 파일 확인**
   ```bash
   docker inspect backend --format '{{.LogPath}}'
   ```
   로그 파일 경로가 올바른지 확인합니다.

5. **수동 테스트**
   - Backend API를 여러 번 호출하여 로그를 생성합니다.
   - 1-2분 후 Grafana에서 확인합니다.
   ```bash
   # 여러 번 API 호출
   for i in {1..5}; do curl -s http://localhost:8080/api/swagger-ui.html > /dev/null; sleep 2; done
   ```

### 참고 자료

- [Grafana Loki 공식 문서](https://grafana.com/docs/loki/latest/)
- [Grafana Alloy 공식 문서](https://grafana.com/docs/alloy/latest/)
- [Grafana 공식 문서](https://grafana.com/docs/grafana/latest/)
- [LogQL 쿼리 언어](https://grafana.com/docs/loki/latest/logql/)

### 추가 정보

자세한 설정 및 사용법은 `backend/monitoring/README.md`를 참고하세요.
