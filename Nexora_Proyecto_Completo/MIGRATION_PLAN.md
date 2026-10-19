# Plan de Migración — Nexora Marketplace → Arquitectura Hexagonal (Spring Boot)

> Fase 0: Análisis y planificación. **No se modifica código en esta fase.**
> Proyecto origen (referencia): `C:\Users\Alejandro\construccion_de_software_2_2026_2` (sistema Bancario).
> Proyecto destino: `Nexora_Proyecto_Completo` (Marketplace).

---

## 1. Resumen ejecutivo

Nexora es hoy un proyecto Java 17 + Maven minimalista (solo JUnit 5) con entidades en memoria,
servicios con reglas de negocio y un repositorio genérico `InMemoryRepository<T>`. No tiene web,
seguridad, persistencia real ni separación hexagonal.

El proyecto de referencia es un sistema Bancario completo en **Spring Boot 4.1** con **arquitectura
hexagonal (Ports & Adapters + DDD)**: REST por roles, JWT stateless con invalidación por versión,
persistencia relacional en MySQL (JPA) + auditoría en MongoDB, validación Jakarta, manejo global
de errores, Actuator y Docker Compose.

La migración consiste en **reestructurar Nexora para adoptar esa arquitectura y stack**, manteniendo
el dominio Marketplace (compradores, vendedores, productos, inventario, carritos, pedidos,
facturación, envíos, devoluciones, reembolsos) y sus reglas de negocio actuales.

---

## 2. Estado actual de Nexora

### 2.1 Stack y estructura

- Java 17, Maven, JUnit 5.
- Paquete raíz: `com.nexora`.
- Capas actuales: `domain/`, `enums/`, `repository/`, `service/`, `Main.java`.
- Persistencia: `InMemoryRepository<T>` (listas en memoria).
- Sin framework web, sin seguridad, sin base de datos, sin Docker.

### 2.2 Dominio actual (entidades)

`User`, `Product`, `Category`, `Warehouse`, `Inventory`, `InventoryMovement`, `Cart`, `CartItem`,
`Order`, `OrderItem`, `Invoice`, `Shipment`, `Return`, `Refund`.

### 2.3 Reglas de negocio existentes

- Identificador y correo de usuario únicos.
- Un rol por usuario.
- Vendedor solo se incorpora por administrador.
- Inventario no negativo; no reservar más de lo disponible ni inventario dañado.
- Ciclo de estados del pedido: `PENDING_PAYMENT → PAID → DISPATCHED → DELIVERED → FINALIZED`.
- Pedido finalizado no modificable.
- Roles: `BUYER`, `SELLER`, `ADMINISTRATOR`, `LOGISTICS_OPERATOR`, `SUPERVISOR`.

### 2.4 Casos de uso planeados (SDD Nexora)

Gestión de usuarios, onboarding de vendedor, productos, categorías, bodegas, inventario,
movimientos de inventario, carrito, creación de pedido, pago, factura, preparación, despacho,
consulta de estado, devoluciones, reembolsos, consulta operativa del supervisor.

---

## 3. Proyecto de referencia (Banco) — capacidades a reutilizar

### 3.1 Stack

Java 17, Maven (wrapper), Spring Boot 4.1.0, Spring MVC, Spring Security (JWT stateless + BCrypt),
Spring Data JPA/Hibernate → MySQL 8, Spring Data MongoDB → MongoDB (auditoría), JJWT 0.12.6,
Jakarta Bean Validation, Actuator, Lombok, Docker.

### 3.2 Arquitectura hexagonal

Paquetes bajo `application/`:
- `domain/`: `models`, `valueobjects`, `enums`, `exceptions`, `ports/in`, `ports/out`, `services`.
- `adapters/`: `rest` (controllers, dtos, mappers, exception), `useCases` (implementan puertos de entrada),
  `persistence` (jpa + mongodb), `security`, `config`, `notification`.
- `infrastructure/`: `seed` (DatabaseSeeder).

### 3.3 Características funcionales del Banco

- Autenticación JWT sin sesión (`sub`, `jti`, `ver`, `iat`, `exp`; sin PII).
- Invalidación de tokens por versión (`authTokenVersion`): cambio de contraseña/estado/logout
  incrementa la versión y deja obsoletos los tokens anteriores.
