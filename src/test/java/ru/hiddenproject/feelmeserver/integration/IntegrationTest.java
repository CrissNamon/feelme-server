package ru.hiddenproject.feelmeserver.integration;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.flywaydb.test.annotation.FlywayTest;

@FlywayTest
@AutoConfigureEmbeddedDatabase(beanName = "dataSource")
public abstract class IntegrationTest {
}
