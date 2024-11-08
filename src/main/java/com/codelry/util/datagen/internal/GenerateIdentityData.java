package com.codelry.util.datagen.internal;

import com.codelry.util.datagen.util.ProgressOutput;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.structured.StructuredPrompt;
import dev.langchain4j.model.input.structured.StructuredPromptProcessor;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import static dev.langchain4j.model.openai.OpenAiChatModelName.GPT_4_O;
import static java.time.Duration.ofSeconds;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class GenerateIdentityData {
  private static final Logger LOGGER = LogManager.getLogger(GenerateIdentityData.class);
  private static final ObjectMapper mapper = new ObjectMapper();
  private static final TypeFactory typeFactory = mapper.getTypeFactory();
  static ChatLanguageModel model = OpenAiChatModel.builder()
      .apiKey(ApiKeys.OPENAI_API_KEY)
      .modelName(GPT_4_O)
      .timeout(ofSeconds(180))
      .maxTokens(4096)
      .build();

  public GenerateIdentityData() {}

  public List<JsonNode> generateNames(int iterations, int count) {
    return SimpleNameRecord.run(iterations, count);
  }

  public List<JsonNode> generateAddresses(int iterations, int count) {
    return SimpleAddressRecord.run(iterations, count);
  }

  static class SimpleNameRecord {

    @StructuredPrompt({
        "Generate {{count}} unique synthetic first and last name data records using names commonly found in the United States.",
        "Come up with real names, and never use most popular placeholders like john doe and jane doe.",
        "Structure your answer as a JSON list without any markdown with each JSON object formatted in the following way:",

        "{",
        "firstName: The person's first name",
        "lastName: The person's surname",
        "gender: The person's gender denoted as male or female",
        "}"
    })
    static class CreateNamePrompt {
      private int count;
    }

    interface Assistant {
      String chat(String message);
    }

    public static List<JsonNode> run(int iterations, int count) {
      ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);
      List<JsonNode> nameList = new ArrayList<>();
      int total = count * iterations;

      CreateNamePrompt namePrompt = new CreateNamePrompt();
      namePrompt.count = count;
      Prompt prompt = StructuredPromptProcessor.toPrompt(namePrompt);

      Assistant assistant = AiServices.builder(Assistant.class)
          .chatLanguageModel(model)
          .chatMemory(chatMemory)
          .build();

      ProgressOutput progress = new ProgressOutput(total);
      progress.init();
      while (nameList.size() < total) {
        try {
          String answer = assistant.chat(prompt.toUserMessage().toString());
          List<JsonNode> batch = mapper.readValue(answer, typeFactory.constructCollectionType(List.class, JsonNode.class));
          progress.writeLine(batch.size());
          nameList.addAll(batch);
        } catch (Exception e) {
          progress.incrementErrorCount();
          LOGGER.debug(e.getMessage(), e);
        }
      }
      progress.newLine();
      return new ArrayList<>(nameList.subList(0, total));
    }
  }

  static class SimpleAddressRecord {

    @StructuredPrompt({
        "Generate {{count}} unique synthetic mailing address records located in the United States.",
        "Structure your answer as a JSON list without any markdown with each JSON object formatted in the following way:",

        "{",
        "number: The address number",
        "street: The address street name and street suffix",
        "city: The address city",
        "state: The address state standard abbreviation",
        "zipCode: THe address five digit zip code",
        "}"
    })
    static class CreateAddressPrompt {
      private int count;
    }

    interface Assistant {
      String chat(String message);
    }

    public static List<JsonNode> run(int iterations, int count) {
      ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);
      List<JsonNode> addressList = new ArrayList<>();
      int total = count * iterations;

      CreateAddressPrompt addressPrompt = new CreateAddressPrompt();
      addressPrompt.count = count;
      Prompt prompt = StructuredPromptProcessor.toPrompt(addressPrompt);

      Assistant assistant = AiServices.builder(Assistant.class)
          .chatLanguageModel(model)
          .chatMemory(chatMemory)
          .build();

      ProgressOutput progress = new ProgressOutput(total);
      progress.init();
      while (addressList.size() < total) {
        try {
          String answer = assistant.chat(prompt.toUserMessage().toString());
          List<JsonNode> batch = mapper.readValue(answer, typeFactory.constructCollectionType(List.class, JsonNode.class));
          progress.writeLine(batch.size());
          addressList.addAll(batch);
        } catch (Exception e) {
          progress.incrementErrorCount();
          LOGGER.debug(e.getMessage(), e);
        }
      }
      progress.newLine();
      return new ArrayList<>(addressList.subList(0, total));
    }
  }
}
