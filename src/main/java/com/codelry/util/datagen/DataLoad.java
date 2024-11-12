package com.codelry.util.datagen;

import java.util.Properties;
import com.codelry.util.datagen.generator.Schema;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class DataLoad {
  private static final Logger LOGGER = LogManager.getLogger(DataLoad.class);

  public abstract void init(Properties properties);

  public abstract void prepare(Schema schema);

  public abstract void generate();

  public abstract void cleanup();
}
