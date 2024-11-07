package com.codelry.util.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DatabaseManager {
  private static final Logger LOGGER = LogManager.getLogger(DatabaseManager.class);
  private static DatabaseManager instance;

  private DatabaseManager() {}

  public static DatabaseManager init() {
    if (instance == null) {
      instance = new DatabaseManager();
    }
    return instance;
  }

  public void setup() {
  }
}
