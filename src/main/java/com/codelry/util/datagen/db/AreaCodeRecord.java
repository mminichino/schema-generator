package com.codelry.util.datagen.db;

public class AreaCodeRecord {
  public String code;
  public String state;

  public AreaCodeRecord(String code, String state) {
    this.code = code;
    this.state = state;
  }

  public String getCode() {
    return code;
  }

  public String getState() {
    return state;
  }
}
