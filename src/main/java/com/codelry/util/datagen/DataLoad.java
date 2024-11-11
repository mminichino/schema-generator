package com.codelry.util.datagen;

import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.PriorityBlockingQueue;

import com.codelry.util.cbdb3.CouchbaseConnect;
import com.couchbase.client.core.error.CouchbaseException;
import com.couchbase.client.java.ReactiveCollection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;

public class DataLoad {
  private static final Logger LOGGER = LogManager.getLogger(DataLoad.class);
  public static final String BATCH_SIZE = "couchbase.batchSize";
  public static final String BATCH_SIZE_DEFAULT = "100";
  public static final String ENABLE_DEBUG = "couchbase.debug";
  public static final String ENABLE_DEBUG_DEFAULT = "false";
  public final PriorityBlockingQueue<Throwable> errorQueue = new PriorityBlockingQueue<>();
  public int batchSize = 100;
  public boolean enableDebug = false;

  public DataLoad(Properties properties) {
    batchSize = Integer.parseInt(properties.getProperty(BATCH_SIZE, BATCH_SIZE_DEFAULT));
    enableDebug = properties.getProperty(ENABLE_DEBUG, ENABLE_DEBUG_DEFAULT).equals("true");

    CouchbaseConnect.CouchbaseBuilder dbBuilder = new CouchbaseConnect.CouchbaseBuilder();
    CouchbaseConnect db = dbBuilder
        .fromProperties(properties)
        .enableDebug(enableDebug)
        .build();
  }

  public void cleanup() {}

  public void loadBatch(ReactiveCollection collection, List<Record> batch) {
    Flux.fromIterable(batch)
        .flatMap(record -> collection.upsert(record.getId(), record.getDocument()))
        .retryWhen(Retry.backoff(10, Duration.ofMillis(10)).filter(t -> t instanceof CouchbaseException))
        .doOnError(errorQueue::put)
        .blockLast();
  }
}
