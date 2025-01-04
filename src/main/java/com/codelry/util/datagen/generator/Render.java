package com.codelry.util.datagen.generator;

import com.codelry.util.datagen.db.NameRecord;
import com.codelry.util.datagen.db.AddressRecord;
import com.codelry.util.datagen.randomizer.Randomizer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hubspot.jinjava.Jinjava;
import com.hubspot.jinjava.JinjavaConfig;
import com.hubspot.jinjava.features.FeatureConfig;
import com.hubspot.jinjava.features.FeatureStrategies;
import com.hubspot.jinjava.interpret.Context;
import com.hubspot.jinjava.interpret.JinjavaInterpreter;
import static com.hubspot.jinjava.lib.expression.DefaultExpressionStrategy.ECHO_UNDEFINED;

import com.hubspot.jinjava.lib.fn.ELFunctionDefinition;
import com.hubspot.jinjava.objects.collections.PyList;
import com.hubspot.jinjava.tree.Node;
import com.hubspot.jinjava.tree.ExpressionNode;
import com.hubspot.jinjava.tree.parse.ExpressionToken;
import org.apache.commons.codec.binary.Hex;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Render {
  private static Render instance;
  private final Randomizer randomizer = new Randomizer();
  private static final AtomicInteger index = new AtomicInteger(1);

  private Render() {}

  public static Render getInstance() {
    if (instance == null) {
      instance = new Render();
    }
    return instance;
  }

  public String processTemplate(String template) {
    Context context = new Context();
    JinjavaConfig config = JinjavaConfig.newBuilder().build();
    Jinjava jinjava = new Jinjava(config);
    try {
      jinjava.getGlobalContext().registerFunction(
          new ELFunctionDefinition(
              "",
              "random_uuid",
              this.getClass().getDeclaredMethod("randomUuid")
          )
      );
      jinjava.getGlobalContext().registerFunction(
          new ELFunctionDefinition(
              "",
              "index",
              this.getClass().getDeclaredMethod("indexNum", int.class)
          )
      );
      jinjava.getGlobalContext().registerFunction(
          new ELFunctionDefinition(
              "",
              "number",
              this.getClass().getDeclaredMethod("randomNumber", int.class, int.class)
          )
      );
      jinjava.getGlobalContext().registerFunction(
          new ELFunctionDefinition(
              "",
              "decimal",
              this.getClass().getDeclaredMethod("randomDecimal", int.class, int.class, int.class)
          )
      );
      jinjava.getGlobalContext().registerFunction(
          new ELFunctionDefinition(
              "",
              "random",
              this.getClass().getDeclaredMethod("randomSelection", Object.class)
          )
      );
      jinjava.getGlobalContext().registerFunction(
          new ELFunctionDefinition(
              "",
              "repeat",
              this.getClass().getDeclaredMethod("repeat", int.class)
          )
      );
      Set<String> bindings = extractBindings(template);

      for (String binding : bindings) {
        switch (binding) {
          case "RANDOM_UUID":
            context.put("RANDOM_UUID", randomUuid());
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
            context.put("ZIPCODE", "\"" + address.zip + "\"");
            context.put("PHONE_NUMBER", phoneNumber);
            break;
          case "BOOLEAN":
            context.put("BOOLEAN", randomizer.randomBoolean());
        }
      }
      JinjavaInterpreter interpreter = new JinjavaInterpreter(jinjava, context, config);
      return interpreter.render(template);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
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

  public Set<String> extractBindings(String template) {
    Set<String> bindings = new HashSet<>();
    try {
      Jinjava jinjava = new Jinjava();
      JinjavaInterpreter interpreter = jinjava.newInterpreter();
      Node rootNode = interpreter.parse(template);
      traverseNodes(rootNode, bindings);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return bindings;
  }

  public static String randomUuid() {
    return UUID.randomUUID().toString();
  }

  public static String indexNum(int pad) {
    pad = pad <= 0 ? 1 : pad;
    return String.format("%0" + pad + "d", index.getAndIncrement());
  }

  public static String randomSelection(Object list) {
    PyList elements = (PyList) list;
    Random rand = new Random();
    int index = rand.nextInt(elements.size());
    return elements.get(index).toString();
  }

  public static Integer randomNumber(int start, int end) {
    Random rand = new Random();
    return rand.nextInt((end - start) + 1) + start;
  }

  public static double randomDecimal(int start, int end, int precision) {
    Random rand = new Random();
    BigDecimal bd;
    double value = start + (end - start) * rand.nextDouble();
    bd = BigDecimal.valueOf(value);
    bd = bd.setScale(precision, RoundingMode.HALF_UP);
    return bd.doubleValue();
  }

  public static int repeat(int size) {
    return size;
  }
}
