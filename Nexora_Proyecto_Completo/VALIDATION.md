# Validación — Nexora

Asume la app corriendo en `http://localhost:8080`.

## 1. Registrar usuario

```bash
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"Juan","email":"juan@nexora.com","password":"pass123","role":"BUYER"}'
```

## 2. Login

```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"juan@nexora.com","password":"pass123"}'
```

Guarda el `token` de la respuesta.

## 3. Endpoint protegido

```bash
curl http://localhost:8080/api/v1/buyer/orders/1 \
  -H "Authorization: Bearer <token>"
```

## 4. Logout

```bash
curl -X POST http://localhost:8080/api/v1/auth/logout \
  -H "Authorization: Bearer <token>"
```

## 5. Token rechazado

Repetir el paso 3 con el mismo token debe retornar 403.

## Health

```bash
curl http://localhost:8080/actuator/health
```
