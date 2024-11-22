package com.codelry.util.datagen.cli;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Properties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeneratorConfig {
  public static final String GENERATOR_SCHEMA_NAME = "generator.schema";
  public static final String GENERATOR_SCHEMA_SIZE = "generator.size";
  public static final String GENERATOR_DRIVER = "generator.driver";
  private String schemaName;
  private String schemaSize;
  private String driver;

  public Properties toProperties() {
    Properties properties = new Properties();
    if (schemaName != null) properties.setProperty(GENERATOR_SCHEMA_NAME, schemaName);
    if (schemaSize != null) properties.setProperty(GENERATOR_SCHEMA_SIZE, schemaSize);
    if (driver != null) properties.setProperty(GENERATOR_DRIVER, driver);
    return properties;}

  public String getSchemaName() {
    return schemaName;
  }

  public void setSchemaName(String schemaName) {
    this.schemaName = schemaName;
  }

  public String getSchemaSize() {
    return schemaSize;
  }

  public void setSchemaSize(String schemaSize) {
    this.schemaSize = schemaSize;
  }

  public String getDriver() {
    return driver;
  }

  public void setDriver(String driver) {
    this.driver = driver;
  }
}
