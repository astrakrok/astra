package com.example.astraapi.config;

import com.github.database.rider.core.api.dataset.DataSet;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class BaseTest {
    private static final String DB_NAME = "astra";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";
    private static final String IMAGE_VERSION = "postgres:14";

    private static final PostgreSQLContainer<?> CONTAINER = new PostgreSQLContainer<>(IMAGE_VERSION)
            .withDatabaseName(DB_NAME)
            .withUsername(DB_USER)
            .withPassword(DB_PASSWORD);

    @DynamicPropertySource
    public static void registerPostgreSQLProperties(DynamicPropertyRegistry registry) {
        CONTAINER.start();

        registry.add("spring.datasource.url", CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", CONTAINER::getUsername);
        registry.add("spring.datasource.password", CONTAINER::getPassword);
    }

    @DataSet(
            cleanBefore = true,
            executeScriptsBefore = "restart-sequences.sql",
            skipCleaningFor = "flyway_schema_history")
    @BeforeEach
    public void cleanupDb() {}
}
