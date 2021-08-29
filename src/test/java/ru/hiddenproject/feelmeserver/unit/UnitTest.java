package ru.hiddenproject.feelmeserver.unit;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AutoConfigureEmbeddedDatabase(beanName = "dataSource")
public abstract class UnitTest {
    protected Logger logger = LoggerFactory.getLogger(UnitTest.class);
}
