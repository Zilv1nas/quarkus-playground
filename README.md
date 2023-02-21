# quarkus-playground

This project uses Quarkus, the Supersonic Subatomic Java Framework.

It provides a CRUD for Users API and it supports these features:
- JSON logging (for prod profile)
- Distributed tracing
- Application configuration
- Prometheus metrics
- CQRS using Kafka as event bus
- Full text search with ElasticSearch
- PostgreSQL for users storage
- Redis for users cache
- Open API exposed via swagger ui

Accessible endpoints:
- Dev console: http://localhost:8080/q/dev/
- Swagger UI: http://localhost:8080/q/swagger-ui/
- Prometheus metrics: http://localhost:8080/q/metrics
- Health endpoint: http://localhost:8080/q/health
- Readiness endpoint: http://localhost:8080//q/health/ready
- Readiness endpoint: http://localhost:8080//q/health/live
- Jaeger UI (tracing): http://localhost:16686/search (docker compose must be started to access tracing info)

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/.

## Running the application in dev mode
First, start docker compose:
```shell script
docker-compose up -d
```
It will boot up Jaeger tracing tool.

Then, You can run your application in dev mode that enables live coding using:
```shell script
./gradlew quarkusDev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./gradlew build
```
It produces the `quarkus-run.jar` file in the `build/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `build/quarkus-app/lib/` directory.

The application is now runnable using `java -jar build/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./gradlew build -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar build/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./gradlew build -Dquarkus.package.type=native
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./gradlew build -Dquarkus.package.type=native -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./build/quarkus-playground-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/gradle-tooling.

## Related Guides

- RESTEasy Reactive ([guide](https://quarkus.io/guides/resteasy-reactive)): A JAX-RS implementation utilizing build time processing and Vert.x. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it.
- Kotlin ([guide](https://quarkus.io/guides/kotlin)): Write your services in Kotlin

## Provided Code

### RESTEasy Reactive

Easily start your Reactive RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
