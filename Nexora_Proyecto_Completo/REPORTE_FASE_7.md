# Reporte Fase 7 — Infraestructura, CORS, Seeder, Actuator y Docker (Nexora)

## 1. Resumen

Se configuró CORS, un seeder de datos iniciales, Actuator y se añadieron Dockerfile y servicio
de aplicación en docker-compose. El proyecto compila y los tests pasan (`mvn test` → BUILD SUCCESS, 6/6).

## 2. Configuración

- `CorsConfig`: permite todos los orígenes/métodos/headers para la API.
- `SecurityConfig`: CORS habilitado con la configuración anterior.
- `application.yml`: Actuator expone health, info, metrics.

## 3. Seeder

- `DataSeeder` (CommandLineRunner): crea admin (admin@nexora.com / admin123), seller
  (seller@nexora.com / seller123) y 2 productos demo si no existen. Contraseñas BCrypt.

## 4. Docker

- `Dockerfile`: multi-stage (Maven build + JRE Alpine runtime).
- `docker-compose.yml`: servicios MySQL 8, MongoDB 6 y `nexora-app` (puerto 8080),
  con variables de entorno y dependencias de salud.

## 5. Compilación y tests

- `mvn clean compile` → BUILD SUCCESS (106 fuentes).
- `mvn test` → BUILD SUCCESS (6/6 tests).

## 6. Archivos creados/modificados

Creados: `CorsConfig`, `DataSeeder`, `Dockerfile`. Modificados: `SecurityConfig`,
`application.yml`, `docker-compose.yml`.
