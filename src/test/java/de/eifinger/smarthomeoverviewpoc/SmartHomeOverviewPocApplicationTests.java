package de.eifinger.smarthomeoverviewpoc;

import de.eifinger.smarthomeoverviewpoc.domain.home.Home;
import de.eifinger.smarthomeoverviewpoc.domain.home.HomeRepository;
import de.eifinger.smarthomeoverviewpoc.domain.room.Room;
import de.eifinger.smarthomeoverviewpoc.domain.room.RoomRepository;
import de.eifinger.smarthomeoverviewpoc.domain.thermostat.Thermostat;
import de.eifinger.smarthomeoverviewpoc.domain.thermostat.ThermostatRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
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

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@AutoConfigureWebTestClient
@ContextConfiguration(initializers = SmartHomeOverviewPocApplicationTests.DataSourceInitializer.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SmartHomeOverviewPocApplicationTests {

	@Autowired
	private WebTestClient webClient;

	@Autowired
	private HomeRepository homeRepository;

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private ThermostatRepository thermostatRepository;

	@Container
	private static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>("postgres:15.2")
        .withDatabaseName("postgres")
        .withUsername("postgres")
        .withPassword("postgres");

	@BeforeEach
	void beforeEach() {
		homeRepository.deleteAll().block();
	}

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

	@Test
	void shouldGetAllHomes() {
		var newHome1 = Home.builder().name("testHome").build();
		var newHome2 = Home.builder().name("secondTestHome").build();
		homeRepository.saveAll(List.of(newHome1, newHome2)).blockLast();
		this.webClient.get().uri("/home").exchange()
				.expectBodyList(Home.class)
				.hasSize(2);
	}

	@Test
	void shouldAssignThermostatToHome() {
		var newHome = Home.builder().name("testHome").build();
		var savedHome = homeRepository.save(newHome).block();

		var newRoom = Room.builder().name("testRoom").homeId(savedHome.getId()).build();
		var savedRoom = roomRepository.save(newRoom).block();

		var newThermostat = Thermostat.builder().name("testThermostat")
				.build();
		var savedThermostat = thermostatRepository.save(newThermostat).block();

		var assignRoomUri = URI.create("/thermostat/%s/assignRoom".formatted(savedThermostat.getId()));
		this.webClient.post().uri(assignRoomUri).bodyValue(savedRoom.getId()).exchange()
				.expectBodyList(Thermostat.class)
				.hasSize(1);

		var searchUri = URI.create("/thermostat?roomId=%s".formatted(savedRoom.getId()));
		var thermostat = this.webClient.get().uri(searchUri).exchange()
				.expectBodyList(Thermostat.class)
				.hasSize(1)
				.returnResult()
				.getResponseBody()
				.get(0);
		assertThat(thermostat.getAssignedRoom()).isEqualTo(savedRoom.getId());
		assertThat(thermostat.getId()).isEqualTo(savedThermostat.getId());
		assertThat(thermostat.getName()).isEqualTo(savedThermostat.getName());

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
