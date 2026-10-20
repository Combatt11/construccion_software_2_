# Nexora — Plataforma Marketplace (Arquitectura Hexagonal)

Nexora es una plataforma marketplace con compradores, vendedores, administradores, operadores
logísticos y supervisores. Cubre usuarios, productos, categorías, bodegas, inventario, carritos,
pedidos, facturación, envíos, devoluciones y reembolsos.

El proyecto fue migrado a **arquitectura hexagonal (Ports & Adapters + DDD)** sobre Spring Boot 4.1,
siguiendo el modelo del sistema bancario de referencia del curso.

## Stack tecnológico

- Java 17, Maven
- Spring Boot 4.1 (Web, Security, Data JPA, Data MongoDB, Validation, Actuator)
- Persistencia relacional: MySQL 8 (JPA/Hibernate, auto-DDL)
- Auditoría NoSQL: MongoDB (colección `audit_logs`)
- Seguridad: JWT stateless (JJWT 0.12.6) + BCrypt, invalidación por versión de token
- Docker / Docker Compose
- Pruebas: JUnit 5 + Mockito

## Arquitectura

Hexagonal (Ports & Adapters). Paquetes bajo `com.nexora`:

- `domain/`: modelos, value objects, enums, excepciones (Java puro).
- `application/`: puertos de entrada (`ports/in`), puertos de salida (`ports/out`), servicios (casos de uso).
- `adapters/`: entrada REST (controllers, DTOs, mappers, handler), salida (JPA, Mongo, in-memory).
- `infrastructure/`: seguridad (JWT, filtros), config (CORS, seeder), request id.

## Ejecutar en local

Prerrequisitos: JDK 17+, Maven, MySQL 8 y MongoDB (o Docker).

```bash
mvn clean compile
mvn test
mvn spring-boot:run
```

La app queda en `http://localhost:8080`. Health: `http://localhost:8080/actuator/health`.

## Ejecutar con Docker

```bash
docker compose up -d --build
```

Servicios: MySQL (3306), MongoDB (27017), app (8080).

## Documentación

- `SDD/architecture.md` — arquitectura hexagonal.
- `SDD/api.md` — endpoints REST.
- `SDD/data_model.md` — modelo de datos.
- `SDD/security.md` — seguridad JWT.
- `VALIDATION.md` — guía de validación end-to-end.
- `MIGRATION_PLAN.md` — plan de migración por fases.

## Credenciales seed (desarrollo)

- Admin: `admin@nexora.com` / `admin123`
- Seller: `seller@nexora.com` / `seller123`
