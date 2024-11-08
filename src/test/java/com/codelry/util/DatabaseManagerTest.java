package com.codelry.util;

import com.codelry.util.datagen.db.DatabaseManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManagerTest {
  private static final Logger LOGGER = LogManager.getLogger(DatabaseManagerTest.class);

  @Test
  public void testDatabaseManager() {
    LOGGER.info("Starting test");
    DatabaseManager.init();
    Connection conn = DatabaseManager.getConnection();

    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT * FROM names LIMIT 10");

      while (rs.next()) {
        LOGGER.info(rs.getString("first"));
      }

      rs.close();
      stmt.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
