package com.codelry.util.datagen.generator;

import com.codelry.util.datagen.db.NameRecord;
import com.codelry.util.datagen.randomizer.Randomizer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubspot.jinjava.Jinjava;
import com.hubspot.jinjava.JinjavaConfig;
import com.hubspot.jinjava.interpret.JinjavaInterpreter;
import com.hubspot.jinjava.tree.Node;
import com.hubspot.jinjava.tree.output.OutputNode;
import com.hubspot.jinjava.tree.ExpressionNode;
import com.hubspot.jinjava.tree.parse.ExpressionToken;
import com.hubspot.jinjava.tree.parse.Token;
import com.hubspot.jinjava.tree.parse.TokenScannerSymbols;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class Generator {
  private static final Logger LOGGER = LogManager.getLogger(Generator.class);
  private static volatile Generator instance;
  private static Jinjava jinjava;
  private static final Map<String, Object> context = new HashMap<>();
  private static String template;
  private static final ObjectMapper mapper = new ObjectMapper();
  private static final AtomicLong index = new AtomicLong(0);
  private static Set<String> bindings;

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
    JinjavaConfig config = new JinjavaConfig();
    jinjava = new Jinjava(config);
    Randomizer.init();
  }

  public static Set<String> extractBindings() {
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

  private static void traverseNodes(Node node, Set<String> bindings) {
    if (node instanceof ExpressionNode) {
      ExpressionToken token = (ExpressionToken) node.getMaster();
      bindings.add(token.getExpr());
    }
    for (Node child : node.getChildren()) {
      traverseNodes(child, bindings);
    }
  }

  private static void setContext() {
    for (String binding : bindings) {
      switch (binding) {
        case "DOCUMENT_ID":
          context.put("DOCUMENT_ID", DocumentId.documentId());
          break;
        case "INDEX":
          context.put("INDEX", index.getAndIncrement());
          break;
        case "FIRST_NAME":
        case "LAST_NAME":
        case "FULL_NAME":
        case "EMAIL_ADDRESS":
          NameRecord name = Randomizer.randomNameRecord();
          context.put("FIRST_NAME", name.first);
          context.put("LAST_NAME", name.last);
          context.put("FULL_NAME", name.fullName());
          context.put("EMAIL_ADDRESS", name.emailAddress());
          break;
      }
    }
  }

  public static void readTemplate(JsonNode templateJson) {
    try {
      template = mapper.writeValueAsString(templateJson);
      context.clear();
      bindings = extractBindings();
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
