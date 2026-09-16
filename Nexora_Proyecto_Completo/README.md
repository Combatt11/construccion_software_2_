# Nexora — Plataforma Marketplace

Proyecto académico de Construcción de Software 2.

Nexora es una plataforma marketplace que contempla compradores, vendedores, administradores,
operadores logísticos y supervisores. El proyecto cubre usuarios, productos, categorías,
bodegas, inventario, carritos, pedidos, facturación, envíos, devoluciones y reembolsos.

## Tecnologías

- Java 17
- Maven
- JUnit 5 para pruebas
- VS Code con Extension Pack for Java

## Estructura

```text
nexora/
├── SDD/                         # Documentación propia de Nexora
├── src/
│   ├── main/java/com/nexora/
│   │   ├── domain/              # Entidades del dominio
│   │   ├── enums/               # Estados, tipos y roles
│   │   ├── repository/          # Persistencia temporal en memoria
│   │   ├── service/             # Reglas y casos de uso principales
│   │   └── Main.java             # Ejecución de demostración
│   └── test/java/com/nexora/    # Pruebas de reglas principales
├── pom.xml
├── .gitignore
└── LICENSE
```

## Ejecutar en VS Code

1. Abrir la carpeta raíz `Nexora_Proyecto_Completo` en VS Code.
2. Tener instalado JDK 17 o superior.
3. Instalar Extension Pack for Java.
4. Abrir `src/main/java/com/nexora/Main.java`.
5. Presionar **Run Java**.

## Ejecutar pruebas

Desde la terminal ubicada en la carpeta raíz:

```bash
mvn test
```

## Decisiones de diseño para Nexora

Para esta primera implementación se usa persistencia en memoria y una arquitectura sencilla
separada en dominio, servicios y repositorio. Esto permite probar las reglas de negocio sin
introducir todavía una base de datos o un framework web.

Las clases y servicios implementan las reglas que ya fueron definidas para Nexora, especialmente:
identificadores y correos únicos, un rol por usuario, incorporación de vendedores por administrador,
inventario no negativo, restricción de inventario dañado, ciclo de estados del pedido y bloqueo de
modificaciones cuando el pedido está finalizado.
