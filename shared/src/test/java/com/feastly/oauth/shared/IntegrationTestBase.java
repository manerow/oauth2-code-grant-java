package com.feastly.oauth.shared;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.micronaut.test.support.TestPropertyProvider;
import jakarta.inject.Inject;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@MicronautTest(environments = "test")
@Testcontainers(disabledWithoutDocker = true)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@Sql(scripts = "classpath:sql/seed-data.sql", phase = Sql.Phase.BEFORE_EACH)
public class IntegrationTestBase implements TestPropertyProvider {

    @Inject
    EmbeddedApplication<?> application;

    @Container
    static MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:5.7");


    @Override
    public @NonNull Map<String, String> getProperties() {
        if (!mysqlContainer.isRunning()) {
            mysqlContainer.start();
        }
        return Map.of("datasources.default.url", mysqlContainer.getJdbcUrl(),
            "datasources.default.username", mysqlContainer.getUsername(),
            "datasources.default.password", mysqlContainer.getPassword());
    }

    @Test
    void testItWorks() {
        Assertions.assertTrue(application.isRunning());
    }
}
