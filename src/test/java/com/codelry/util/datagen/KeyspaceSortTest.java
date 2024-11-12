package com.codelry.util.datagen;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.util.*;

public class KeyspaceSortTest {
  private static final Logger LOGGER = LogManager.getLogger(KeyspaceSortTest.class);

  @Test
  public void sortTest() {
    List<String> keyspaceList = List.of(
        "data._default._default",
        "data._default.items",
        "data.data.customers",
        "data.data.items",
        "data.data.inventory",
        "object.info.values"
    );
    Set<String> buckets = new HashSet<>();
    Map<String, Set<String>> scopes = new HashMap<>();
    Map<String, Set<String>> collections = new HashMap<>();

    for (String keyspace : keyspaceList) {
      List<String> keys = Arrays.asList(keyspace.split("\\."));
      if (keys.size() != 3) {
        throw new RuntimeException("Invalid keyspace: " + keyspace);
      }
      buckets.add(keys.get(0));
      if (!scopes.containsKey(keys.get(0))) {
        scopes.put(keys.get(0), new HashSet<>());
      }
      scopes.get(keys.get(0)).add(keys.get(1));
      if (!collections.containsKey(keys.get(1))) {
        collections.put(keys.get(1), new HashSet<>());
      }
      collections.get(keys.get(1)).add(keys.get(2));
    }
    for (String bucket : buckets) {
      LOGGER.info("Bucket: {}", bucket);
      for (String scope : scopes.get(bucket)) {
        LOGGER.info("Scope: {}", scope);
        for (String collection : collections.get(scope)) {
          LOGGER.info("Collection: {}", collection);
        }
      }
    }
  }
}
