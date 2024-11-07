package com.codelry.util;

import com.codelry.util.internal.CreateDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

public class DatabaseCreateTest {
  private static final Logger LOGGER = LogManager.getLogger(DatabaseCreateTest.class);

  @Test
  public void testDatabaseCreation() {
    LOGGER.info("Starting test");
    runMain();
  }

  public static void runMain() {
    String [] args = new String[0];
    CreateDatabase.main(args);
  }
}
