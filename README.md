# framework-core

전자정부프레임워크 4.3.0 + Spring Boot + Gradle + JDK21 기반 코어 프로젝트 템플릿입니다.

## 포함 기능
- `RuntimeException` -> `BizException` 공통 변환 및 JSON 오류 응답
- 프로필별 DataSource
  - `local`: `application-local.yml` (암호화된 비밀번호)
  - `dev/stag/prod`: WAS JNDI
- DataSource 모드
  - `framework.datasource.mode=multi`
  - `framework.datasource.mode=xa`
- Quartz Trigger + Spring Batch 실행 연동
- 파일 업로드/다운로드 기본 API
- 허용 도메인 기반 CORS 필터
- 컨트롤러 파라미터 VO/Record 규약 점검 로그

## 실행
```bash
./gradlew bootRun
```

Windows:
```powershell
.\gradlew.bat bootRun
```

## 프로필
- local: `spring.profiles.active=local`
- dev: `spring.profiles.active=dev`
- stag: `spring.profiles.active=stag`
- prod: `spring.profiles.active=prod`

## 패키지 규칙
- 프레임워크 코어: `com.framwork.core`
- 샘플: `com.biz`
