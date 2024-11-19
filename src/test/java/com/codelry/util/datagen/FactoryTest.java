package com.codelry.util.datagen;

import com.codelry.util.datagen.generator.RecordFactory;
import com.codelry.util.datagen.generator.Record;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

public class FactoryTest {
  private final ObjectMapper mapper = new ObjectMapper();

  @ParameterizedTest
  @ValueSource(strings = {"basic.json"})
  public void testRecordGeneration(String templateFile) {
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

    RecordFactory factory = new RecordFactory(idTemplate, template);
    factory.start();
    for(int i = 1; i <= 10; i++) {
      try {
        Record record = factory.getNext();
        System.out.println("Document ID:" + record.getId());
        System.out.println(record.getDocument().toPrettyString());
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
    factory.stop();
  }
}
