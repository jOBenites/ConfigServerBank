package com.bank.configserver;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Verifica que el Config Server levanta y sirve las propiedades del config-repo
 * nativo: la URI de MongoDB propia de cada microservicio y las comunes (Kafka, Redis).
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConfigServerApplicationTests {

    /** Cliente HTTP de prueba contra el server levantado en puerto aleatorio. */
    private final TestRestTemplate restTemplate;

    @Autowired
    ConfigServerApplicationTests(TestRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Test
    void contextLoads() {
    }

    @Test
    void servesMongoUriForEveryService() {
        Map.of(
            "ms-customer", "customer_db",
            "ms-account", "account_db",
            "ms-credit", "credit_db",
            "ms-creditcard", "creditcard_db",
            "ms-debitcard", "debitcard_db",
            "ms-debt", "debt_db",
            "ms-wallet", "wallet_db",
            "ms-report", "report_db",
            "auth-service", "auth_db"
        ).forEach((app, database) -> {
            ResponseEntity<String> response = restTemplate.getForEntity("/" + app + "/default", String.class);
            assertThat(response.getStatusCode()).as(app + " responde 200").isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).as(app + " expone su database").contains(database);
        });
    }

    @Test
    void servesCommonProperties() {
        ResponseEntity<String> response = restTemplate.getForEntity("/ms-customer/default", String.class);
        assertThat(response.getBody())
            .contains("localhost:9092")
            .contains("devpassword")
            .contains("defaultZone");
    }
}
