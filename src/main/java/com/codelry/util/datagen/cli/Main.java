package com.codelry.util.datagen.cli;

import com.codelry.util.datagen.DataLoad;
import com.codelry.util.datagen.drivers.Couchbase;
import com.codelry.util.datagen.drivers.JsonFile;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class Main {
  private static final Logger LOGGER = LogManager.getLogger(Main.class);
  public static final String GENERATOR_SCHEMA_NAME = "generator.schema";
  public static final String GENERATOR_SCHEMA_SIZE = "generator.size";
  public static final String GENERATOR_DRIVER = "generator.driver";
  public static final String GENERATOR_DEFAULT_SCHEMA = "customers";

  public static void main(String[] args) {
    Properties properties = argsToProperties(args);
    DataLoad driver;

    String schema = properties.getProperty(GENERATOR_SCHEMA_NAME, GENERATOR_DEFAULT_SCHEMA);
    String driverName = properties.getProperty(GENERATOR_DRIVER, "couchbase");
    long schemaSize = Long.parseLong(properties.getProperty(GENERATOR_SCHEMA_SIZE, "1"));

    if (driverName.equalsIgnoreCase("couchbase")) {
      LOGGER.info("Using Couchbase driver");
      driver = new Couchbase();
    } else {
      LOGGER.info("Using JsonFile driver");
      driver = new JsonFile();
    }

    LOGGER.info("Generating records for schema {} and size {}", schema, schemaSize);
    driver.init(properties, schema, 1, schemaSize);
    driver.prepare();
    driver.generate();
    driver.cleanup();

    System.exit(0);
  }

  public static Properties argsToProperties(String[] args) {
    Properties properties = new Properties();
    Options options = new Options();
    CommandLine cmd = null;

    Option hostnameOpt = Option.builder("h").longOpt("hostname").hasArg().desc("Hostname")
        .required(false).build();
    Option usernameOpt = Option.builder("u").longOpt("username").hasArg().desc("Username")
        .required(false).build();
    Option passwordOpt = Option.builder("p").longOpt("password").hasArg().desc("Password")
        .required(false).build();
    Option projectOpt = Option.builder("p").longOpt("project").hasArg().desc("Project")
        .required(false).build();
    Option databaseOpt = Option.builder("d").longOpt("database").hasArg().desc("Database")
        .required(false).build();
    Option tokenOpt = Option.builder("t").longOpt("token").hasArg().desc("Token")
        .required(false).build();
    Option emailOpt = Option.builder("e").longOpt("email").hasArg().desc("Email")
        .required(false).build();
    Option schemaOpt = Option.builder("s").longOpt("schema").hasArg().desc("Schema")
        .required(false).build();
    Option countOpt = Option.builder("n").longOpt("number").hasArg().desc("Number of records")
        .required(false).build();
    Option driverOpt = Option.builder("d").longOpt("driver").hasArg().desc("Driver")
        .required(false).build();
    Option configOpt = Option.builder("c").longOpt("config").hasArg().desc("Config file")
        .required(false).build();

    options.addOption(hostnameOpt);
    options.addOption(usernameOpt);
    options.addOption(passwordOpt);
    options.addOption(projectOpt);
    options.addOption(databaseOpt);
    options.addOption(tokenOpt);
    options.addOption(emailOpt);
    options.addOption(schemaOpt);
    options.addOption(countOpt);
    options.addOption(driverOpt);

    options.addOption(configOpt);

    CommandLineParser parser = new DefaultParser();
    HelpFormatter formatter = new HelpFormatter();

    try {
      cmd = parser.parse(options, args);
    } catch (ParseException e) {
      System.out.println(e.getMessage());
      formatter.printHelp("generator", options);
      System.exit(1);
    }

    CapellaConfig capellaConfig = new CapellaConfig();
    capellaConfig.setProject(cmd.getOptionValue("project"));
    capellaConfig.setDatabase(cmd.getOptionValue("database"));
    capellaConfig.setToken(cmd.getOptionValue("token"));
    capellaConfig.setUserEmail(cmd.getOptionValue("email"));
    capellaConfig.setUserId(cmd.getOptionValue("user"));
    capellaConfig.setProjectId(cmd.getOptionValue("project-id"));
    capellaConfig.setDatabaseId(cmd.getOptionValue("database-id"));
    properties.putAll(capellaConfig.toProperties());

    CouchbaseConfig couchbaseConfig = new CouchbaseConfig();
    couchbaseConfig.setHostname(cmd.getOptionValue("hostname"));
    couchbaseConfig.setUsername(cmd.getOptionValue("username"));
    couchbaseConfig.setPassword(cmd.getOptionValue("password"));
    properties.putAll(couchbaseConfig.toProperties());

    GeneratorConfig generatorConfig = new GeneratorConfig();
    generatorConfig.setSchemaName(cmd.getOptionValue("schema"));
    generatorConfig.setSchemaSize(cmd.getOptionValue("number"));
    generatorConfig.setDriver(cmd.getOptionValue("driver", "couchbase"));
    properties.putAll(generatorConfig.toProperties());

    Properties configProperties = yamlToProperties(cmd.getOptionValue("config"));
    properties.putAll(configProperties);

    return properties;
  }

  @SuppressWarnings("unchecked")
  public static Properties yamlToProperties(String yaml) {
    Properties properties = new Properties();

    if (yaml != null) {
      LOGGER.debug("Reading config file: {}", yaml);
      ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
      try {
        Object config = yamlMapper.readValue(new File(yaml), Object.class);
        if (config instanceof Map) {
          Map<String, Object> data = (Map<String, Object>) config;
          CapellaConfig capellaConfig = yamlMapper.convertValue(data.get("capella"), CapellaConfig.class);
          CouchbaseConfig couchbaseConfig = yamlMapper.convertValue(data.get("couchbase"), CouchbaseConfig.class);
          GeneratorConfig generatorConfig = yamlMapper.convertValue(data.get("generator"), GeneratorConfig.class);
          properties.putAll(capellaConfig.toProperties());
          properties.putAll(couchbaseConfig.toProperties());
          properties.putAll(generatorConfig.toProperties());
        }
      } catch (IOException e) {
        throw new RuntimeException("Can not read config file " + yaml, e);
      }
    }

    return properties;
  }
}
