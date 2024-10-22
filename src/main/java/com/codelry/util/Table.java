package com.codelry.util;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class Table {
  public final ObjectNode data = new ObjectMapper().createObjectNode();
  public String recordName = "document";
  public long recordNumber = 1;

  public abstract void init(long id);

  public String recordId() {
    return String.format("%s::%016d", recordName, recordNumber);
  }

  public ObjectNode asNode() {
    return data;
  }

  public String asJson() {
    return data.toString();
  }
}
