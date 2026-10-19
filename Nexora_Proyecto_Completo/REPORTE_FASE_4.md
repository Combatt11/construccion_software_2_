# Reporte Fase 4 — Persistencia NoSQL (MongoDB) y Auditoría (Nexora)

## 1. Resumen

Se añadió la capa de auditoría en MongoDB con documento, repositorio y adaptador, además de un
filtro que genera `X-Request-Id`. Se integró la auditoría en `UserService`. El proyecto compila
y los tests pasan (`mvn test` → BUILD SUCCESS, 6/6).

## 2. MongoDB y configuración

- `docker-compose.yml`: servicio `mongo-db` (Mongo 6, puerto 27017, volumen `mongo_data`).
- `application.yml`: `spring.mongodb.uri` apuntando a `localhost:27017/nexora_audit_db`.

## 3. Capa NoSQL creada

- Documento: `adapters/out/persistence/document/AuditLog` (`@Document(collection = "audit_logs")`).
- Repositorio: `adapters/out/persistence/mongo/AuditLogMongoRepository` (MongoRepository).
- Adaptador: `adapters/out/persistence/mongo/AuditMongoAdapter` (`@Component`, implementa `AuditPort`).
- Puerto de salida: `application/ports/out/AuditPort`.
- Adaptador in-memory para tests: `adapters/out/persistence/InMemoryAuditAdapter`.

## 4. Filtro e integración

- `infrastructure/config/RequestIdFilter` (OncePerRequestFilter): genera/propaga `X-Request-Id`,
  lo guarda en MDC y lo devuelve en la respuesta.
- `UserService` ahora depende de `AuditPort` y registra acciones `REGISTER_USER` y `ONBOARD_SELLER`.

## 5. Compilación y tests

- `mvn clean compile` → BUILD SUCCESS (84 fuentes).
- `mvn test` → BUILD SUCCESS (6/6 tests).

## 6. Archivos creados/modificados

Creados: `AuditPort`, `AuditLog` (document), `AuditLogMongoRepository`, `AuditMongoAdapter`,
`InMemoryAuditAdapter`, `RequestIdFilter`. Modificados: `docker-compose.yml`, `application.yml`,
`UserService`, `Main.java`, `NexoraTest.java`.
