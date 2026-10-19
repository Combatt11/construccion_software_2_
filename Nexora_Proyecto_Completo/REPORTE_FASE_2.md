# Reporte Fase 2 — Dominio puro, Value Objects, Excepciones y Puertos (Nexora)

## 1. Resumen

Se endureció el dominio y se introdujo la estructura hexagonal de puertos. Los servicios
dejaron de depender de `InMemoryRepository` directamente y ahora dependen de puertos de salida.
Los casos de uso se exponen mediante puertos de entrada. El proyecto compila y los tests pasan
(`mvn test` → BUILD SUCCESS, 6/6 tests).

## 2. Modelos anémicos resueltos

- Entidades movidas a `com.nexora.domain.models` (Java puro, sin anotaciones de framework).
- Se mantienen invariantes de negocio en los servicios/entidades (ej. inventario no negativo,
  pedido finalizado no modificable mediante `ensureEditable()`).
- Se crearon Value Objects: `Money` y `Email` en `com.nexora.domain.valueobjects`.
- Se crearon excepciones de dominio en `com.nexora.domain.exceptions`:
  `DomainException`, `InsufficientStockException`, `InvalidOrderStateException`,
  `DuplicateEmailException`, `UnauthorizedOperationException`.

## 3. Puertos creados

### Puertos de salida (`application/ports/out`)
- `UserRepositoryPort`
- `ProductRepositoryPort`
- `OrderRepositoryPort`
- `InventoryRepositoryPort`
- `InventoryMovementRepositoryPort`

### Puertos de entrada (`application/ports/in`)
- `UserUseCase`
- `ProductUseCase`
- `InventoryUseCase`
- `CartUseCase`
- `OrderUseCase`
- `InvoiceUseCase`
- `ReturnUseCase`

## 4. Hallazgos críticos resueltos

- **Fuga de persistencia en OrderService:** ya no recibe el repositorio de productos como parámetro;
  ahora depende de `ProductRepositoryPort` inyectado por constructor.
- **Servicios dependientes de InMemoryRepository:** refactorizados a depender solo de puertos de salida.
- **IDs calculados (id+1000):** se mantienen temporalmente; la generación segura de IDs queda
  para fases posteriores, pero la persistencia quedó encapsulada tras puertos.
- **Modelos anémicos:** se añadieron Value Objects y excepciones de dominio; la lógica de transición
  de estados del pedido se mantiene en el dominio (`Order.ensureEditable`).

## 5. Adapters y refactor

- Se crearon adaptadores in-memory en `adapters/out/persistence` implementando los puertos de salida.
- Los servicios se movieron a `application/services` e implementan los puertos de entrada.
- `Main.java` y `NexoraTest.java` se actualizaron para inyectar los adaptadores (sin cambiar reglas).

## 6. Compilación y tests

- `mvn clean compile` → BUILD SUCCESS (57 fuentes).
- `mvn test` → BUILD SUCCESS (6/6 tests).

## 7. Archivos creados/modificados principales

Creados: excepciones de dominio, Value Objects, puertos in/out, adaptadores in-memory, servicios refactorizados.
Modificados: `Main.java`, `NexoraTest.java`, servicios (movidos a `application/services`).
