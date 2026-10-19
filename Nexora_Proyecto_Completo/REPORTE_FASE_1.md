# Reporte Fase 1 — Fundaciones y esqueleto Spring Boot (Nexora)

## 1. Resumen

Se convirtió Nexora en un proyecto Spring Boot 4.1 (Java 17) con la estructura base de
arquitectura hexagonal. Se reorganizaron las clases existentes (sin cambiar lógica de negocio)
y se verificó la compilación con `mvn clean compile` → **BUILD SUCCESS**.

## 2. Archivos modificados

- `pom.xml` — añadido parent Spring Boot 4.1.0, starters (web, data-jpa, data-mongodb, security,
  validation, actuator), MySQL connector, Lombok, JJWT 0.12.6, plugins spring-boot-maven y compiler.
- `src/main/java/com/nexora/Main.java` — actualizados imports a nuevos paquetes (sin cambio de lógica).

## 3. Archivos creados

- `src/main/java/com/nexora/NexoraApplication.java` — clase principal `@SpringBootApplication`.
- `src/main/resources/application.yml` — configuración básica (puerto 8080, app name, placeholders MySQL/Mongo/JWT).

## 4. Estructura creada y movimientos

Nueva estructura bajo `src/main/java/com/nexora/`:
- `application/ports/in`, `application/ports/out`, `application/services`
- `domain/models`, `domain/services`
- `adapters/in/rest`, `adapters/out/persistence`
- `infrastructure/config`

Movimientos:
- `com.nexora.domain.*` → `com.nexora.domain.models.*` (14 entidades).
- `com.nexora.service.*` → `com.nexora.domain.services.*` (8 servicios).
- `com.nexora.repository.InMemoryRepository` → `com.nexora.adapters.out.persistence.InMemoryRepository`.
- `enums/` se mantiene en `com.nexora.enums`.

Se actualizaron las declaraciones de paquete e imports correspondientes en los archivos movidos
y en `Main.java` y `NexoraTest.java`.

## 5. Compilación

Comando ejecutado:
`mvn -f pom.xml clean compile` (usando wrapper de referencia).

Resultado: **BUILD SUCCESS** — 33 archivos fuente compilados (release 17).

## 6. Notas

- Maven no estaba en PATH; se usó el wrapper del proyecto de referencia.
- JDK disponible en la máquina es 26; compilación se realizó con `--release 17`.
- No se modificó lógica de negocio.
- Las carpetas hexagonales vacías se crearon como directorios en disco (sin archivos placeholder).
