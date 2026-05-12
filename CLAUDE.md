# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

### Build
```bash
./gradlew clean build
```

### Run locally (requires PostgreSQL running)
```bash
./gradlew bootRun
```

### Run with Docker (app + PostgreSQL)
```bash
docker compose up
```

### Run all tests
```bash
./gradlew test
```

### Run a single test class
```bash
./gradlew test --tests "ru.ekononov.phonebook.unit.service.ContactServiceImplTest"
./gradlew test --tests "ru.ekononov.phonebook.integration.controller.ContactsControllerTest"
```

## Architecture

### Layer structure
```
Controller → Service (interface + impl) → Repository → Entity
                   ↕
             DTO / Mapper
```

- **Controllers** (`controller/`) — REST endpoints, validation via `@Validated` with groups
- **Services** (`service/contact/`, `service/company/`) — business logic, transaction boundaries; class-level `@Transactional(readOnly = true)`, write methods override with `@Transactional`
- **Repositories** (`database/repository/`) — extend `JpaRepository` + `QuerydslPredicateExecutor` for dynamic filtering
- **Entities** (`database/entity/`) — implement `BaseEntity<ID>`; `equals`/`hashCode` based on ID with HibernateProxy awareness
- **DTOs** (`dto/`) — separate Write DTOs (`ContactCreateUpdateDto`, `CompanyCreateUpdateDto`) and Read DTOs (`ContactReadDto`, `CompanyReadDto`); mappers implement the `Mapper<F, T>` interface

### Validation
Two validation groups: `CreateAction` and `UpdateAction` (both combined with `Default`). The custom `@ContactName` annotation validates at the class level that at least one of `firstName`/`lastName` is filled.

### Dynamic filtering with QueryDSL
`QPredicates` (`database/querydsl/QPredicates.java`) is a builder that accumulates `Predicate`s from nullable filter fields and combines them with `AND` (`build()`) or `OR` (`buildOr()`). Q-classes (`QContact`, `QCompany`) are generated at compile time via annotation processor.

### Error handling
`RestControllerExceptionHandler` extends `ResponseEntityExceptionHandler` and returns `ErrorResponse` (with `errors` list containing `field`/`message` maps) for validation failures. Non-existing resources throw `ResponseStatusException` directly from service.

### Configuration
Environment variables with defaults: `DB_HOST` (default: `localhost`), `DB_NAME` (default: `phonebook`), `DB_USERNAME` (default: `postgres`), `DB_PASSWORD` (default: `pass`). Docker Compose requires a `.env` file with all four variables set.

### Database migrations
Liquibase changelogs in `src/main/resources/db/changelog/`. Master file `db.changelog-master.yaml` includes versioned SQL files. DDL is set to `validate` — schema changes must go through a new changelog file.

### Testing
- **Unit tests** (`test/.../unit/`) — `@ExtendWith(MockitoExtension.class)`, mock all dependencies, verify interactions with `verifyNoMoreInteractions`
- **Integration tests** (`test/.../integration/`) — extend `IntegrationTestBase` which starts a Testcontainers PostgreSQL container (`postgres:18.0`) and loads `classpath:sql/data.sql` before each test via `@Sql`
- Test fixtures live in `common/contact/` and `common/company/` — constants in `*TestConstants` inner classes, entity/DTO builders in `*TestFactory`

### API
Base URL: `/api/v1/contacts`, `/api/v1/companies`. Swagger UI available at `http://localhost:8080/swagger-ui/index.html`.
