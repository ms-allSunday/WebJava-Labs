package com.example.spacecatsmarket;


import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
public abstract class AbstractIT {

    private static final int POSTGRES_PORT = 5432;

   @SuppressWarnings("resource")
    static final GenericContainer<?> POSTGRES_CONTAINER =
            new GenericContainer<>("postgres:16-alpine")
                    .withEnv("POSTGRES_DB", "test_db")
                    .withEnv("POSTGRES_USER", "test_user")
                    .withEnv("POSTGRES_PASSWORD", "test_pass")
                    .withExposedPorts(POSTGRES_PORT)
                    .waitingFor(Wait.forLogMessage(".*database system is ready to accept connections.*\\s", 2));

    static {
        POSTGRES_CONTAINER.start();
    }

    @DynamicPropertySource
    static void setupTestContainerProperties(DynamicPropertyRegistry registry) {
     String jdbcUrl = String.format("jdbc:postgresql://%s:%d/test_db",
                POSTGRES_CONTAINER.getHost(),
                POSTGRES_CONTAINER.getMappedPort(POSTGRES_PORT));

        registry.add("spring.datasource.url", () -> jdbcUrl);
        registry.add("spring.datasource.username", () -> "test_user");
        registry.add("spring.datasource.password", () -> "test_pass");

       registry.add("spring.liquibase.url", () -> jdbcUrl);
        registry.add("spring.liquibase.user", () -> "test_user");
        registry.add("spring.liquibase.password", () -> "test_pass");
        registry.add("spring.liquibase.enabled", () -> "true");
    }
}
