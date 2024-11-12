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
  public void testDatabaseManager() throws RecordNotFound {
    LOGGER.info("Starting test");
    DatabaseManager databaseManager = new DatabaseManager();
    NameRecord name = databaseManager.getNameById(1);
    LOGGER.info("First name: {} Last name: {}", name.first, name.last);
    AddressRecord address = databaseManager.getAddressById(1);
    LOGGER.info("Address street: {} Zip: {}", address.street, address.zip);
    List<AreaCodeRecord> codes = databaseManager.getAreaCodesByState("CA");
    LOGGER.info("Area code: {} State: {}", codes.get(0).code, codes.get(0).state);
    for (Map.Entry<String, Long> entry : databaseManager.areaCodeCountByState.entrySet()) {
      LOGGER.info("State: {} Count: {}", entry.getKey(), entry.getValue());
    }
  }
}
