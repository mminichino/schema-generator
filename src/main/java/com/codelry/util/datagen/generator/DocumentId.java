package com.codelry.util.datagen.generator;

import java.util.UUID;

public class DocumentId {
  public static String documentId() {
    return UUID.randomUUID().toString();
  }
}
