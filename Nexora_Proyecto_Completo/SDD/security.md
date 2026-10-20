# Seguridad — Nexora

- Autenticación stateless con JWT (JJWT 0.12.6).
- Token incluye claims: `sub` (id de usuario), `jti`, `ver` (authTokenVersion), `iat`, `exp`.
- Contraseñas encriptadas con BCrypt.
- `authTokenVersion`: se incrementa en logout (y en cambios sensibles). El filtro JWT compara
  el claim `ver` con la versión actual del usuario en BD; si no coinciden, retorna 403.
- Rutas públicas: `/api/v1/auth/**`, `/actuator/**`. El resto requiere autenticación.
- Roles: BUYER, SELLER, ADMINISTRATOR, LOGISTICS_OPERATOR, SUPERVISOR.
- CORS configurable por origen.
- `X-Request-Id` se genera por petición y se propaga en respuestas y auditoría.
