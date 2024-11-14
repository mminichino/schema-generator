package com.codelry.util.datagen;

import com.codelry.util.datagen.drivers.Couchbase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.util.Properties;

public class DriverTest {
  private static final Logger LOGGER = LogManager.getLogger(DriverTest.class);
  private static final String propertyFile = "test.properties";
  public static Properties properties;

  @BeforeAll
  public static void setUpBeforeClass() {
    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    properties = new Properties();

    LOGGER.info("Testing with properties file: {}", propertyFile);
    try {
      properties.load(loader.getResourceAsStream(propertyFile));
    } catch (IOException e) {
      LOGGER.debug("can not open properties file: {}", e.getMessage(), e);
    }
  }

  @Test
  public void testDriver() {
    Couchbase driver = new Couchbase();
    driver.init(properties, "customers", 1, 1000);
    driver.prepare();
    driver.generate();
  }
}
