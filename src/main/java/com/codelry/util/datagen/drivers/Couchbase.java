package com.codelry.util.datagen.drivers;

import com.codelry.util.cbdb3.CouchbaseConfig;
import com.codelry.util.cbdb3.CouchbaseConnect;
import com.codelry.util.datagen.DataLoad;
import com.codelry.util.datagen.generator.Record;
import com.codelry.util.datagen.generator.Generator;
import com.codelry.util.datagen.generator.Keyspace;
import com.codelry.util.datagen.generator.Schema;
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
  private static final Logger LOGGER = LogManager.getLogger(DataLoad.class);
  public static final String BATCH_SIZE = "couchbase.batchSize";
  public static final String BATCH_SIZE_DEFAULT = "100";
  public static final String ENABLE_DEBUG = "couchbase.debug";
  public static final String ENABLE_DEBUG_DEFAULT = "false";
  public final PriorityBlockingQueue<Throwable> errorQueue = new PriorityBlockingQueue<>();
  public static final CouchbaseConnect db = CouchbaseConnect.getInstance();
  public int batchSize = 100;
  public boolean enableDebug = false;

  @Override
  public void init(Properties properties) {
    batchSize = Integer.parseInt(properties.getProperty(BATCH_SIZE, BATCH_SIZE_DEFAULT));
    enableDebug = properties.getProperty(ENABLE_DEBUG, ENABLE_DEBUG_DEFAULT).equals("true");

    CouchbaseConfig config = new CouchbaseConfig()
        .fromProperties(properties)
        .enableDebug(enableDebug);
    db.connect(config);
  }

  @Override
  public void prepare(Schema schema) {
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
      db.createBucket(bucket);
      for (String scope : scopes.get(bucket)) {
        db.createScope(bucket, scope);
        for (String collection : collections.get(scope)) {
          db.createCollection(bucket, scope, collection);
        }
      }
    }
  }

  @Override
  public void generate(Schema schema) {
    for (Keyspace keyspace : schema.getSchemaList()) {
      Generator generator = new Generator(keyspace.template);
      db.connectKeyspace(keyspace.bucket, keyspace.scope, keyspace.collection);
    }
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
