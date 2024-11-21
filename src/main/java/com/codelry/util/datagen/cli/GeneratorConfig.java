package com.codelry.util.datagen.cli;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Properties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeneratorConfig {
  public static final String GENERATOR_SCHEMA_NAME = "generator.schema";
  public static final String GENERATOR_SCHEMA_SIZE = "generator.size";
  public static final String GENERATOR_DRIVER = "generator.driver";
  private String schemaName;
  private int schemaSize = 10;
  private String driver = "couchbase";

  public Properties toProperties() {
    Properties properties = new Properties();
    properties.setProperty(GENERATOR_SCHEMA_NAME, schemaName);
    properties.setProperty(GENERATOR_SCHEMA_SIZE, String.valueOf(schemaSize));
    properties.setProperty(GENERATOR_DRIVER, driver);
    return properties;}

  public String getSchemaName() {
    return schemaName;
  }

  public void setSchemaName(String schemaName) {
    this.schemaName = schemaName;
  }

  public int getSchemaSize() {
    return schemaSize;
  }

  public void setSchemaSize(int schemaSize) {
    this.schemaSize = schemaSize;
  }

  public String getDriver() {
    return driver;
  }

  public void setDriver(String driver) {
    this.driver = driver;
  }
}