- 8 puertos de entrada por rol + acceso público.
- Persistencia relacional automática (DDL `update`).
- Auditoría NoSQL en MongoDB.
- Manejo global de excepciones con envelope + `X-Request-Id`.
- CORS restringido por origen.
- Actuator `/actuator/health`.
- Seeder de datos iniciales.
- 110+ tests (dominio, servicios, JWT, seguridad, persistencia fake).

---

## 4. Mapa de traducción de dominio (Banco → Marketplace)

### 4.1 Capas y paquetes

| Capa Banco | Equivalente Nexora | Notas |
|---|---|---|
| `application/` (raíz) | `com.nexora/` (raíz) | Mantener paquete existente; subpaquetes idénticos. |
| `domain/models` | `domain/models` | Entidades Nexora actuales + `Person`/base si se desea. |
| `domain/valueobjects` | `domain/valueobjects` | `Money`, `SystemRole`, estados como VOs (opcional, incremental). |
| `domain/enums` | `enums/` o `domain/enums` | Mover enums al dominio; mantener compatibilidad. |
| `domain/exceptions` | `domain/exceptions` | Excepciones de dominio por entidad. |
| `domain/ports/in` | `domain/ports/in` | Puertos por rol Marketplace. |
| `domain/ports/out` | `domain/ports/out` | Repositorios y servicios externos. |
| `domain/services` | `domain/services` | Reglas de negocio puras. |
| `adapters/rest` | `adapters/rest` | Controllers por rol/feature, DTOs, mappers, exception. |
| `adapters/useCases` | `adapters/useCases` | Implementaciones de puertos de entrada. |
| `adapters/persistence/jpa` | `adapters/persistence/jpa` | Entidades JPA, repos, mappers, adapters. |
| `adapters/persistence/mongodb` | `adapters/persistence/mongodb` | Auditoría NoSQL. |
| `adapters/security` | `adapters/security` | JWT, filtro, BCrypt, config, principal. |
| `adapters/config` | `adapters/config` | Configuración de negocio (NoOp, defaults). |
| `adapters/notification` | `adapters/notification` | Notificaciones (NoOp inicial). |
| `infrastructure/seed` | `infrastructure/seed` | Seeder de datos demo. |
| `infrastructure/config` | `infrastructure/config` | Config técnica. |

### 4.2 Conceptos de dominio (entidades)

| Concepto Banco | Concepto Marketplace | Justificación |
|---|---|---|
| `Person` | `User` / `Buyer` / `Seller` | Persona base con datos de contacto. |
| `Customer` (abstracto) | `Buyer` / `Seller` | Cliente bancario → comprador/vendedor marketplace. |
| `NaturalCustomer` | `Buyer` | Cliente individual. |
| `BusinessCustomer` | `Seller` (vendedor) | Cliente empresarial → vendedor. |
| `BankAccount` (BankingProduct) | `Product` | **Cuentas → Productos** (producto bancario → producto marketplace). |
| `BankingProduct` | `Product` (base) | Producto comercializable. |
| `Loan` | `Order` / pago a plazos (futuro) | No hay préstamos; se adapta a pedidos. |
| `Transfer` | `Shipment` / transacción de pedido | Movimiento de valor/bien. |
| `Operation` | `InventoryMovement` / evento de pedido | Registro de operación. |
| `AuditLog` | `AuditLog` | Igual; auditoría de acciones. |
| `User` | `User` | Igual; con `authTokenVersion`. |
| `CustomerProducts` | Productos del vendedor/comprador | Catálogo asociado. |

### 4.3 Roles

| Rol Banco | Rol Marketplace | Notas |
|---|---|---|
| `NATURAL_CUSTOMER` | `BUYER` | Comprador. |
| `BUSINESS_CUSTOMER` | `SELLER` | Vendedor. |
| `TELLER_EMPLOYEE` | `SELLER` (operativo) | **Teller → Seller** (operación de caja → gestión de venta). |
| `COMMERCIAL_EMPLOYEE` | `ADMINISTRATOR` (comercial) | Gestión de catálogo/comercial. |
| `BUSINESS_OPERATOR` | `LOGISTICS_OPERATOR` | Operaciones logísticas. |
| `BUSINESS_SUPERVISOR` | `SUPERVISOR` | Supervisión. |
| `INTERNAL_ANALYST` | `ADMINISTRATOR` (analista) | Revisión/aprobación interna. |

