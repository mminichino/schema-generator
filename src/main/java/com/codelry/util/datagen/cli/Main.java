package com.codelry.util.datagen.cli;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
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

  public static void main(String[] args) {
    Properties properties = argsToProperties(args);
  }

  public static Properties argsToProperties(String[] args) {
    Properties properties = new Properties();
    Options options = new Options();
    CommandLine cmd = null;
    String connectString;
    String username;
    String password;
    String project;
    String database;
    String token;
    String email;

    String configFile = null;

    Option connectStringOpt = new Option("C", "connect", true, "Connect String");
    Option usernameOpt = new Option("u", "username", true, "Username");
    Option passwordOpt = new Option("p", "password", true, "Password");
    Option projectOpt = new Option("P", "project", true, "Project");
    Option databaseOpt = new Option("D", "database", true, "Database");
    Option tokenOpt = new Option("T", "token", true, "Token");
    Option emailOpt = new Option("E", "email", true, "Email");

    Option configOpt = new Option("c", "config", true, "Config File");

    connectStringOpt.setRequired(false);
    usernameOpt.setRequired(false);
    passwordOpt.setRequired(false);
    projectOpt.setRequired(false);
    databaseOpt.setRequired(false);
    tokenOpt.setRequired(false);
    emailOpt.setRequired(false);

    configOpt.setRequired(false);

    options.addOption(connectStringOpt);
    options.addOption(usernameOpt);
    options.addOption(passwordOpt);
    options.addOption(projectOpt);
    options.addOption(databaseOpt);
    options.addOption(tokenOpt);
    options.addOption(emailOpt);

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
    } catch (IOException e) {
      throw new RuntimeException("Can not read config file " + yaml, e);
    }

    return properties;
  }
}
