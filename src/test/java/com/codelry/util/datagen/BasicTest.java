package com.codelry.util.datagen;

import com.codelry.util.datagen.generator.Generator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

public class BasicTest {
  private final ObjectMapper mapper = new ObjectMapper();

  @ParameterizedTest
  @ValueSource(strings = {"basic.json"})
  public void testCouchbaseConnect(String templateFile) {
    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    JsonNode template;
    String idTemplate = "{{ random_uuid() }}";

    System.out.printf("Testing with template file: %s%n", templateFile);
    try {
      template = mapper.readTree(loader.getResourceAsStream(templateFile));
    } catch (IOException e) {
      System.out.println("can not open template file: " + e.getMessage());
      e.printStackTrace(System.err);
      throw new RuntimeException(e);
    }

    for(int i = 1; i <= 10; i++) {
      Generator generator = new Generator(i, idTemplate, template);
      System.out.println("Document ID:" + generator.getId());
      System.out.println(generator.getDocument().toPrettyString());
    }
  }
}
