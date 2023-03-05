package de.eifinger.smarthomeoverviewpoc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SmartHomeOverviewPocApplicationTests {

	@Autowired
	private WebTestClient webClient;

	@Test
	void exampleTest() {
		this.webClient.get().uri("/overview").exchange().expectStatus().isOk()
				.expectBody(String.class).isEqualTo("This is where your overview will be");
	}

}