### 4.4 Puertos de salida (repositorios)

| Puerto Banco | Puerto Marketplace | Persistencia |
|---|---|---|
| `CustomerRepositoryPort` | `BuyerRepositoryPort` / `SellerRepositoryPort` | MySQL (JPA) |
| `UserRepositoryPort` | `UserRepositoryPort` | MySQL (JPA) |
| `BankAccountRepositoryPort` | `ProductRepositoryPort` | MySQL (JPA) |
| `LoanRepositoryPort` | `OrderRepositoryPort` | MySQL (JPA) |
| `TransferRepositoryPort` | `ShipmentRepositoryPort` (o `OrderRepositoryPort`) | MySQL (JPA) |
| `OperationRepositoryPort` | `InventoryMovementRepositoryPort` / `OrderEventRepositoryPort` | MySQL (JPA) |
| `AuditLogRepositoryPort` | `AuditLogRepositoryPort` | MongoDB |
| `AuthorizationPort` | `AuthorizationPort` | Dominio |
| `JwtServicePort` | `JwtServicePort` | Seguridad |
| `PasswordServicePort` | `PasswordServicePort` | Seguridad (BCrypt) |
| `NotificationPort` | `NotificationPort` | NoOp inicial |
| `BusinessConfigurationPort` | `BusinessConfigurationPort` | Defaults |

### 4.5 Puertos de entrada (por rol)

Se adaptan los 8 puertos del Banco a los 5 roles de Nexora (más acceso público):

| Puerto Marketplace | Actor | Casos de uso principales |
|---|---|---|
| `PublicAccessPort` | Público | Login, logout, registro comprador, registro vendedor (vía admin), registro usuario. |
| `BuyerPort` | BUYER | Perfil, productos, carrito, pedido, pago, consulta. |
| `SellerPort` | SELLER | Gestión de productos, inventario propio, pedidos. |
| `AdministratorPort` | ADMINISTRATOR | Usuarios, onboarding vendedor, categorías, configuración. |
| `LogisticsOperatorPort` | LOGISTICS_OPERATOR | Preparación, despacho, envíos. |
| `SupervisorPort` | SUPERVISOR | Consulta operativa, auditoría. |

### 4.6 Servicios de dominio

Los servicios actuales de Nexora (`UserService`, `ProductService`, `InventoryService`, `OrderService`,
`InvoiceService`, `ReturnService`, `CartService`) se refactorizan a servicios de dominio puros
(`domain/services/...`) que reciben repositorios por puertos de salida, sin dependencias de framework
ni de `InMemoryRepository`.

---

## 5. Features a añadir (basadas en el proyecto de referencia)

1. Spring Boot 4.1 + Spring MVC (REST API).
2. Arquitectura hexagonal con paquetes `domain/adapters/infrastructure`.
3. Seguridad JWT stateless + BCrypt + invalidación por versión de token.
4. Persistencia relacional MySQL con Spring Data JPA (auto-DDL).
5. Auditoría NoSQL en MongoDB.
6. Puertos de entrada por rol + adaptadores use case.
7. DTOs de request/response + mappers (REST, JPA, Mongo).
8. Validación Jakarta Bean Validation en DTOs.
9. Manejo global de excepciones con envelope + `X-Request-Id`.
10. Actuator (`/actuator/health`).
11. CORS configurable por origen.
12. Docker Compose (app + MySQL + MongoDB).
13. Seeder de datos iniciales.
14. Adaptadores NoOp (notificación, configuración de negocio).
15. Tests de dominio, servicios, JWT, seguridad y persistencia fake.
16. (Opcional) Frontend origin config para integración con UI.

---

## 6. Orden de implementación (9 fases)

### Fase 0 — Análisis y planificación (actual)
- Leer estructuras, configuración y features de ambos proyectos.
- Generar mapa de traducción y plan.
- **Entregables:** `MIGRATION_PLAN.md`, `REPORTE_FASE_0.md`.
- **Sin cambios de código.**

### Fase 1 — Bootstrap y estructura
- Crear proyecto Spring Boot 4.1 con Maven (pom con starters).
- Establecer paquetes `domain/adapters/infrastructure` bajo `com.nexora`.
- Configurar `application.properties` (MySQL, Mongo, JWT, server).
- Docker Compose inicial (app + mysql + mongo).
- **Criterio:** compila y arranca con `/actuator/health` UP.

