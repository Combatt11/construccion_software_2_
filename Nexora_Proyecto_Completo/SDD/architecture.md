# Arquitectura — Nexora (Hexagonal)

Nexora sigue una arquitectura hexagonal (Ports & Adapters) con DDD.

## Capas

- `domain/`: modelos puros, value objects, enums, excepciones. Sin dependencias de framework.
- `application/`: puertos de entrada (casos de uso), puertos de salida (repositorios/servicios externos),
  servicios que implementan los casos de uso.
- `adapters/in/rest/`: controladores, DTOs, mappers REST, manejo global de errores.
- `adapters/out/persistence/`: adaptadores JPA (MySQL), Mongo (auditoría), in-memory (tests).
- `infrastructure/`: seguridad (JWT, filtros), config (CORS, seeder), request id.

## Flujo de dependencias

```
Controller -> Input Port -> Domain Service -> Output Port -> Persistence Adapter -> DB
```

Todas las dependencias apuntan hacia el dominio. El dominio no conoce frameworks ni bases de datos.

## Restricciones

1. La lógica de negocio vive exclusivamente en el dominio.
2. Los controladores no contienen reglas de negocio.
3. Los DTOs no entran al dominio.
4. Las entidades de persistencia no se exponen por la API.
5. La comunicación con tecnologías externas ocurre solo por puertos.
6. El dominio es testeable sin infraestructura.
