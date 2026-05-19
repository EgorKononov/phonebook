# Phonebook

Phonebook - pet-проект на Java + Spring Boot, представляющий собой REST-сервис для управления контактами.

Проект создан в учебных целях и демонстрирует использование Spring Boot, работу с базой данных, контейнеризацию приложения и покрытие его тестами.

---

## Описание приложения и его функциональность
Приложение предоставляет REST API для работы с контактами и включает два CRUD сервиса:

### Contacts
CRUD операции для управления контактами:

- создание контакта
- получение контакта по ID
- получение списка контактов с пагинацией и фильтрацией
- обновление контакта
- удаление контакта

Контакт содержит основные данные (имя, телефон, email и т.д.) и ссылку на компанию.

### Companies
CRUD операции для управления компаниями:

- создание компании
- получение компании по ID
- получение списка компаний с пагинацией и фильтрацией
- обновление компании
- удаление компании

Компания является отдельной сущностью и используется как одно из полей сущности контакта.

## Версионирование API
В проекте используется версионирование REST API на уровне URL. Текущая версия API: v1

Все публичные эндпоинты имеют префикс версии:

```bash
/api/v1/contacts
/api/v1/companies
```

При развитии приложения новые версии API будут добавляться без нарушения обратной совместимости существующих клиентов.

## Архитектура приложения
Приложение построено по классической слоистой архитектуре:

- Controller layer — REST-контроллеры, принимающие и валидирующие входящие запросы
- Service layer — бизнес-логика, управление транзакциями
- Repository layer — доступ к данным через Spring Data JPA и QueryDSL
- DTO / Mapper layer — разделение API-моделей и persistence-сущностей

Такое разделение упрощает тестирование, поддержку и дальнейшее расширение функциональности.
Данная архитектура соответствует общепринятым практикам Spring-приложений и позволяет легко масштабировать проект.

## Ключевые архитектурные решения
- QueryDSL выбран для реализации фильтрации без роста количества методов репозиториев
- Liquibase используется для контролируемого изменения схемы базы данных
- DTO применяются для изоляции REST API от изменений persistence-сущностей
- Для операций создания и обновления используются Write DTO, а для чтения - отдельные Read DTO. Это защищает данные от непреднамеренного изменения и упрощает расширение функциональности
- Docker используются для упрощения локального запуска и воспроизводимости окружения

---

## Требования
Для локального запуска проекта необходимо:

- Java 21+
- Gradle
- Docker

Для запуска в Kubernetes дополнительно необходимо:

- kubectl
- Docker Desktop с включённым Kubernetes (или другой K8s кластер)

---

## Сборка и запуск проекта

### Сборка проекта
Для сборки приложения выполните:

```bash
./gradlew clean build
```

### Запуск без Docker
Для запуска приложения локально выполните:

```bash
./gradlew bootRun
```

### Запуск с использованием Docker
Для запуска приложения и базы данных выполните:

```bash
docker compose up
```

После запуска приложение доступно на http://localhost:8080

Swagger / OpenAPI: http://localhost:8080/swagger-ui/index.html

PostgreSQL запускается в отдельном Docker контейнере

### Запуск в Kubernetes

Манифесты для деплоя находятся в директории `k8s/`.

**1. Собрать JAR и Docker-образ:**

```bash
./gradlew bootJar
docker build -t phonebook:1.0.0 .
```

**2. Применить манифесты:**

```bash
kubectl apply -f k8s/
```

**3. Дождаться готовности подов:**

```bash
kubectl -n phonebook rollout status deployment/postgres
kubectl -n phonebook rollout status deployment/phonebook
```

После запуска приложение доступно на http://localhost:30080

Swagger / OpenAPI: http://localhost:30080/swagger-ui/index.html

**Диагностика при проблемах:**

```bash
kubectl -n phonebook get pods
kubectl -n phonebook logs deployment/phonebook
kubectl -n phonebook describe pod <pod-name>
```

**Удаление всех ресурсов:**

```bash
kubectl delete namespace phonebook
```

---

