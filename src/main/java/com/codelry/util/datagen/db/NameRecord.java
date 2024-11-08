package com.codelry.util.datagen.db;

public class NameRecord {
  public String first;
  public String last;
  public String gender;

  public NameRecord(String first, String last, String gender) {
    this.first = first;
    this.last = last;
    this.gender = gender;
  }

  public String getFirst() {
    return first;
  }

  public String getLast() {
    return last;
  }

  public String getGender() {
    return gender;
  }
}
