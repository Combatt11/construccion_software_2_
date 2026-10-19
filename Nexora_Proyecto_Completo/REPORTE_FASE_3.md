# Reporte Fase 3 — Persistencia JPA + MySQL + Docker (Nexora)

## 1. Resumen

Se añadió la capa de persistencia relacional (JPA/MySQL) con entidades, mappers, repositorios
Spring Data y adaptadores que implementan los puertos de salida. Se configuró Docker Compose
para MySQL 8. El proyecto compila y los tests pasan (`mvn test` → BUILD SUCCESS, 6/6).

## 2. Docker y configuración

- `docker-compose.yml` en la raíz: servicio `mysql-db` (MySQL 8, db `nexora_db`, root/root_password, puerto 3306).
- `application.yml`: datasource localhost:3306/nexora_db, credenciales root/root_password, `ddl-auto=update`, `show-sql=true`.

## 3. Persistencia creada

### Entidades JPA (`adapters/out/persistence/jpa/entity`)
- `UserJpaEntity`, `ProductJpaEntity`, `OrderJpaEntity`, `OrderItemJpaEntity`,
  `InventoryJpaEntity`, `InventoryMovementJpaEntity`.

### Mappers (`adapters/out/persistence/jpa/mapper`)
- `UserJpaMapper`, `ProductJpaMapper`, `OrderJpaMapper`, `InventoryJpaMapper`,
  `InventoryMovementJpaMapper`.

### Repositorios Spring Data (`adapters/out/persistence/jpa/repository`)
- `SpringDataJpaUserRepository` (con `findByEmail`), `SpringDataJpaProductRepository`,
  `SpringDataJpaOrderRepository`, `SpringDataJpaInventoryRepository`,
  `SpringDataJpaInventoryMovementRepository`.

### Adaptadores (`adapters/out/persistence/jpa/adapter`)
- `UserJpaAdapter`, `ProductJpaAdapter`, `OrderJpaAdapter`, `InventoryJpaAdapter`,
  `InventoryMovementJpaAdapter` (anotados `@Repository`, implementan los puertos de salida).

## 4. Integración

- Los adaptadores JPA coexisten con los adaptadores in-memory; los servicios siguen dependiendo
  de los puertos de salida.
- `@SpringBootApplication` escanea `com.nexora`, por lo que los adaptadores `@Repository`
  se inyectan automáticamente cuando se usan en un contexto Spring.

## 5. Compilación y tests

- `mvn clean compile` → BUILD SUCCESS (78 fuentes).
- `mvn test` → BUILD SUCCESS (6/6 tests).

## 6. Archivos creados

- `docker-compose.yml`
- Entidades, mappers, repositorios y adaptadores JPA listados arriba.
