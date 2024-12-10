package com.codelry.util.datagen;

import com.codelry.util.datagen.db.AddressRecord;
import com.codelry.util.datagen.db.NameRecord;
import com.codelry.util.datagen.randomizer.Randomizer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.util.*;

public class RandomizerTest {
  private static final Logger LOGGER = LogManager.getLogger(RandomizerTest.class);

  public static <K, V extends Comparable<? super V>> LinkedHashMap<K, V> sortByValue(Map<K, V> map) {
    List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
    list.sort(Map.Entry.comparingByValue());

    LinkedHashMap<K, V> result = new LinkedHashMap<>();
    for (Map.Entry<K, V> entry : list) {
      result.put(entry.getKey(), entry.getValue());
    }
    return result;
  }

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
    Map<String, Integer> stateResults = new HashMap<>();
    for (int i = 0; i < 1000; i++) {
      String state = randomizer.randomState();
      if (stateResults.containsKey(state)) {
        stateResults.put(state, stateResults.get(state) + 1);
      } else {
        stateResults.put(state, 1);
      }
    }
    for (Map.Entry<String, Integer> entry : sortByValue(stateResults).entrySet()) {
      LOGGER.info("State: {} Count: {}", entry.getKey(), entry.getValue());
    }
    AddressRecord address = randomizer.randomAddressRecord();
    String phone = randomizer.randomPhoneNumber(address.state);
    LOGGER.info("Address: {} {}, {}, {} {}", address.number, address.street, address.city, address.state, address.zip);
    LOGGER.info("Phone number: {}", phone);
  }
}
