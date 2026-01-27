
# Take idle time with Parkybara

<a xlink:href="https://parkybara.deving.xyz" target="_blank">
<svg width="130" height="130" viewBox="0 0 130 130" fill="none" xmlns="http://www.w3.org/2000/svg">
<g clip-path="url(#clip0_1_280)">
<rect x="1.26999" y="1.26999" width="127" height="127" rx="22.4779" fill="white"/>
<circle cx="15.3187" cy="38.9208" r="54.5088" fill="#2CA607"/>
<path fill-rule="evenodd" clip-rule="evenodd" d="M100.721 48.4621C106.116 47.7373 108.458 52.9751 109.161 56.6297C110.42 56.5707 111.701 56.5465 113.004 56.5574C116.698 56.5883 120.14 56.8569 123.343 57.3363C124.616 52.9089 127.769 47.6132 133.766 49.8286C139.803 52.059 138.997 57.5793 137.283 61.3086C154.317 68.9874 160.719 84.7765 160.594 99.7453C160.488 112.388 152.374 121.486 140.026 126.595L139.972 133.069L81.8537 132.583L81.914 125.376C69.6295 119.956 61.2262 110.986 61.3272 98.9144C61.4658 82.3584 73.697 64.9665 95.2682 58.8465C94.7919 54.9647 95.3909 49.178 100.721 48.4621Z" fill="#6ECD42"/>
<path d="M15.3187 52.9691L15.3187 133.889" stroke="#117600" stroke-width="5.61947" stroke-linecap="round"/>
<path d="M15.3187 75.4471L31.4746 58.4482M45.6638 43.4161L31.4746 58.4482M31.4746 58.4482L45.6638 60.8365" stroke="#117600" stroke-width="5.61947" stroke-linecap="round"/>
<path d="M101.016 80.2234L109.726 82.4712" stroke="#1A6404" stroke-width="5.61947" stroke-linecap="round"/>
<path d="M78.2567 93.4293C78.2567 93.4293 75.3744 97.5281 72.7394 97.3578C70.3188 97.2013 68.1417 95.0469 68.1417 95.0469" stroke="#1A6404" stroke-width="5.61947" stroke-linecap="round"/>
<path d="M73.1992 103.544L73.1992 97.3629" stroke="#1A6404" stroke-width="5.61947" stroke-linecap="round"/>
</g>
<rect x="0.634989" y="0.634989" width="128.27" height="128.27" rx="23.1129" stroke="#E8E8E8" stroke-width="1.27"/>
<defs>
<clipPath id="clip0_1_280">
<rect x="1.26999" y="1.26999" width="127" height="127" rx="22.4779" fill="white"/>
</clipPath>
</defs>
</svg>
</a>

> Parkybara는 조용한 공원에서 쉬고싶은 현대인을 위해 만들어진 프로젝트입니다.  
> Parkybara와 함께 평화를 누리세요.

---

## 개요

### 프로젝트 목적
본 프로젝트의 목적은 공원 방문을 ‘휴식 중심의 의사결정’으로 재정의하고, 사용자가 외출 전에 느끼는 불확실성과 탐색 피로를 최소화하여 실패 없는 휴식 경험을 제공하는 것이다.  
이를 위해 파편화된 공공데이터(실시간 유동인구, 날씨 등)를 통합·가공하여 사용자가 언제, 어디로 가면 가장 여유롭게 쉴 수 있는지를 직관적으로 판단할 수 있도록 돕는다.  
궁극적으로는 공원 방문 실패 경험 감소, 탐색 시간 및 정신적 에너지 절감, 대체지(Plan B) 확보를 통한 시간 효율성 향상을 통해 휴식 만족도를 구조적으로 개선하는 것을 목표로 한다.

### 해결하려는 문제

1. 휴식 목적의 공원 방문에서 발생하는 정보 불확실성

사용자는 공원을 ‘휴식’을 위해 방문하지만, 사전에 혼잡도를 정확히 예측하지 못해 예상과 다른 혼잡한 현장을 마주치는 경우가 빈번하다.  
이로 인해 소음, 대기, 이동 스트레스가 발생하며 휴식 만족도가 크게 저하된다.

2. 혼잡도 예측을 위한 과도한 탐색 비용

현재 사용자는 블로그, SNS 후기 및 포털 지도 서비스의 과거 방문 통계와 같은 정적이고 파편적인 정보에 의존하고 있다.  
이 과정에서 여러 앱을 오가며 정보를 비교해야 하고 실시간 변수(날씨, 행사, 시간대 변화)를 반영하지 못해 의사결정 과정 자체가 피로한 탐색 행위로 전락한다.