### Fase 2 — Dominio puro
- Migrar entidades Nexora a `domain/models` (sin Spring/JPA).
- Crear value objects/enums/excepciones de dominio.
- Definir puertos de salida (`domain/ports/out`).
- Definir puertos de entrada por rol (`domain/ports/in`).
- **Criterio:** dominio compila sin dependencias de framework.

### Fase 3 — Persistencia
- Entidades JPA + repositorios Spring Data + mappers + adapters (MySQL).
- Documentos Mongo + repositorio + mapper + adapter (auditoría).
- **Criterio:** auto-DDL crea tablas/colección; CRUD básico.

### Fase 4 — Seguridad
- JWT provider, filtro, BCrypt, `SecurityConfig`, principal adaptado.
- Campo `authTokenVersion` en `User` y validación en filtro.
- **Criterio:** login/logout e invalidación de token viejo (403).

### Fase 5 — Casos de uso y servicios de dominio
- Implementar servicios de dominio puros.
- Implementar adaptadores use case (por rol) que inyectan servicios.
- **Criterio:** reglas de negocio existentes pasan en tests de dominio.

### Fase 6 — Adaptadores REST
- Controllers por rol/feature, DTOs request/response, mappers REST.
- Validación Jakarta y manejo global de excepciones (envelope + `X-Request-Id`).
- **Criterio:** endpoints principales responden con códigos HTTP correctos.

### Fase 7 — Infraestructura y configuración
- Seeder de datos demo.
- CORS, Actuator, NoOp notification/config adapters.
- **Criterio:** app arranca con datos semilla y health UP.

### Fase 8 — Pruebas
- Migrar/adaptar tests existentes a la nueva estructura.
- Añadir tests de JWT, seguridad, persistencia fake y validación de DTOs.
- **Criterio:** `mvn test` BUILD SUCCESS.

### Fase 9 — Integración y validación final
- Prueba end-to-end (registro → login → operación protegida → logout → token obsoleto).
- Verificación de persistencia en MySQL/Mongo.
- Documentación final (README/SETUP adaptados a Nexora).
- **Criterio:** flujo completo verificado en Docker.

---

## 7. Riesgos y puntos críticos

1. **Pureza del dominio:** riesgo de filtrar Spring/JPA en `domain`. Mitigación: revisión de imports y tests sin infraestructura.
2. **Mapeo de roles ambiguo** (Teller/Commercial → Seller/Admin). Mitigación: validar con stakeholders antes de Fase 5.
3. **Modelo de datos incompleto:** entidades Nexora actuales carecen de campos para JPA (ids, timestamps, relaciones). Mitigación: ampliar modelos en Fase 2/3 sin romper reglas.
4. **Invalidación de tokens:** requiere `authTokenVersion` persistido y actualizado en cada cambio sensible. Riesgo de olvidar bump en algún flujo.
5. **Complejidad operativa:** MySQL + Mongo + app en Docker; conflictos de puertos y versiones (Spring Boot 4.x usa `spring.mongodb.uri`).
6. **Regresión de reglas:** los tests actuales son mínimos; migrar sin perder invariantes (inventario no negativo, pedido finalizado inmutable).
7. **Codificación:** archivos con acentos; mantener UTF-8 en fuentes y properties.
8. **Scope creep:** no migrar préstamos/transferencias bancarias literalmente; adaptar solo lo aplicable al marketplace.
9. **Testing con bases reales:** `contextLoads` requiere MySQL arriba; documentar.

---

## 8. Decisiones recomendadas (a confirmar)

- Paquete raíz se mantiene `com.nexora` (no `application`).
- Roles Nexora se mapean a los puertos por rol del Banco.
- Productos reemplazan a cuentas bancarias como agregado principal.
- Auditoría se guarda en MongoDB (colección `audit_logs`).
- Notificaciones inician como NoOp.
- Persistencia relacional principal en MySQL (migrar desde memoria).

---

## 9. Archivos de esta fase

- `MIGRATION_PLAN.md` (este archivo).
- `REPORTE_FASE_0.md` (resumen de análisis y archivos creados).
