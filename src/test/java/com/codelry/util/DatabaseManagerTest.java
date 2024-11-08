package com.codelry.util;

import com.codelry.util.datagen.db.DatabaseManager;
import com.codelry.util.datagen.db.NameRecord;
import com.codelry.util.datagen.db.RecordNotFound;
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
  public void testDatabaseManager() throws RecordNotFound {
    LOGGER.info("Starting test");
    DatabaseManager.init();
    NameRecord record = DatabaseManager.getNameById(1);
    LOGGER.info("First name: {} Last name: {}", record.first, record.last);
  }
}
