package com.codelry.util.datagen;

import com.codelry.util.datagen.db.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;

public class DatabaseManagerTest {
  private static final Logger LOGGER = LogManager.getLogger(DatabaseManagerTest.class);

  @Test
  public void testDatabaseManager() {
    LOGGER.info("Starting test");
    DatabaseManager databaseManager = DatabaseManager.getInstance();
    NameRecord name = databaseManager.getNameById(1);
    LOGGER.info("First Name: {} Last Name: {}", name.first, name.last);
    AddressRecord address = databaseManager.getAddressById(1);
    LOGGER.info("Address Street: {} Zip: {}", address.street, address.zip);
    List<String> codes = databaseManager.getAreaCodesByState("CA");
    LOGGER.info("Area Code: {}", codes.get(0));
    for (Map.Entry<String, List<String>> entry : DatabaseManager.areaCodeList.entrySet()) {
      LOGGER.info("State: {} Codes: {}", entry.getKey(), String.join(",", entry.getValue()));
    }
  }
}
