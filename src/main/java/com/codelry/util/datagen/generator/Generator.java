package com.codelry.util.datagen.generator;

import com.codelry.util.datagen.db.AddressRecord;
import com.codelry.util.datagen.db.NameRecord;
import com.codelry.util.datagen.randomizer.Randomizer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubspot.jinjava.Jinjava;
import com.hubspot.jinjava.JinjavaConfig;
import com.hubspot.jinjava.interpret.JinjavaInterpreter;
import com.hubspot.jinjava.tree.Node;
import com.hubspot.jinjava.tree.ExpressionNode;
import com.hubspot.jinjava.tree.parse.ExpressionToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class Generator {
  private static final Logger LOGGER = LogManager.getLogger(Generator.class);
  private static Jinjava jinjava;
  private static String template;
  private static Set<String> bindings;
  private static final ObjectMapper mapper = new ObjectMapper();
  private static final AtomicLong index = new AtomicLong(0);
  private static final Randomizer randomizer = new Randomizer();

  public Generator(JsonNode templateJson) {
    JinjavaConfig config = new JinjavaConfig();
    jinjava = new Jinjava(config);

    try {
      template = mapper.writeValueAsString(templateJson);
      bindings = extractBindings(template);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public Generator(String templateString) {
    JinjavaConfig config = new JinjavaConfig();
    jinjava = new Jinjava(config);

    template = templateString;
    bindings = extractBindings(template);
  }

  public Set<String> extractBindings(String template) {
    Set<String> bindings = new HashSet<>();
    try {
      JinjavaInterpreter interpreter = jinjava.newInterpreter();
      Node rootNode = interpreter.parse(template);
      traverseNodes(rootNode, bindings);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return bindings;
  }

  private void traverseNodes(Node node, Set<String> bindings) {
    if (node instanceof ExpressionNode) {
      ExpressionToken token = (ExpressionToken) node.getMaster();
      bindings.add(token.getExpr());
    }
    for (Node child : node.getChildren()) {
      traverseNodes(child, bindings);
    }
  }

  public Map<String, Object> setContext(Set<String> bindings) {
    Map<String, Object> context = new HashMap<>();

    for (String binding : bindings) {
      switch (binding) {
        case "RANDOM_UUID":
          context.put("RANDOM_UUID", UUID.randomUUID().toString());
          break;
        case "INDEX":
          context.put("INDEX", index.getAndIncrement());
          break;
        case "FIRST_NAME":
        case "LAST_NAME":
        case "FULL_NAME":
        case "EMAIL_ADDRESS":
          NameRecord name = randomizer.randomNameRecord();
          context.put("FIRST_NAME", name.first);
          context.put("LAST_NAME", name.last);
          context.put("FULL_NAME", name.fullName());
          context.put("EMAIL_ADDRESS", name.emailAddress());
          break;
        case "ADDRESS_LINE_1":
        case "CITY":
        case "STATE":
        case "ZIPCODE":
        case "PHONE_NUMBER":
          AddressRecord address = randomizer.randomAddressRecord();
          String phoneNumber = randomizer.randomPhoneNumber(address.state);
          context.put("ADDRESS_LINE_1", address.number + " " + address.street);
          context.put("CITY", address.city);
          context.put("STATE", address.state);
          context.put("ZIPCODE", address.zip);
          context.put("PHONE_NUMBER", phoneNumber);
          break;
      }
    }

    return context;
  }

  public JsonNode renderJson() {
    try {
      String renderedTemplate = jinjava.render(template, setContext(bindings));
      return mapper.readTree(renderedTemplate);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Can not render template as JSON");
    } catch (Exception e) {
      throw new RuntimeException("Problem rendering template: " + e.getMessage(), e);
    }
  }

  public String renderString() {
    try {
      return jinjava.render(template, setContext(bindings));
    } catch (Exception e) {
      throw new RuntimeException("Problem rendering template: " + e.getMessage(), e);
    }
  }
}
