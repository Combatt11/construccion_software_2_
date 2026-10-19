# Reporte Fase 0 — Nexora (Marketplace) Migración a Hexagonal

## 1. Resumen del análisis

Se analizaron ambos proyectos:

- **Nexora (destino):** Java 17 + Maven + JUnit 5, arquitectura simple (domain/enums/repository/service), persistencia en memoria (`InMemoryRepository<T>`), reglas de negocio de marketplace (usuarios, productos, inventario, pedidos, facturas, envíos, devoluciones). Sin web, seguridad ni bases de datos.
- **Banco (referencia):** Java 17 + Spring Boot 4.1, arquitectura hexagonal (Ports & Adapters + DDD), REST por roles, JWT stateless con invalidación por versión, MySQL (JPA) + MongoDB (auditoría), validación Jakarta, manejo global de errores, Actuator, Docker Compose, 110+ tests.

Se generó el mapa de traducción de dominio (Banco → Marketplace), destacando:
- Cuentas bancarias → Productos.
- Teller → Seller.
- Natural/Business Customer → Buyer/Seller.
- Business Operator/Supervisor/Internal Analyst → Logistics Operator/Supervisor/Administrator.
- Préstamos/transferencias → pedidos/envíos (adaptación, no migración literal).
- Repositorios y puertos adaptados a entidades marketplace.

## 2. Archivos creados

1. `MIGRATION_PLAN.md` — Plan completo de migración:
   - Mapa de equivalencias de conceptos y capas hexagonales.
   - Features a añadir (basadas en el proyecto de referencia).
   - Orden de implementación por 9 fases.
   - Riesgos y puntos críticos.
   - Decisiones recomendadas.

2. `REPORTE_FASE_0.md` — Este archivo (resumen del análisis y archivos creados).

## 3. Alcance respetado

- **Solo análisis.** No se modificó código fuente, `pom.xml`, configuración ni tests.
- No se crearon worktrees ni se ejecutaron builds.

## 4. Próximos pasos sugeridos

Iniciar Fase 1: bootstrap Spring Boot 4.1, estructura hexagonal, configuración y Docker Compose,
sin aún migrar reglas de negocio.
