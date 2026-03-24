# framework-core

`framework-core`는 Spring Boot 기반의 공통 백엔드 코어 프로젝트입니다.  
초급 개발자가 바로 실행하고, Swagger로 API를 테스트하며, 예외/메시지 처리 방식을 이해할 수 있도록 구성되어 있습니다.

## 1. 프로젝트 목적

이 프로젝트는 아래 공통 기능을 템플릿으로 제공합니다.

- REST API 기본 구조
- 공통 예외 처리 (`BizException`, `GlobalExceptionHandler`)
- 메시지 외부화 (`common_message.properties`)
- 파일 업로드/다운로드 API
- CORS 필터
- 다중 DataSource 및 XA/JNDI 구성
- Quartz + Spring Batch 연동
- Swagger(OpenAPI) 문서 자동화

## 2. 기술 스택

- Java 21
- Spring Boot 3.3.5
- Gradle 9
- Spring Web / Validation / JDBC / Batch / Quartz
- springdoc-openapi (Swagger UI)
- H2 (local 프로필 기본)
- Atomikos (XA 트랜잭션)
- Jasypt (암호화 값 사용 시)
- eGovFrame RTE 4.3.0

## 3. 빠른 시작 (Windows 기준)

### 3-1. 사전 준비

1. JDK 21 설치
2. 터미널(PowerShell) 실행
3. 프로젝트 폴더 이동

```powershell
cd C:\work\python\apm\windows_APM_BOT\framework-core
```

### 3-2. JAVA_HOME 설정

아래 경로는 예시입니다. 본인 PC 경로에 맞게 수정하세요.

```powershell
$env:JAVA_HOME='C:\Users\devel\.jdks\ms-21.0.10'
$env:Path="$env:JAVA_HOME\bin;$env:Path"
java -version
```

### 3-3. 애플리케이션 실행

```powershell
.\gradlew.bat bootRun
```

실행 후 기본 접속 주소:

- App: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/api-docs`

## 4. 빌드/테스트 명령어

```powershell
.\gradlew.bat clean compileJava
.\gradlew.bat test
.\gradlew.bat clean build
```

참고: 현재 테스트 코드는 `NO-SOURCE` 상태일 수 있습니다(테스트 클래스가 없는 경우).

## 5. Swagger로 기능 확인하는 방법

1. 서버 실행 (`bootRun`)
2. 브라우저에서 `http://localhost:8080/swagger-ui.html` 접속
3. 원하는 API 선택
4. `Try it out` 클릭
5. 파라미터 입력 후 `Execute`로 호출
6. 응답 코드/응답 바디 확인

### 자주 확인하는 API

- `GET /api/sample/wrong?name=test`
- `POST /api/sample/vo`
- `POST /api/sample/record`
- `POST /api/core/files/upload` (multipart/form-data, key: `file`)
- `GET /api/core/files/{fileId}/download`

## 6. 메시지 관리 방식 (`common_message.properties`)

공통 메시지는 아래 파일에서 관리합니다.

- `src/main/resources/common_message.properties`

예시 메시지 코드:

- `BIZ-0001`: 기본 오류 메시지
- `FILE-400`, `FILE-404`, `FILE-500`
- `CORS-0001`
- `NOTIFY-0001`

코드에서는 `CommonMessageService`를 통해 메시지 코드를 읽습니다.  
즉, 문자열 하드코딩보다 메시지 코드를 사용하면 유지보수가 쉬워집니다.

## 7. 예외 처리 흐름

1. 비즈니스 로직에서 `BizException("코드")` 또는 `BizException("코드", cause)` 발생
2. `GlobalExceptionHandler`가 예외를 수신
3. 메시지 코드를 `common_message.properties`에서 조회
4. 공통 JSON 에러 응답(`ApiErrorResponse`) 반환

응답 예시(개념):

```json
{
  "timestamp": "2026-03-24T12:34:56+09:00",
  "status": 400,
  "code": "FILE-404",
  "message": "요청한 파일을 찾을 수 없습니다.",
  "path": "/api/core/files/abc/download"
}
```

## 8. 설정 파일 구조

- `application.yml`: 공통 설정
- `application-local.yml`: 로컬 개발 설정(H2, local datasource)
- `application-dev.yml`: 개발 환경 설정
- `application-stag.yml`: 스테이징 환경 설정
- `application-prod.yml`: 운영 환경 설정

프로필 전환 예시:

```powershell
.\gradlew.bat bootRun --args="--spring.profiles.active=local"
```

## 9. DataSource 모드 개요

- `framework.datasource.mode=multi`
- `framework.datasource.mode=xa`

local 프로필은 H2를 사용하고, dev/stag/prod는 JNDI 기반으로 확장할 수 있게 분리되어 있습니다.

## 10. 프로젝트 주요 패키지

- `com.framwork.core.common.config`: 데이터소스/트랜잭션/Swagger 설정
- `com.framwork.core.common.exception`: 공통 예외 및 핸들러
- `com.framwork.core.common.message`: 메시지 조회 서비스
- `com.framwork.core.common.file`: 파일 API
- `com.framwork.core.common.cors`: CORS 필터
- `com.framwork.core.batch`: Quartz + Batch 실행
- `com.biz.sample`: 샘플 API

## 11. 자주 발생하는 오류와 해결

### 11-1. `JAVA_HOME is not set`

- JDK 21 설치 여부 확인
- `JAVA_HOME`, `Path` 설정 후 재실행

### 11-2. eGovFrame 의존성 해상 실패

- `build.gradle`의 eGovFrame 버전 확인 (`4.3.0`)
- 저장소 설정 확인 (`mavenCentral`, eGovFrame Maven URL)

### 11-3. DataSource password 바인딩 실패

- local 환경에서는 `application-local.yml`의 비밀번호를 평문 또는 빈값으로 두고 확인
- `ENC(...)` 사용 시 `JASYPT_ENCRYPTOR_PASSWORD` 환경변수 필요

### 11-4. JNDI 클래스 import 오류

- `org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup` 사용 여부 확인

## 12. 개발 시 권장 순서

1. local 프로필로 서버 실행
2. Swagger에서 API 동작 확인
3. 메시지는 `common_message.properties`에 코드로 추가
4. 코드에서는 문자열 대신 메시지 코드 사용
5. `clean build`로 컴파일/검증 후 커밋

---

문의나 확장 작업 시에는 먼저 `common` 패키지 구조와 `application-*.yml` 환경별 설정부터 확인하는 것을 권장합니다.
