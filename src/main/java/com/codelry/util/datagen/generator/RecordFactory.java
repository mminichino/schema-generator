package com.codelry.util.datagen.generator;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class RecordFactory {
  private static final Logger LOGGER = LogManager.getLogger(RecordFactory.class);
  public BlockingQueue<Record> recordQueue;
  public BlockingQueue<Throwable> errorQueue = new LinkedBlockingQueue<>();
  private final List<Future<Record>> loadTasks = new ArrayList<>();
  private ExecutorService loadExecutor;
  private static final AtomicLong counter = new AtomicLong(1);
  private final String idTemplate;
  private final JsonNode template;
  private int batchSize = 32;
  private Thread runThread;

  public RecordFactory(String id, JsonNode doc) {
    loadExecutor = Executors.newFixedThreadPool(64);
    recordQueue = new LinkedBlockingQueue<>(32);
    idTemplate = id;
    template = doc;
  }

  public void setThreads(int threads) {
    loadExecutor = Executors.newFixedThreadPool(threads);
  }

  public void setThreshold(int threshold) {
    recordQueue = new LinkedBlockingQueue<>(threshold);
    batchSize = threshold;
  }

  public void setIndex(long index) {
    counter.set(index);
  }

  public void loadTaskAdd(Callable<Record> task) {
    loadTasks.add(loadExecutor.submit(task));
  }

  public void loadTaskGet() {
    for (Future<Record> future : loadTasks) {
      try {
        Record record = future.get();
        recordQueue.put(record);
      } catch (ExecutionException e) {
        errorQueue.add(e);
      } catch (InterruptedException e) {
        LOGGER.debug("Record wait interrupted");
      }
    }
    loadTasks.clear();
  }

  public void start() {
    runThread = new Thread(() -> {
      while (!Thread.currentThread().isInterrupted()) {
        Generator generator = new Generator(counter.get(), idTemplate, template);
        loadTaskAdd(generator::generate);
        if (counter.getAndIncrement() % batchSize == 0) {
          loadTaskGet();
        }
      }
    });
    runThread.start();
  }

  public void stop() {
    LOGGER.debug("Stopping record factory");
    runThread.interrupt();
  }

  public List<Record> collect(int quantity) {
    List<Record> records = new ArrayList<>();
    for (int i = 0; i < quantity; i++) {
      try {
        records.add(getNext());
      } catch (InterruptedException e) {
        LOGGER.error(e.getMessage(), e);
      }
    }
    return records;
  }

  public Record getNext() throws InterruptedException {
    return recordQueue.poll(5, TimeUnit.SECONDS);
  }
}
