package com.codelry.util.datagen;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.ExecutionException;
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
  private final List<Future<Record>> loadTasks = new ArrayList<>();
  private final ExecutorService loadExecutor = Executors.newFixedThreadPool(32);

  public void init(Properties props, String schemaName, long start, long scaleFactor) {
    setProperties(props);
    schema = new Schema(schemaName, start);
    recordCount = scaleFactor;
    counter.set(start);
  }

  public void loadTaskAdd(Callable<Record> task) {
    loadTasks.add(loadExecutor.submit(task));
  }

  public List<Record> loadTaskWait() {
    List<Record> documents = new ArrayList<>();
    for (Future<Record> future : loadTasks) {
      try {
        Record record = future.get();
        documents.add(record);
      } catch (InterruptedException | ExecutionException e) {
        LOGGER.error(e.getMessage(), e);
      }
    }
    loadTasks.clear();
    return documents;
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
      LOGGER.info("Generating data for keyspace {}", keyspace.toString());
      connect(keyspace);
      List<Record> documents = new ArrayList<>();
      String idTemplate = keyspace.idTemplate;
      JsonNode template = keyspace.template;
      while (counter.get() <= recordCount) {
        Generator generator = new Generator(counter.getAndIncrement(), idTemplate, template);
        loadTaskAdd(generator::generate);
        if (counter.get() % batchSize == 0) {
          insertBatch(loadTaskWait());
        }
      }
      if (!loadTasks.isEmpty()) {
        insertBatch(loadTaskWait());
      }
    }
  }

  public abstract void cleanup();
}
