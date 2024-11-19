package com.codelry.util.datagen;

import java.util.List;
import java.util.Properties;

import com.codelry.util.datagen.generator.*;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class DataLoad {
  private static final Logger LOGGER = LogManager.getLogger(DataLoad.class);
  private static int batchSize = 100;
  private static long recordCount = 1;
  private static long recordStart = 1;
  public static Properties properties;
  public static Schema schema;

  public void init(Properties props, String schemaName, long start, long scaleFactor) {
    setProperties(props);
    schema = new Schema(schemaName, start);
    recordCount = scaleFactor;
    recordStart = start;
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

  public void generate() {
    for (Keyspace keyspace : schema.getSchemaList()) {
      LOGGER.info("Generating data for keyspace {}", keyspace.toString());
      connect(keyspace);
      String idTemplate = keyspace.idTemplate;
      JsonNode template = keyspace.template;
      RecordFactory factory = new RecordFactory(idTemplate, template);
      factory.setIndex(recordStart);
      factory.start();
      for (int i = 0; i < recordCount; i += batchSize) {
        long end = Math.min(i + batchSize, recordCount);
        int chunk = (int) (end - i);
        insertBatch(factory.collect(chunk));
      }
      factory.stop();
    }
  }

  public abstract void cleanup();
}
