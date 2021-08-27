package ru.hiddenproject.feelmeserver;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@FlywayTest
@AutoConfigureEmbeddedDatabase(beanName = "dataSource")
class FeelMeServerApplicationTests {

	@Test
	void contextLoads() {
	}

}
