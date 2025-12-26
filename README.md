# 개발 환경 가이드

> Docker + Spring Boot + Next.js 기반 통합 개발환경  
> 백엔드는 로컬 실행, DB/Redis/Nginx/Next.js는 Docker로 구성

---

## 1️⃣ 아키텍처 개요

프로젝트는 다음과 같은 구조로 구성되어 있습니다:
<img width="650" height="567" alt="스크린샷 2025-10-29 오후 5 06 33" src="https://github.com/user-attachments/assets/1ece32a2-8ce7-43eb-860f-88feb516dcc2" />

- **브라우저**: 사용자 요청
- **Nginx (Docker)**: 리버스 프록시 역할
    - `/api/` 요청 → 로컬 Spring Boot 백엔드
    - 그 외 요청 → Next.js 프론트엔드
- **Spring Boot (로컬)**: API 서버, 포트 8080, 컨텍스트 경로 `/api`
- **Docker Compose 서비스**:
    - PostgreSQL 데이터베이스 (포트 5432)
    - Redis 캐시 서버 (포트 6379)
    - Next.js 프론트엔드 (포트 3000)
    - Nginx (포트 80)

---

## 2️⃣ 프로젝트 구조

- `backend/` : Spring Boot 서버 (로컬 실행)
- `frontend/` : Next.js 프론트엔드 (Docker로 실행)
- `nginx/` : Nginx 설정
- `docker-compose.yml` : DB, Redis, Nginx, Next.js 정의
- `.env.dev` : 개발 환경 변수
- `README.md` : 문서

---

## 3️⃣ Docker 서비스 구성

| 서비스   | 이미지/포트        | 설명 |
|----------|-----------------|------|
| db       | postgres:15 / 5432 | PostgreSQL 데이터베이스 |
| redis    | redis:7 / 6379     | 캐시/세션 저장 |
| frontend | node:18 / 3000     | Next.js 개발 서버 |
| nginx    | nginx:stable / 80  | 리버스 프록시 (프론트/백엔드 연결) |

---

## 4️⃣ 환경 변수 (.env.dev)

개발 환경에서 사용하는 데이터베이스 계정과 비밀번호, DB 이름의 정의

---

## 5️⃣ Spring Boot 설정

- `application-dev.yml`에서 데이터베이스와 Redis 연결 설정, JPA 설정, 서버 포트와 컨텍스트 경로를 정의
- 로컬에서 백엔드 서버를 실행할 때 활성화할 개발 프로파일 `dev` 설정

---

## 6️⃣ 개발용 Security 설정

> 현재 Security 환경 설정은 보류된 상황
> 기획에 따라 개발 스택이 변동될 예정

---

## 7️⃣ 예제 API 구성

- `/api/v1/locations` 등 기본 API 엔드포인트를 정의
- Spring Boot 컨트롤러에서 데이터를 처리하고 JSON으로 반환

---

## 8️⃣ Next.js 프론트엔드 구성

- 클라이언트에서 API 요청을 보내고 데이터를 화면에 표시
- React Hooks(`useEffect`, `useState`)를 사용
- Nginx를 통해 백엔드 API로 요청 가능

---

## 9️⃣ Nginx 리버스 프록시

- 프론트엔드와 백엔드 서버를 하나의 도메인과 포트로 통합
- 요청 경로에 따라 Next.js 또는 Spring Boot로 라우팅
- 클라이언트 요청 호스트와 실제 IP를 백엔드로 전달

---

## 🔧 개발용 실행 순서

1. manually start
```shell
# build : 처음 빌드 시, PR 후 변동사항 적용 시 실행
docker compose -f "$COMPOSE_FILE" --env-file "$ENV_FILE" up --build
# detach : 빌드 이후 컨테이너가 종료 된 상태에서 다시 키는 경우 재빌드 없이 백그라운드 실행. (더 빠름)
# 백그라운드 실행 옵션 없이 실행하려면 -d 제거
docker compose -f "$COMPOSE_FILE" --env-file "$ENV_FILE" up -d
# stop : 컨테이너 멈춤 (현재 동작중인 컨테이너 일시정지)
docker compose -f "$COMPOSE_FILE" --env-file "$ENV_FILE" stop
# down : 컨테이너 삭제 (현재 빌드된 내용 제거, 이후 컨테이너 실행 시 빌드 적용)
docker compose -f "$COMPOSE_FILE" --env-file "$ENV_FILE" down
```

2. shell script (auto)

개발 환경
windows에서는 `/` 대신 `\` 사용이 필요할 수 있음.
```shell
# front-end 환경, back-end 환경 별 실행
./dev.sh {front|back} {build|up|detach|down}
```

배포 환경
--no-cache 옵션을 통해 변동사항 등 캐시 제거 및 재빌드
```shell
./prod.sh {build|restart|deploy|down} [--no-cache]
```