## Используемые технологии
### Spring Boot
Spring Boot является основным фреймворком приложения и используется для:

- автоконфигурации приложения
- запуска embedded Tomcat
- работы с зависимостями
- построения REST API
- работы с профилями

### Spring Data JPA + QueryDSL
Для работы с базой данных используется Spring Data JPA и QueryDSL:

- работа с сущностями и транзакциями
- репозитории реализованы через JpaRepository
- пагинация (Page, Pageable)
- динамическая фильтрация с помощью QueryDSL
- в качестве базы данных используется PostgreSQL

### Liquibase
Liquibase применяется для управления схемой базы данных:

- все изменения БД версионируются
- миграции описываются в changelog-файлах
- автоматический запуск миграций при старте приложения
- контроль целостности структуры БД

### Docker
Docker используется для контейнеризации:

- изолированная среда запуска приложения
- запуск базы данных в контейнере
- сохранение логов приложения в Docker volume

### Kubernetes
Kubernetes используется для оркестрации контейнеров:

- деплой приложения и базы данных через манифесты в директории `k8s/`
- изоляция ресурсов в отдельном namespace
- хранение credentials в Secret, конфигурации в ConfigMap
- персистентное хранилище для PostgreSQL через PersistentVolumeClaim
- health-проверки через readiness и liveness пробы

### Externalized Configuration

Конфигурация приложения вынесена за пределы исходного кода и управляется через переменные окружения, .env файлы и Spring Profiles.

### Spring Validation
Для валидации входящих данных применяется Spring Validation:
 
- стандартная валидация (@NotNull, @NotBlank, @Email и т.д.)
- кастомные валидационные аннотации
- разные правила валидации для операций создания и обновления

### Unit-тестирование
Unit-тесты используются для проверки бизнес-логики приложения:

- тестирование слоя сервисов
- изоляция зависимостей с помощью Mockito

### Integration-тестирование
Integration-тесты проверяют работу приложения целиком:

- тестирование REST-контроллеров через MockMvc
- тестирование слоя сервисов и репозиториев
- использование Testcontainers для поднятия базы данных в тестах

### Swagger / OpenAPI
Для документирования REST API используется Swagger / OpenAPI

- автоматическая генерация OpenAPI спецификации
- интерактивная документация Swagger UI
- возможность тестировать REST API через браузер

### Spring Boot Actuator
Actuator используется для наблюдаемости и интеграции с Kubernetes:

- `/actuator/health/readiness` — проверка готовности приложения принимать трафик (используется K8s readiness probe)
- `/actuator/health/liveness` — проверка работоспособности приложения (используется K8s liveness probe)
- Включает статус подключения к БД — K8s не пустит трафик, пока не завершатся Liquibase-миграции

### Нефункциональные аспекты
- Валидация входящих данных выполняется на уровне DTO
- Обработка ошибок централизована через @RestControllerAdvice
- Транзакционность управляется на сервисном слое
- Логи приложения пишутся в Docker volume и сохраняются между перезапусками контейнеров

## Планируемые улучшения

### Целевая архитектура платформы

```
Internet
   │
   ▼
┌──────────────────────────────────┐
│           api-gateway            │  Spring Cloud Gateway + Security (JWT/OAuth2)
│      rate-limit (Redis)          │  Circuit Breaker (Resilience4j)
└──┬──────────┬──────────┬─────────┘
   │          │          │
   ▼          ▼          ▼
contact    audit      analytics
service    service     service
(Core)    (MongoDB)   (MongoDB)
   │           ▲           ▲
   │      RabbitMQ       Kafka
   │           └──────────┘
   └──► Outbox Table (PostgreSQL)
               │
          Debezium CDC
               │
         ┌─────┴──────┐
         ▼            ▼
       Kafka       RabbitMQ
         │
         ▼
  notification-service
     (MongoDB + email/webhook)
```

Все сервисы подключены к **Spring Cloud Config Server** и получают обновления конфигурации в реальном времени через **Spring Cloud Bus** (Kafka-топик `spring-cloud-bus`) без перезапуска.

