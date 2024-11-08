package com.codelry.util.datagen;

import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.PriorityBlockingQueue;

import com.couchbase.client.core.error.CouchbaseException;
import com.couchbase.client.java.ReactiveCollection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;

public abstract class DataLoad {
  private static final Logger LOGGER = LogManager.getLogger(DataLoad.class);
  public final PriorityBlockingQueue<Throwable> errorQueue = new PriorityBlockingQueue<>();
  public static final String CLUSTER_HOST = "couchbase.hostname";
  public static final String CLUSTER_USER = "couchbase.username";
  public static final String CLUSTER_PASSWORD = "couchbase.password";
  public static final String CLUSTER_BUCKET = "couchbase.bucket";
  public static final String DEFAULT_USER = "Administrator";
  public static final String DEFAULT_PASSWORD = "password";
  public static final String DEFAULT_HOSTNAME = "127.0.0.1";
  public static final String DEFAULT_BUCKET = "default";
  public static final String BATCH_SIZE = "couchbase.batchSize";
  public static final String BATCH_SIZE_DEFAULT = "100";
  public static final String ENABLE_DEBUG = "couchbase.debug";
  public static final String ENABLE_DEBUG_DEFAULT = "false";
  public String hostname;
  public String username;
  public String password;
  public String bucket;
  public int batchSize = 100;

  public void init(Properties properties) {
//    hostname = properties.getProperty(CLUSTER_HOST, DEFAULT_HOSTNAME);
//    username = properties.getProperty(CLUSTER_USER, DEFAULT_USER);
//    password = properties.getProperty(CLUSTER_PASSWORD, DEFAULT_PASSWORD);
//    batchSize = Integer.parseInt(properties.getProperty(BATCH_SIZE, BATCH_SIZE_DEFAULT));
//    boolean debug = getProperties().getProperty(ENABLE_DEBUG, ENABLE_DEBUG_DEFAULT).equals("true");
//
//    CouchbaseConnect.CouchbaseBuilder dbBuilder = new CouchbaseConnect.CouchbaseBuilder();
//    db = dbBuilder
//        .host(hostname)
//        .username(username)
//        .password(password)
//        .enableDebug(debug)
//        .build();
//  }
  }

  public abstract void cleanup();

  public void loadBatch(ReactiveCollection collection, List<Table> batch) {
    Flux.fromIterable(batch)
        .flatMap(record -> collection.upsert(record.recordId(), record.asNode()))
        .retryWhen(Retry.backoff(10, Duration.ofMillis(10)).filter(t -> t instanceof CouchbaseException))
        .doOnError(errorQueue::put)
        .blockLast();
  }
}
