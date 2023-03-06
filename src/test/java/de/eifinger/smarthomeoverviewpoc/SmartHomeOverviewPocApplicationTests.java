package de.eifinger.smarthomeoverviewpoc;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@AutoConfigureWebTestClient
@ContextConfiguration(initializers = SmartHomeOverviewPocApplicationTests.DataSourceInitializer.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SmartHomeOverviewPocApplicationTests {

	@Autowired
	private WebTestClient webClient;

	@Container
	private static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>("postgres:15.2")
        .withDatabaseName("postgres")
        .withUsername("postgres")
        .withPassword("postgres");

	@Test
	void exampleTest() {
		this.webClient.get().uri("/overview").exchange()
				.expectStatus().isOk()
				.expectBody(String.class).isEqualTo("This is where your overview will be");
	}

	@Test
	void shouldCreateHome() {
		this.webClient.put().uri("/home").bodyValue("testHomeName").exchange()
				.expectStatus().isCreated();
	}

	public static class DataSourceInitializer
			implements ApplicationContextInitializer<ConfigurableApplicationContext> {

		@Override
		public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
			TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
					applicationContext,
					"spring.r2dbc.url=" + POSTGRE_SQL_CONTAINER.getJdbcUrl().replace("jdbc", "r2dbc"),
					"spring.r2dbc.username=" + POSTGRE_SQL_CONTAINER.getUsername(),
					"spring.r2dbc.password=" + POSTGRE_SQL_CONTAINER.getPassword(),
					"spring.flyway.url=" + POSTGRE_SQL_CONTAINER.getJdbcUrl(),
					"spring.flyway.username=" + POSTGRE_SQL_CONTAINER.getUsername(),
					"spring.flyway.password=" + POSTGRE_SQL_CONTAINER.getPassword()
			);
		}
	}

}
