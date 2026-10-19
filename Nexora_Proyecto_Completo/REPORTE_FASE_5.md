# Reporte Fase 5 — Seguridad JWT + Spring Security (Nexora)

## 1. Resumen

Se implementó seguridad stateless con Spring Security + JWT (jjwt 0.12.6), incluyendo invalidación
de tokens por versión (`authTokenVersion`). Se añadieron endpoints de registro, login y logout.
El proyecto compila y los tests pasan (`mvn test` → BUILD SUCCESS, 6/6).

## 2. Dominio y persistencia

- `User` (dominio) ahora incluye `password` (hash) y `authTokenVersion` (default 0), con
  `bumpAuthTokenVersion()`.
- `UserJpaEntity` y `UserJpaMapper` actualizados para persistir ambos campos.

## 3. Seguridad implementada

- `SecurityConfig`: SecurityFilterChain stateless, CSRF deshabilitado, rutas públicas
  `/api/v1/auth/**`, `/actuator/**`, resto protegido; bean `BCryptPasswordEncoder`.
- `JwtService`: genera/valida tokens con claims `sub`, `jti`, `ver`, `iat`, `exp`.
- `JwtAuthenticationFilter`: valida token y compara `ver` con `authTokenVersion` actual; si no
  coinciden retorna 403.
- `CustomUserDetailsService`: carga usuario por email.

## 4. Endpoints de autenticación

- `POST /api/v1/auth/register` (público): registra usuario con contraseña BCrypt.
- `POST /api/v1/auth/login` (público): valida credenciales y devuelve JWT.
- `POST /api/v1/auth/logout` (protegido): incrementa `authTokenVersion`, invalidando el token.

## 5. Compilación y tests

- `mvn clean compile` → BUILD SUCCESS (89 fuentes).
- `mvn test` → BUILD SUCCESS (6/6 tests).

## 6. Archivos creados/modificados

Creados: `JwtService`, `JwtAuthenticationFilter`, `CustomUserDetailsService`, `SecurityConfig`,
`AuthController`. Modificados: `User` (dominio), `UserJpaEntity`, `UserJpaMapper`, `UserService`.
