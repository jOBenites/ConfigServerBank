# Config Server

Servidor de configuración centralizada. Las URIs de conexión de cada microservicio (Mongo, Kafka, Redis) se sirven vía HTTP — ningún microservicio las hardcodea en `application.yml` local.

## Endpoints

| Endpoint | Descripción |
|---|---|
| `GET /{app}/default` | Propiedades del microservicio `{app}` (Mongo URI específica + comunes). Ej: `/ms-customer/default` |
| `GET /actuator/health` | Estado del server. Responde `{"status":"UP"}` |

## Servicios disponibles

`ms-customer`, `ms-account`, `ms-credit`, `ms-creditcard`, `ms-debitcard`, `ms-debt`, `ms-wallet`, `ms-report`, `auth-service`.

Cada uno sirve su URI de MongoDB con base propia (database-per-service). Todos comparten las comunes de `config-repo/application.yml` (Kafka, Redis, Eureka preparado).

## Levantar

```bash
mvn package -DskipTests
java -jar target/config-server-1.0.0.jar
```

Escucha en el puerto **8888**.

## Backend de configuración

Nativo (classpath). Los YAML están en `src/main/resources/config-repo/`. Si necesitas Git como backend, cambiar `spring.cloud.config.server.native.search-locations` en `application.yml` y apuntar al repo remoto.

## Registro en Eureka

La dependencia está en el pom pero desactivada (`eureka.client.enabled=false`). Activarla en Fase II cuando exista `service-registry`.
