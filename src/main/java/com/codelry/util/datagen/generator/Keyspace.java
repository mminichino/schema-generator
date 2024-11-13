package com.codelry.util.datagen.generator;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Arrays;
import java.util.List;

public class Keyspace {
  public String bucket;
  public String scope;
  public String collection;
  public JsonNode template;

  public Keyspace(String keyspace, JsonNode schema) {
    List<String> keys = Arrays.asList(keyspace.split("\\."));
    if (keys.size() != 3) {
      throw new RuntimeException("Invalid keyspace: " + keyspace);
    }
    bucket = keys.get(0);
    scope = keys.get(1);
    collection = keys.get(2);
    template = schema;
  }

  public String getBucket() {
    return bucket;
  }

  public String getScope() {
    return scope;
  }

  public String getCollection() {
    return collection;
  }

  public JsonNode getTemplate() {
    return template;
  }
}
