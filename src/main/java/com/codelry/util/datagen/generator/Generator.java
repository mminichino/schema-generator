package com.codelry.util.datagen.generator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubspot.jinjava.Jinjava;
import com.hubspot.jinjava.JinjavaConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class Generator {
  private static final Logger LOGGER = LogManager.getLogger(Generator.class);
  private static volatile Generator instance;
  private static JinjavaConfig config;
  private static Jinjava jinjava;
  private static final Map<String, Object> context = new HashMap<>();
  private static String template;
  private static final ObjectMapper mapper = new ObjectMapper();
  private static final AtomicLong index = new AtomicLong(0);

  private Generator() {}

  public static Generator init() {
    if (instance == null) {
      synchronized (Generator.class) {
        if (instance == null) {
          instance = new Generator();
          instance.setup();
        }
      }
    }
    return instance;
  }

  private void setup() {
    config = new JinjavaConfig();
    jinjava = new Jinjava(config);
  }

  private static void setContext() {
    context.put("DOCUMENT_ID", DocumentId.documentId());
    context.put("INDEX", index.getAndIncrement());
    context.put("FIRST_NAME", FirstName.firstName());
    context.put("LAST_NAME", LastName.lastName());
  }

  public static void readTemplate(JsonNode templateJson) {
    try {
      template = mapper.writeValueAsString(templateJson);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public static JsonNode render() {
    setContext();
    String renderedTemplate = jinjava.render(template, context);
    try {
      return mapper.readTree(renderedTemplate);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
