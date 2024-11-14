package com.codelry.util.datagen;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

import com.codelry.util.datagen.generator.*;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class DataLoad {
  private static final Logger LOGGER = LogManager.getLogger(DataLoad.class);
  private static final AtomicLong counter = new AtomicLong(1);
  private static int batchSize = 100;
  private static long recordCount = 0;
  public static Properties properties;
  public static Schema schema;

  public void init(Properties props, String schemaName, long start, long scaleFactor) {
    setProperties(props);
    schema = new Schema(schemaName, start);
    recordCount = scaleFactor;
  }

  public void setProperties(Properties props) {
    properties = props;
  }

  public void setBatchSize(int batchSize) {
    DataLoad.batchSize = batchSize;
  }

  public abstract void prepare();

  public abstract void insertBatch(List<Record> batch);

  public abstract void connect(Keyspace keyspace);

  public Stream<List<Record>> dataStream(List<Record> data) {
    return Batch.split(data, batchSize);
  }

  public void generate() {
    for (Keyspace keyspace : schema.getSchemaList()) {
      connect(keyspace);
      List<Record> documents = new ArrayList<>();
      String idTemplate = keyspace.idTemplate;
      JsonNode template = keyspace.template;
      while (counter.get() <= recordCount) {
        Generator generator = new Generator(counter.getAndIncrement(), idTemplate, template);
        Record record = new Record(generator.getId(), generator.getDocument());
        documents.add(record);
      }
      dataStream(documents).forEach(this::insertBatch);
    }
  }

  public abstract void cleanup();
}
