package com.codelry.util.datagen.cli;

import com.codelry.util.datagen.drivers.Couchbase;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class Main {
  private static final Logger LOGGER = LogManager.getLogger(Main.class);
  public static final String CAPELLA_PROJECT_NAME = "capella.project.name";
  public static final String CAPELLA_PROJECT_ID = "capella.project.id";
  public static final String CAPELLA_DATABASE_NAME = "capella.database.name";
  public static final String CAPELLA_DATABASE_ID = "capella.database.id";
  public static final String CAPELLA_TOKEN = "capella.token";
  public static final String CAPELLA_USER_EMAIL = "capella.user.email";
  public static final String CAPELLA_USER_ID = "capella.user.id";
  public static final String COUCHBASE_HOSTNAME = "couchbase.hostname";
  public static final String COUCHBASE_USERNAME = "couchbase.username";
  public static final String COUCHBASE_PASSWORD = "couchbase.password";
  public static final String GENERATOR_SCHEMA_NAME = "generator.schema";
  public static final String GENERATOR_SCHEMA_SIZE = "generator.size";
  public static final String GENERATOR_DEFAULT_SCHEMA = "customers";

  public static void main(String[] args) {
    Properties properties = argsToProperties(args);

    String schema = properties.getProperty(GENERATOR_SCHEMA_NAME, GENERATOR_DEFAULT_SCHEMA);

    Couchbase driver = new Couchbase();
    driver.init(properties, schema, 1, 1000);
    driver.prepare();
    driver.generate();
    driver.cleanup();
  }

  public static Properties argsToProperties(String[] args) {
    Properties properties = new Properties();
    Options options = new Options();
    CommandLine cmd = null;
    String hostname;
    String username;
    String password;
    String project;
    String database;
    String token;
    String email;
    String schema;

    String configFile = null;

    Option hostnameOpt = new Option("h", "hostname", true, "Hostname");
    Option usernameOpt = new Option("u", "username", true, "Username");
    Option passwordOpt = new Option("p", "password", true, "Password");
    Option projectOpt = new Option("P", "project", true, "Project");
    Option databaseOpt = new Option("D", "database", true, "Database");
    Option tokenOpt = new Option("T", "token", true, "Token");
    Option emailOpt = new Option("E", "email", true, "Email");
    Option schemaOpt = new Option("s", "schema", true, "Schema");
    Option countOpt = new Option("c", "count", true, "Count");

    Option configOpt = new Option("c", "config", true, "Config File");

    hostnameOpt.setRequired(false);
    usernameOpt.setRequired(false);
    passwordOpt.setRequired(false);
    projectOpt.setRequired(false);
    databaseOpt.setRequired(false);
    tokenOpt.setRequired(false);
    emailOpt.setRequired(false);
    schemaOpt.setRequired(false);
    countOpt.setRequired(false);

    configOpt.setRequired(false);

    options.addOption(hostnameOpt);
    options.addOption(usernameOpt);
    options.addOption(passwordOpt);
    options.addOption(projectOpt);
    options.addOption(databaseOpt);
    options.addOption(tokenOpt);
    options.addOption(emailOpt);
    options.addOption(schemaOpt);
    options.addOption(countOpt);

    options.addOption(configOpt);

    CommandLineParser parser = new DefaultParser();
    HelpFormatter formatter = new HelpFormatter();

    try {
      cmd = parser.parse(options, args);
      configFile = cmd.hasOption("config") ? cmd.getOptionValue("config") : null;
    } catch (ParseException e) {
      System.out.println(e.getMessage());
      formatter.printHelp("generator", options);
      System.exit(1);
    }

    if (configFile != null) {
      LOGGER.debug("Reading config file: {}", cmd.getOptionValue("config"));
      return yamlToProperties(cmd.getOptionValue("config"));
    } else {
      if (cmd.hasOption("hostname")) {
        hostname = cmd.getOptionValue("hostname");
        properties.setProperty(COUCHBASE_HOSTNAME, hostname);
      }
      if (cmd.hasOption("username")) {
        username = cmd.getOptionValue("username");
        properties.setProperty(COUCHBASE_USERNAME, username);
      }
      if (cmd.hasOption("password")) {
        password = cmd.getOptionValue("password");
        properties.setProperty(COUCHBASE_PASSWORD, password);
      }
      if (cmd.hasOption("project")) {
        project = cmd.getOptionValue("project");
        properties.setProperty(CAPELLA_PROJECT_NAME, project);
      }
      if (cmd.hasOption("database")) {
        database = cmd.getOptionValue("database");
        properties.setProperty(CAPELLA_DATABASE_NAME, database);
      }
      if (cmd.hasOption("token")) {
        token = cmd.getOptionValue("token");
        properties.setProperty(CAPELLA_TOKEN, token);
      }
      if (cmd.hasOption("email")) {
        email = cmd.getOptionValue("email");
        properties.setProperty(CAPELLA_USER_EMAIL, email);
      }
      if (cmd.hasOption("schema")) {
        schema = cmd.getOptionValue("schema");
        properties.setProperty(GENERATOR_SCHEMA_NAME, schema);
      }
      if (cmd.hasOption("count")) {
        String size = cmd.getOptionValue("count");
        properties.setProperty(GENERATOR_SCHEMA_SIZE, size);
      }
    }

    return properties;
  }

  public static Properties yamlToProperties(String yaml) {
    Properties properties = new Properties();

    ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
    try {
      Object config = yamlMapper.readValue(new File(yaml), Object.class);
      ObjectMapper jsonMapper = new ObjectMapper();
      JsonNode json = jsonMapper.convertValue(config, JsonNode.class);
      LOGGER.debug("Config contents:\n{}", json.toPrettyString());
      if (json.has("capella")) {
        for (Iterator<Map.Entry<String, JsonNode>> it = json.get("capella").fields(); it.hasNext(); ) {
          Map.Entry<String, JsonNode> entry = it.next();
          switch (entry.getKey()) {
            case "project":
              properties.setProperty(CAPELLA_PROJECT_NAME, entry.getValue().asText());
              break;
            case "projectId":
              properties.setProperty(CAPELLA_PROJECT_ID, entry.getValue().asText());
              break;
            case "database":
              properties.setProperty(CAPELLA_DATABASE_NAME, entry.getValue().asText());
              break;
            case "databaseId":
              properties.setProperty(CAPELLA_DATABASE_ID, entry.getValue().asText());
              break;
            case "token":
              properties.setProperty(CAPELLA_TOKEN, entry.getValue().asText());
              break;
            case "userEmail":
              properties.setProperty(CAPELLA_USER_EMAIL, entry.getValue().asText());
              break;
            case "userId":
              properties.setProperty(CAPELLA_USER_ID, entry.getValue().asText());
              break;
          }
        }
      }
      if (json.has("couchbase")) {
        for (Iterator<Map.Entry<String, JsonNode>> it = json.get("couchbase").fields(); it.hasNext(); ) {
          Map.Entry<String, JsonNode> entry = it.next();
          switch (entry.getKey()) {
            case "hostname":
              properties.setProperty(COUCHBASE_HOSTNAME, entry.getValue().asText());
              break;
            case "username":
              properties.setProperty(COUCHBASE_USERNAME, entry.getValue().asText());
              break;
            case "password":
              properties.setProperty(COUCHBASE_PASSWORD, entry.getValue().asText());
              break;
          }
        }
      }
      if (json.has("generator")) {
        for (Iterator<Map.Entry<String, JsonNode>> it = json.get("generator").fields(); it.hasNext(); ) {
          Map.Entry<String, JsonNode> entry = it.next();
          switch (entry.getKey()) {
            case "schemaName":
              properties.setProperty(GENERATOR_SCHEMA_NAME, entry.getValue().asText());
              break;
            case "schemaSize":
              properties.setProperty(GENERATOR_SCHEMA_SIZE, entry.getValue().asText());
              break;
          }
        }
      }
    } catch (IOException e) {
      throw new RuntimeException("Can not read config file " + yaml, e);
    }

    return properties;
  }
}
