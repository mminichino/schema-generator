package com.codelry.util.datagen.drivers;

import com.codelry.util.datagen.DataLoad;
import com.codelry.util.datagen.generator.Keyspace;
import com.codelry.util.datagen.generator.Record;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.SystemUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class JsonFile extends DataLoad {
  private static final Logger LOGGER = LogManager.getLogger(JsonFile.class);
  public static final String BATCH_SIZE = "couchbase.batchSize";
  public static final String BATCH_SIZE_DEFAULT = "100";
  public static final String ENABLE_DEBUG = "generator.debug";
  public static final String ENABLE_DEBUG_DEFAULT = "false";
  public static final String OUTPUT_DIRECTORY = "generator.outputDirectory";
  public static final String OUTPUT_DIRECTORY_DEFAULT = Paths.get(SystemUtils.getUserHome().getAbsolutePath(), "generator").toString();
  public static final ObjectMapper mapper = new ObjectMapper();
  public static int batchSize = 1000;
  public static boolean enableDebug = false;
  public static String directoryName;
  public static JsonGenerator jsonGenerator;

  public static void createPath(String path) {
    Path p = Paths.get(path);
    if (!Files.exists(p)) {
      try {
        Files.createDirectories(p);
        LOGGER.info("Created directory: {}", path);
      } catch (IOException e) {
        throw new RuntimeException("Error creating directory: " + e.getMessage(), e);
      }
    }
  }

  @Override
  public void prepare() {
    batchSize = Integer.parseInt(properties.getProperty(BATCH_SIZE, BATCH_SIZE_DEFAULT));
    enableDebug = properties.getProperty(ENABLE_DEBUG, ENABLE_DEBUG_DEFAULT).equals("true");
    directoryName = properties.getProperty(OUTPUT_DIRECTORY, OUTPUT_DIRECTORY_DEFAULT);
    createPath(directoryName);
  }

  @Override
  public void insertBatch(List<Record> batch) {
    batch.forEach(record -> {
      try {
        mapper.writeValue(jsonGenerator, record.getDocument());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
  }

  @Override
  public void connect(Keyspace keyspace) {
    try {
      LOGGER.debug("Generating keyspace {}", keyspace.toString());
      String fileName = Paths.get(directoryName, keyspace.bucket, keyspace.scope, keyspace.collection + ".json").toString();
      createPath(Paths.get(fileName).getParent().toString());
      jsonGenerator = new JsonFactory().createGenerator(new File(fileName), JsonEncoding.UTF8);
      jsonGenerator.useDefaultPrettyPrinter();
      jsonGenerator.writeStartArray();
    } catch (IOException e) {
      LOGGER.error(e.getMessage(), e);
      throw new RuntimeException(e);
    }
  }

  @Override
  public void cleanup() {
    try {
      jsonGenerator.writeEndArray();
      jsonGenerator.close();
      LOGGER.info("Done");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
