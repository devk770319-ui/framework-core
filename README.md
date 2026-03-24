# framework-core

Spring Boot 3.3 + Gradle + JDK 21 based core framework template.

## Features
- Global error handling (`RuntimeException` to `BizException`) with JSON response
- Multi/XA datasource modes
- Local and JNDI datasource configurations by profile
- Quartz + Spring Batch integration
- File upload/download API
- Allowed-origin based CORS filter
- Swagger/OpenAPI integration
- Message externalization via `common_message.properties`

## Run
```bash
./gradlew bootRun
```

Windows:
```powershell
.\gradlew.bat bootRun
```

## Profiles
- `local`
- `dev`
- `stag`
- `prod`

## Swagger
- UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/api-docs`
