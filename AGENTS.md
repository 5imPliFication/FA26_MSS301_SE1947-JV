# Repository instructions

## Build layout

- There is no root `pom.xml` or Maven wrapper. Use an installed `mvn` with JDK 21; run commands from the repository root with `-f` or from the relevant project directory.
- `ats_be/pom.xml` is a six-module reactor: `infras/api-gateway` plus the five `services/*-service` modules. `ddd-design-sample/pom.xml` is a separate standalone project and is not consumed by the backend.

## Commands

From the repository root:

- `mvn -f ats_be/pom.xml compile` — compile/typecheck the backend reactor.
- `mvn -f ats_be/pom.xml test` — run the backend test lifecycle.
- `mvn -f ats_be/pom.xml clean package` — build all backend modules.
- `mvn -f ddd-design-sample/pom.xml compile` — compile/typecheck the standalone sample.
- `mvn -f ddd-design-sample/pom.xml test` — run the sample's test lifecycle.
- `mvn -f ddd-design-sample/pom.xml clean package` — build the standalone sample.
- `mvn -f ats_be/pom.xml -pl services/candidate-service test` — focus on the candidate module; use the same `-pl` form for another module.

There are currently no test sources, so `mvn test` has nothing to execute and no single-test selector exists. There is also no repository-defined lint, formatter, or static-analysis command; Maven compilation is the available typecheck.

## Current constraints

- The full backend reactor currently fails in `job-service`: `CreateJobUseCase` references an undeclared `Job` and calls nonexistent `JobAggregate.draft()`. Use a focused module build when that baseline failure is unrelated.
- Module names do not imply runnable services. There is no `@SpringBootApplication` or `spring-boot-maven-plugin`; several modules only contain the same plain `fu.ats.Main`, while `candidate-service` has web code but no application entrypoint.
- `candidate-service` and `job-service` hard-code local PostgreSQL settings. Their `spring.jpa` blocks are nested under `spring.datasource.hikari`, so the apparent `ddl-auto: update` is not bound as Spring JPA configuration. There are no migrations or schema fixtures; do not assume tables will be created.
- `candidate-service` and `notification-service` use Lombok and MapStruct annotation processing. Generation happens during Maven compilation under ignored `target/` output; there is no separate codegen command or checked-in generated source.
- Ignore tracked `.idea` metadata when determining workflows; it contains stale JavaScript lint and machine-local settings.
