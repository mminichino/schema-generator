package com.codelry.util.internal;

import com.couchbase.lite.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Objects;

public class CreateDatabase {
  private static final Logger LOGGER = LogManager.getLogger(CreateDatabase.class);
  public static Database database;
  public static Collection collection;

  public CreateDatabase() {}

  public void init() {
    String dbPath = "src/main/resources/data";

    LOGGER.info("Initializing database");
    CouchbaseLite.init();
    Database.log.getConsole().setLevel(LogLevel.ERROR);
    DatabaseConfiguration config = new DatabaseConfiguration();
    config.setDirectory(dbPath);
    try {
      database = new Database("source", config);
    } catch (CouchbaseLiteException e) {
      throw new RuntimeException(e);
    }
  }
}
