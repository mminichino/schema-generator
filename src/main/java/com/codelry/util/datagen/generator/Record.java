package com.codelry.util.datagen.generator;

import com.fasterxml.jackson.databind.JsonNode;

public class Record {
  public String documentId;
  public JsonNode document;

  public Record(String pattern, JsonNode document) {
    this.documentId = pattern;
    this.document = document;
  }

  public String getId() {
    return documentId;
  }

  public JsonNode getDocument() {
    return document;
  }
}
