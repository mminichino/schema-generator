package com.codelry.util.datagen.generator;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

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

  private void init(String name) {

  }
}
