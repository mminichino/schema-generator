package com.codelry.util.datagen;

import com.codelry.util.datagen.drivers.JsonFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Properties;

public class FileDriverTest {
  private static final Logger LOGGER = LogManager.getLogger(FileDriverTest.class);
  private static final String GENERATOR_SCHEMA_PROPERTY = "generator.schema";
  private static final String GENERATOR_DEFAULT_SCHEMA = "customers";
  private static final String propertyFile = "test.file.properties";
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
  public void testDriver1() {
    String schema = properties.getProperty(GENERATOR_SCHEMA_PROPERTY, GENERATOR_DEFAULT_SCHEMA);
    JsonFile driver = new JsonFile();
    driver.init(properties, schema, 1, 30000);
    driver.prepare();
    driver.generate();
    driver.cleanup();
  }
}
