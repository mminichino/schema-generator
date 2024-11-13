package com.codelry.util.datagen;

import com.codelry.util.datagen.generator.Generator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class DocIdGenTest {
  private static final Logger LOGGER = LogManager.getLogger(DocIdGenTest.class);
  private static final ObjectMapper mapper = new ObjectMapper();
  private static JsonNode template;

  @BeforeAll
  public static void setUp() throws Exception {
    ClassLoader loader = Thread.currentThread().getContextClassLoader();

    try {
      template = mapper.readTree(loader.getResourceAsStream("basic.json"));
    } catch (IOException e) {
      System.out.println("can not open template file: " + e.getMessage());
      e.printStackTrace(System.err);
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testRandomUuid() {
    String idTemplate = "{{ random_uuid() }}";
    long i = 100;

    Generator generator = new Generator(i, idTemplate, template);
    LOGGER.info("UUID: {}", generator.getId());
  }

  @Test
  public void testIndex() {
    String idTemplate = "{{ index(5) }}";
    long i = 100;

    Generator generator = new Generator(i, idTemplate, template);
    LOGGER.info("Index: {}", generator.getId());
  }

  @Test
  public void testField() {
    String idTemplate = "{{ field('id') }}";
    long i = 100;

    Generator generator = new Generator(i, idTemplate, template);
    LOGGER.info("Field: {}", generator.getId());
  }

  @Test
  public void testHash() {
    String idTemplate = "{{ doc_hash() }}";
    long i = 100;

    Generator generator = new Generator(i, idTemplate, template);
    LOGGER.info("Hash: {}", generator.getId());
  }
}
