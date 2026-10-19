# Reporte Fase 6 — Adaptadores REST, DTOs, Validaciones y Manejo de Errores (Nexora)

## 1. Resumen

Se añadió la capa REST con DTOs validados, mappers, manejo global de errores y controladores
por rol (Admin, Seller, Buyer). Se habilitó seguridad por método (`@PreAuthorize`). El proyecto
compila y los tests pasan (`mvn test` → BUILD SUCCESS, 6/6).

## 2. DTOs y validaciones

- Requests: `RegisterUserRequest`, `CreateProductRequest`, `CreateOrderRequest`, `OrderItemRequest`
  con anotaciones Jakarta (`@NotBlank`, `@Email`, `@Size`, `@NotNull`, `@DecimalMin`, `@Min`, etc.).
- Responses: `UserResponse`, `ProductResponse`, `OrderResponse`, `OrderItemResponse`.

## 3. Mappers REST

- `UserRestMapper`, `ProductRestMapper`, `OrderRestMapper` (Dominio ↔ DTO).

## 4. Manejo global de errores

- `GlobalExceptionHandler` (`@RestControllerAdvice`) con envelope estándar:
  timestamp, status, error, message, path, requestId.
- Maneja validación, excepciones de dominio, AccessDenied y excepciones generales.

## 5. Controladores por rol

- `AdminController` (`/api/v1/admin`) — registro de usuarios.
- `SellerController` (`/api/v1/seller`) — creación de productos.
- `BuyerController` (`/api/v1/buyer`) — creación/consulta/transiciones de pedidos.

## 6. Compilación y tests

- `mvn clean compile` → BUILD SUCCESS (104 fuentes).
- `mvn test` → BUILD SUCCESS (6/6 tests).

## 7. Archivos creados

DTOs, mappers, `GlobalExceptionHandler`, `AdminController`, `SellerController`, `BuyerController`.
Modificados: `SecurityConfig` (EnableMethodSecurity), `OrderUseCase`/`OrderService` (findById).
