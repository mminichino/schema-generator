package com.codelry.util;

import com.codelry.util.internal.GenerateIdentityData;
import org.junit.jupiter.api.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddressDataTest {
  private static final Logger LOGGER = LogManager.getLogger(AddressDataTest.class);

  @Test
  public void testAddressGeneration() {
    LOGGER.info("Starting test");
    GenerateIdentityData gen = new GenerateIdentityData();
    gen.generate(10);
  }
}
