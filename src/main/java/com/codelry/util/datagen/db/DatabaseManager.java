package com.codelry.util.datagen.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class DatabaseManager {
  private static final Logger LOGGER = LogManager.getLogger(DatabaseManager.class);
  private static DatabaseManager instance;
  private static volatile Connection conn;

  private DatabaseManager() {}

  public static DatabaseManager init() {
    if (instance == null) {
      instance = new DatabaseManager();
      instance.setup();
    }
    return instance;
  }

  public void setup() {
    try {
      conn = DriverManager.getConnection("jdbc:sqlite::memory:");
      cacheDatabase();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public void cacheDatabase() {
    try {
      URL sourceDb = DatabaseManager.class.getClassLoader().getResource("data/source.db");
      Statement stmt = conn.createStatement();
      stmt.executeUpdate("restore from " + Objects.requireNonNull(sourceDb).getPath());
      stmt.close();
    } catch (SQLException | NullPointerException e) {
      throw new RuntimeException(e);
    }
  }

  public static Connection getConnection() {
    return conn;
  }
}
