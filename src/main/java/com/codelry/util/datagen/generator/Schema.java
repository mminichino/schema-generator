package com.codelry.util.datagen.generator;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Schema {
  private static final Logger LOGGER = LogManager.getLogger(Schema.class);
  private long index_start = 0;
  private long index_end = 1000;
  List<JsonNode> schema;

  public Schema(String name, long start, long end) {
    index_start = start;
    index_end = end;
  }

  public Schema(String name, long count) {
    index_start = 1;
    index_end = count;
  }

  public void init(String name) {
    try (InputStream in = getClass().getResourceAsStream(String.format("/schema/%s.json", name))) {
      ObjectMapper mapper = new ObjectMapper();
      schema = mapper.readValue(in, new TypeReference<List<JsonNode>>(){});
    } catch (IOException e) {
      throw new RuntimeException("Failed to load schema " + name, e);
    }
  }

  public List<String> getKeyspaceList() {
    List<String> keyspaceList = new ArrayList<>();
    for (JsonNode schemaNode : schema) {
      keyspaceList.add(schemaNode.get("keyspace").asText());
    }
    return keyspaceList;
  }

  public List<Keyspace> getSchemaList() {
    List<Keyspace> keyspaceList = new ArrayList<>();
    for (JsonNode schemaNode : schema) {
      Keyspace keyspace = new Keyspace(schemaNode.get("keyspace").asText(), schemaNode.get("schema"));
      keyspaceList.add(keyspace);
    }
    return keyspaceList;
  }
}
