package com.codelry.util.internal;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.structured.StructuredPrompt;
import dev.langchain4j.model.input.structured.StructuredPromptProcessor;
import dev.langchain4j.model.openai.OpenAiChatModel;
import static dev.langchain4j.model.openai.OpenAiChatModelName.GPT_4_O;
import static java.time.Duration.ofSeconds;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GenerateIdentityData {
  private static final Logger LOGGER = LogManager.getLogger(GenerateIdentityData.class);
  static ChatLanguageModel model = OpenAiChatModel.builder()
      .apiKey(ApiKeys.OPENAI_API_KEY)
      .modelName(GPT_4_O)
      .timeout(ofSeconds(60))
      .build();

  public GenerateIdentityData() {}

  public void generate(int count) {
    SimpleAddressRecord.run(count);
  }

  static class SimpleAddressRecord {

    @StructuredPrompt({
        "Generate {{count}} detailed personal information records with random realistic people from the United States.",
        "Come up with real names, and never use most popular placeholders like john smith and john doe.",
        "Create realistic addresses located in random states in the United States. And create fictitious realistic telephone",
        "numbers for each person with an area code that matches their address and with 555 for the prefix.",
        "Structure your answer as a JSON list with each JSON object formatted in the following way:",

        "{",
        "firstName: The person's first name",
        "lastName: The person's surname",
        "address: The person's house number and street name and street suffix",
        "city: The person's city",
        "state: The person's state standard abbreviation",
        "zipCode: THe person's zip code",
        "phone: The person's phone number",
        "}"
    })
    static class CreateAddressPrompt {
      private int count;
    }

    public static void run(int count) {
      CreateAddressPrompt addressPrompt = new CreateAddressPrompt();
      addressPrompt.count = count;
      Prompt prompt = StructuredPromptProcessor.toPrompt(addressPrompt);

      LOGGER.info("Generating {} records", count);
      AiMessage aiMessage = model.generate(prompt.toUserMessage()).content();
      System.out.println(aiMessage.text());
    }
  }
}
