package com.codelry.util.datagen;

import com.codelry.util.datagen.db.AddressRecord;
import com.codelry.util.datagen.db.NameRecord;
import com.codelry.util.datagen.randomizer.Randomizer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

public class RandomizerTest {
  private static final Logger LOGGER = LogManager.getLogger(RandomizerTest.class);

  @Test
  public void testRandomizer1() {
    LOGGER.info("Starting test");
    Randomizer randomizer = new Randomizer();
    String firstName = randomizer.randomFirstName();
    String lastName = randomizer.randomLastName();
    String fullName = randomizer.randomFullName();
    LOGGER.info("First name: {}", firstName);
    LOGGER.info("Last name: {}", lastName);
    LOGGER.info("Full name: {}", fullName);
    NameRecord record = randomizer.randomNameRecord();
    LOGGER.info("Full Name: {} Email: {}", record.fullName(), record.emailAddress());
    AddressRecord address = randomizer.randomAddressRecord();
    String phone = randomizer.randomPhoneNumber(address.state);
    LOGGER.info("Address: {} {}, {}, {} {}", address.number, address.street, address.city, address.state, address.zip);
    LOGGER.info("Phone number: {}", phone);
  }
}
