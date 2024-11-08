package com.codelry.util.datagen;

import com.codelry.util.datagen.util.ProgressOutput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProgressTest {
  private static final Logger LOGGER = LogManager.getLogger(ProgressTest.class);

  public static void main(String[] args) {
    LOGGER.info("Starting test");
    ProgressOutput progress = new ProgressOutput(1000L);
    progress.init();
    for (long i = 0; i < 1000; i++) {
      progress.writeLine(1);
      if (i > 0 && i % 100 == 0) {
        progress.incrementErrorCount();
      }
      try {
        Thread.sleep(100L);
      } catch (InterruptedException ignored) {}
    }
    progress.newLine();
  }
}