---

### Новые микросервисы

#### api-gateway
- **Spring Cloud Gateway** — маршрутизация и балансировка запросов ко всем сервисам
- **Spring Security + OAuth2 Resource Server** — валидация JWT-токенов
- **Rate Limiting** — ограничение запросов через Redis (Spring Gateway RateLimiter)
- **Circuit Breaker** — Resilience4j на каждом маршруте

#### notification-service
- Kafka consumer: реагирует на события `ContactCreated`, `ContactUpdated`, `ContactDeleted`
- Доставка уведомлений по каналам: email (Spring Mail), webhook, push
- MongoDB: история уведомлений и пользовательские настройки подписок
- Redis Streams: real-time уведомления через WebSocket

#### audit-service
- RabbitMQ consumer: получает события `AuditRequested` от contact-service
- MongoDB: immutable event log — только append, никаких обновлений
- REST API: история изменений по контакту / компании за период

#### analytics-service
- Kafka consumer: агрегирует события в реальном времени
- MongoDB Aggregation Pipeline: статистика (контакты за период, рост компаний, активность пользователей)
- Redis: кэш готовых отчётов

---

### Outbox Pattern + Debezium CDC

В `phonebook-core` добавляется таблица `outbox_events` (PostgreSQL). При каждом изменении контакта или компании в рамках одной транзакции записываются и сама сущность, и событие в outbox.

**Debezium** читает WAL PostgreSQL и публикует события в Kafka/RabbitMQ без задержки — надёжнее polling-based подхода, не нагружает основную БД дополнительными запросами.

---

### Spring Cloud Bus — кейс: динамическое управление нагрузкой

**Сценарий:** пользователь импортирует CSV с 50 000 контактами. Каждый созданный контакт порождает событие — `notification-service` начинает отправлять тысячи уведомлений, почтовые провайдеры блокируют отправителя.

**Решение без перезапуска сервисов:**

1. Оператор меняет в Git-репозитории конфигов (`notification-service.yaml`):
   ```yaml
   notifications:
     rate-limit:
       per-minute: 5
     batch-mode:
       enabled: true
       flush-interval-seconds: 300
   ```
2. Вызывает `POST /actuator/busrefresh` на config-server
3. Spring Cloud Bus рассылает `RefreshRemoteApplicationEvent` через Kafka всем инстансам `notification-service` одновременно
4. `@RefreshScope`-бины пересоздаются с новыми значениями — трафик не прерывается

Аналогично управляются: maintenance-режим в api-gateway, TTL Redis-кэша в contact-service, окно агрегации в analytics-service.

---

### Cross-cutting concerns

| Аспект | Технология |
| --- | --- |
| Конфигурация | Spring Cloud Config Server + Git-репозиторий |
| Live refresh | Spring Cloud Bus (Kafka) + `@RefreshScope` |
| Service Discovery | Kubernetes native DNS (без Eureka) |
| Распределённый кэш | Redis |
| Трассировка | Micrometer Tracing + Zipkin/Tempo |
| Метрики | Micrometer + Prometheus + Grafana |
| Логи | Loki + Grafana |
| Kafka-схемы | Confluent Schema Registry + Avro |
| Секреты | Kubernetes Secrets + Sealed Secrets |
| CI/CD | GitHub Actions → GHCR → Helm → Kubernetes |

---

### Порядок реализации

1. **Инфраструктура** — расширить Docker Compose (Kafka + Zookeeper, RabbitMQ, Redis, MongoDB, Zipkin)
2. **api-gateway** — Spring Cloud Gateway + Spring Security JWT
3. **Outbox + Spring Cloud Bus** — в `phonebook-core`, Kafka/RabbitMQ события
4. **audit-service** — RabbitMQ consumer + MongoDB
5. **notification-service** — Kafka consumer + MongoDB + email
6. **analytics-service** — Kafka consumer + MongoDB + Redis
7. **CI/CD** — GitHub Actions: тесты → Docker image → GHCR → Helm deploy в K8s
8. **Observability** — Prometheus + Grafana + Loki
