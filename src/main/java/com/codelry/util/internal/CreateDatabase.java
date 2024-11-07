package com.codelry.util.internal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDatabase {
  private static final Logger LOGGER = LogManager.getLogger(CreateDatabase.class);
  private static final String NAMES_TABLE = "CREATE TABLE IF NOT EXISTS users " +
                                            "(id INTEGER PRIMARY KEY, " +
                                            "first TEXT, " +
                                            "last TEXT, " +
                                            "gender TEXT)";

  public CreateDatabase() {}

  public void init() {
    String url = "jdbc:sqlite:src/main/resources/data/source.db";

    LOGGER.info("Initializing database");
    try (Connection conn = DriverManager.getConnection(url)) {
      Statement stmt = conn.createStatement();
      stmt.execute(NAMES_TABLE);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
