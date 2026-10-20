# API — Nexora

Base URL: `http://localhost:8080`

## Autenticación

- `POST /api/v1/auth/register` (público)
- `POST /api/v1/auth/login` (público)
- `POST /api/v1/auth/logout` (protegido)

Ejemplo login request:
```json
{"email":"u@x.com","password":"pass"}
```
Respuesta:
```json
{"token":"<jwt>","type":"Bearer"}
```

## Admin (`/api/v1/admin`, rol ADMINISTRATOR)

- `POST /users`

## Seller (`/api/v1/seller`, rol SELLER)

- `POST /products`

Ejemplo request:
```json
{"name":"P","description":"D","price":10,"type":"PHYSICAL","categoryId":1}
```

## Buyer (`/api/v1/buyer`, rol BUYER)

- `POST /orders`
- `GET /orders/{id}`
- `POST /orders/{id}/pay`
- `POST /orders/{id}/dispatch`
- `POST /orders/{id}/deliver`
- `POST /orders/{id}/finalize`

Ejemplo crear pedido:
```json
{"buyerId":2,"items":[{"productId":10,"quantity":1}]}
```

## Observabilidad

- `GET /actuator/health`
- `GET /actuator/info`
- `GET /actuator/metrics`

Todas las respuestas de error usan un envelope estándar con `timestamp, status, error, message, path, requestId`.
