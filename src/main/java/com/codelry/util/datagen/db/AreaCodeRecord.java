package com.codelry.util.datagen.db;

public class AreaCodeRecord {
  public String code;
  public String prefix;
  public String state;

  public AreaCodeRecord(String code, String prefix, String state) {
    this.code = code;
    this.prefix = prefix;
    this.state = state;
  }

  public String getCode() {
    return code;
  }

  public String getPrefix() {
    return prefix;
  }

  public String getState() {
    return state;
  }
}
