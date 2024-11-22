package com.codelry.util.datagen.drivers;

import com.codelry.util.cbdb3.CouchbaseConfig;
import com.codelry.util.cbdb3.CouchbaseConnect;
import com.codelry.util.datagen.DataLoad;
import com.codelry.util.datagen.generator.Record;
import com.codelry.util.datagen.generator.Keyspace;
import com.couchbase.client.core.error.CouchbaseException;
import com.couchbase.client.java.ReactiveCollection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;

public class Couchbase extends DataLoad {
  private static final Logger LOGGER = LogManager.getLogger(Couchbase.class);
  public static final String BATCH_SIZE = "couchbase.batchSize";
  public static final String BATCH_SIZE_DEFAULT = "100";
  public static final String ENABLE_DEBUG = "couchbase.debug";
  public static final String ENABLE_DEBUG_DEFAULT = "false";
  public final PriorityBlockingQueue<Throwable> errorQueue = new PriorityBlockingQueue<>();
  public static final CouchbaseConnect db = CouchbaseConnect.getInstance();
  public int batchSize = 100;
  public boolean enableDebug = false;
  public ReactiveCollection collection;

  @Override
  public void prepare() {
    batchSize = Integer.parseInt(properties.getProperty(BATCH_SIZE, BATCH_SIZE_DEFAULT));
    enableDebug = properties.getProperty(ENABLE_DEBUG, ENABLE_DEBUG_DEFAULT).equals("true");

    CouchbaseConfig config = new CouchbaseConfig()
        .fromProperties(properties)
        .enableDebug(enableDebug);
    db.connect(config);

    Set<String> buckets = new HashSet<>();
    Map<String, Set<String>> scopes = new HashMap<>();
    Map<String, Set<String>> collections = new HashMap<>();

    for (String keyspace : schema.getKeyspaceList()) {
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
      LOGGER.info("Creating bucket: {}", bucket);
      db.createBucket(bucket);
      for (String scope : scopes.get(bucket)) {
        LOGGER.info("Creating scope: {}", scope);
        db.createScope(bucket, scope);
        for (String collection : collections.get(scope)) {
          LOGGER.info("Creating collection: {}", collection);
          db.createCollection(bucket, scope, collection);
        }
      }
    }

    for (Keyspace keyspace : schema.getSchemaList()) {
      if (keyspace.primaryIndex) {
        LOGGER.info("Creating primary index on collection {}", keyspace.collection);
        db.createPrimaryIndex(keyspace.bucket, keyspace.scope, keyspace.collection);
      }
      if (!keyspace.secondaryIndexFields.isEmpty()) {
        String indexName = String.format("idx_%s", keyspace.collection);
        LOGGER.info("Creating GSI {} on collection {}", indexName, keyspace.collection);
        db.createSecondaryIndex(keyspace.bucket, keyspace.scope, keyspace.collection, indexName, keyspace.secondaryIndexFields);
      }
    }
  }

  @Override
  public void insertBatch(List<Record> batch) {
    Flux.fromIterable(batch)
        .flatMap(record -> collection.upsert(record.getId(), record.getDocument()))
        .retryWhen(Retry.backoff(10, Duration.ofMillis(10)).filter(t -> t instanceof CouchbaseException))
        .doOnError(errorQueue::put)
        .blockLast();
  }

  @Override
  public void connect(Keyspace keyspace) {
    try {
      LOGGER.debug("Connecting to keyspace {}", keyspace.toString());
      db.connectKeyspace(keyspace.bucket, keyspace.scope, keyspace.collection);
      collection = db.getReactiveCollection();
    } catch (CouchbaseException e) {
      LOGGER.error(e.getMessage(), e);
      throw new RuntimeException(e);
    }
  }

  @Override
  public void cleanup() {}
}
