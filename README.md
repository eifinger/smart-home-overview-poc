# smart-home-overview-poc

Spring Boot 2 Application showcasing a smart home rest endpoint
while using [Spring Webflux](https://spring.io/reactive),
[R2DBC](https://spring.io/projects/spring-data-r2dbc) and
[springdoc-openapi](https://github.com/springdoc/springdoc-openapi)

## Usage

The application is packaged and published as a docker container. If you have Docker installed you can run it locally
and use curl or postman to use it.

````shell
docker run -p 8080:8080 -d ghcr.io/eifinger/smart-home-overview-poc:latest
curl --location --request GET 'http://localhost:8080/overview'
````

You can also use the Swagger-UI under http://localhost:8080/swagger-ui.html
to see the available endpoints to create homes, rooms and thermostats
## Testing the application

All tests are e2e/integration tests which test against [Testcontainers](https://www.testcontainers.org/).

This provides the benefit that the whole application with serializing, dependencies,... is tested.

When the application grows in complexity this approach should be revisited.

````shell
./gradlew test
````

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.9/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.7.9/gradle-plugin/reference/html/#build-image)
* [Testcontainers Postgres Module Reference Guide](https://www.testcontainers.org/modules/databases/postgres/)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.7.9/reference/htmlsingle/#using.devtools)
* [Testcontainers](https://www.testcontainers.org/)
* [Spring Configuration Processor](https://docs.spring.io/spring-boot/docs/2.7.9/reference/htmlsingle/#appendix.configuration-metadata.annotation-processor)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/2.7.9/reference/htmlsingle/#actuator)
* [Prometheus](https://docs.spring.io/spring-boot/docs/2.7.9/reference/htmlsingle/#actuator.metrics.export.prometheus)
* [Spring Reactive Web](https://docs.spring.io/spring-boot/docs/2.7.9/reference/htmlsingle/#web.reactive)
* [Flyway Migration](https://docs.spring.io/spring-boot/docs/2.7.9/reference/htmlsingle/#howto.data-initialization.migration-tool.flyway)
