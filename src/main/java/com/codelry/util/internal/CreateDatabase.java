package com.codelry.util.internal;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.sql.*;
import java.util.List;

public class CreateDatabase {
  private static final Logger LOGGER = LogManager.getLogger(CreateDatabase.class);
  private static final String url = "jdbc:sqlite:src/main/resources/data/source.db";
  private static final String NAMES_TABLE = "CREATE TABLE IF NOT EXISTS names " +
                                            "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                            "first TEXT, " +
                                            "last TEXT, " +
                                            "gender TEXT)";
  private static final String ADDRESS_TABLE = "CREATE TABLE IF NOT EXISTS addresses " +
                                              "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                              "number TEXT, " +
                                              "street TEXT, " +
                                              "city TEXT, " +
                                              "state TEXT, " +
                                              "zip TEXT)";
  private static final String AREA_CODE_TABLE = "CREATE TABLE IF NOT EXISTS areacodes " +
                                                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                "code TEXT, " +
                                                "prefix TEXT, " +
                                                "state TEXT)";

  public CreateDatabase() {}

  public static void main(String[] args) {
    Options options = new Options();
    CommandLine cmd = null;

    Option nameOpt = new Option("n", "names", false, "Names");
    Option addressOpt = new Option("a", "address", false, "Addresses");
    Option phoneOpt = new Option("p", "phone", false, "Phones");

    nameOpt.setRequired(false);
    addressOpt.setRequired(false);
    phoneOpt.setRequired(false);

    options.addOption(nameOpt);
    options.addOption(addressOpt);
    options.addOption(phoneOpt);

    CommandLineParser parser = new DefaultParser();
    HelpFormatter formatter = new HelpFormatter();

    try {
      cmd = parser.parse(options, args);
    } catch (ParseException e) {
      System.out.println(e.getMessage());
      formatter.printHelp("simulator", options);
      System.exit(1);
    }

    LOGGER.info("Initializing database");
    try (Connection conn = DriverManager.getConnection(url)) {
      Statement stmt = conn.createStatement();
      stmt.execute(NAMES_TABLE);
      stmt.execute(ADDRESS_TABLE);
      stmt.execute(AREA_CODE_TABLE);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    if (cmd.hasOption("names")) {
      populateNamesTable(10_000);
    } else if (cmd.hasOption("address")) {
      populateAddressTable(10_000);
    }
  }

  public static void populateNamesTable(int records) {
    int batch = 100;
    int count = (int) (Math.ceil((double) records / batch)) * batch;
    int iterations = count / batch;

    GenerateIdentityData gen = new GenerateIdentityData();

    LOGGER.info("Generating random name data");
    List<JsonNode> nameList = gen.generateNames(iterations, batch);

    LOGGER.info("Inserting name data into the database");
    try (Connection conn = DriverManager.getConnection(url)) {
      for (JsonNode name : nameList) {
        String sql = "INSERT INTO names(first,last,gender) VALUES(?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, name.get("firstName").asText());
        stmt.setString(2, name.get("lastName").asText());
        stmt.setString(3, name.get("gender").asText());
        stmt.executeUpdate();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static void populateAddressTable(int records) {
    int batch = 100;
    int count = (int) (Math.ceil((double) records / batch)) * batch;
    int iterations = count / batch;

    GenerateIdentityData gen = new GenerateIdentityData();

    LOGGER.info("Generating random address data");
    List<JsonNode> addressList = gen.generateAddresses(iterations, batch);

    LOGGER.info("Inserting address data into the database");
    try (Connection conn = DriverManager.getConnection(url)) {
      for (JsonNode address : addressList) {
        String sql = "INSERT INTO addresses(number,street,city,state,zip) VALUES(?,?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, address.get("number").asText());
        stmt.setString(2, address.get("street").asText());
        stmt.setString(3, address.get("city").asText());
        stmt.setString(4, address.get("state").asText());
        stmt.setString(5, address.get("zipCode").asText());
        stmt.executeUpdate();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}