3. 실시간 환경 변수를 ‘휴식 관점’으로 통합한 서비스의 부재

실시간 유동인구, 기상 데이터 등은 이미 존재하지만 서로 분산되어 있고 사용자의 ‘휴식 의사결정’이라는 맥락으로 재가공되지 않아 실질적인 판단 도구로 기능하지 못한다.  
즉, 데이터는 존재하지만 ‘휴식 만족도’를 기준으로 통합·해석해주는 서비스가 없는 것이 근원 문제이다.


### 코어 기능

Core 1. 혼잡도 지도 및 통합 정보 제공  
리스트 또는 지도로 공원을 탐색하여 혼잡도와 핵심 정보를 비교

Core 2. 데이터 기반 추천 및 예측  
선택한 공원의 혼잡시간 회피 및 맞춤형 대안 제안


---

## 아키텍처 개요

프로젝트는 다음과 같은 구조로 구성되어 있습니다:

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

### 프로젝트 구조

- `backend/` : Spring Boot 서버 (로컬 실행)
- `frontend/` : Next.js 프론트엔드 (Docker로 실행)
- `nginx/` : Nginx 설정
- `docker-compose.yml` : DB, Redis, Nginx, Next.js 등의 정의
- `.env.dev` : 개발 환경 변수
- `.env.prod` : 배포 환경 변수
- `README.md` : 문서

---

### Docker 서비스 구성

| 서비스      | 이미지/포트                | 설명                    |
|----------|-----------------------|-----------------------|
| db       | postgres:15 / 5432    | PostgreSQL 데이터베이스     |
| redis    | redis:7 / 6379        | 캐시/세션 저장              |
| frontend | node:18 / 3000        | Next.js 개발 서버         |
| backend  | java:21 / 8080        | springboot 개발 서버      |
| nginx    | nginx:stable / 80     | 리버스 프록시 (프론트/백엔드 연결)  |
| alloy    | alloy:latest / 12345  | spring boot 서버 로그 수집기 |
| loki     | loki:latest / 3100    | 로그 모니터링               |
| grafana  | grafana:latest / 3001 | 로그 가시화                |

---

#### 패키지 구조

frontend
```

frontend
|- asdf
|- some component

```

backend
```
com.example.project
│
├─ application
│   └─ product
│       ├─ mapper
│       ├─ service
│       │   ├─ spec
│       │   └─ Impl
│       ├─ controller
│       └─ dto
│
├─ domain
│   └─ product
│       ├─ mapper
│       ├─ entity
│       ├─ repository
│       ├─ service
│       │   ├─ spec
│       │   └─ Impl
│       ├─ controller
│       └─ dto
│
├─ common
│   ├─ response
│   ├─ exception
│   ├─ util
│   └─ config
│
├─ interfaces
│
└─ BackendApplication.java
```

---

### 기술 스택

| 구분 | 사용 기술 및 도구 |
|----|------------|
| FE |            |
| BE |            |

---



## API 명세

---


## 데이터 흐름 및 예상 시나리오

---


## 테스트

---


## 모니터링 및 로깅


---


## 개발 환경 가이드

> 여기서는 개발 환경 및 기술 스택에 대해 설명합니다.  
> Docker + Spring Boot + Next.js 기반 통합 개발환경  
> 백엔드는 로컬 실행, DB/Redis/Nginx/Next.js는 Docker로 구성

---

#### 4️⃣ 환경 변수 (.env.dev 및 .env.prod)

개발 환경에서 사용하는 데이터베이스 계정과 비밀번호, DB 이름의 정의
frontend 환경변수
/frontend/.env
backend 환경변수
/backend/application-secret.yml

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
````

2. shell script (auto)

개발 환경
windows에서는 `/` 대신 `\` 사용이 필요할 수 있음.

```shell
# front-end 환경, back-end 환경 별 실행
./dev.sh {front|back} {build|up|detach|down}
```

통합 실행 환경(local)
--no-cache 옵션을 통해 변동사항 등 캐시 제거 및 재빌드

```shell
./local.sh {build|restart|deploy|down} [--no-cache]
```

배포 환경
--no-cache 옵션을 통해 변동사항 등 캐시 제거 및 재빌드

```shell
./prod.sh {build|restart|deploy|down} [--no-cache]
```
