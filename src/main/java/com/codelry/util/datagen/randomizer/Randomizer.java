package com.codelry.util.datagen.randomizer;

import com.codelry.util.datagen.db.*;

import java.util.Random;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Randomizer {
  private static Randomizer instance;
  private static final Random rand = new Random();

  private Randomizer() {}

  public static Randomizer init() {
    if (instance == null) {
      instance = new Randomizer();
      instance.setup();
    }
    return instance;
  }

  public void setup() {
    DatabaseManager.init();
  }

  public static int randomNumber(int minValue, int maxValue) {
    return rand.nextInt((maxValue - minValue) + 1) + minValue;
  }

  public static double roundDouble(double value, int places) {
    BigDecimal bd;
    bd = BigDecimal.valueOf(value);
    bd = bd.setScale(places, RoundingMode.HALF_UP);
    return bd.doubleValue();
  }

  public static double randomDouble(double minValue, double maxValue, int places) {
    double randomValue = minValue + (maxValue - minValue) * rand.nextDouble();
    return roundDouble(randomValue, places);
  }

  public static String randomFirstName() {
    int index = randomNumber(1, (int) DatabaseManager.nameCount);
    try {
      NameRecord name = DatabaseManager.getNameById(index);
      return name.first;
    } catch (RecordNotFound e) {
      throw new RuntimeException(e);
    }
  }

  public static String randomLastName() {
    int index = randomNumber(1, (int) DatabaseManager.nameCount);
    try {
      NameRecord name = DatabaseManager.getNameById(index);
      return name.last;
    } catch (RecordNotFound e) {
      throw new RuntimeException(e);
    }
  }

  public static String randomFullName() {
    int index = randomNumber(1, (int) DatabaseManager.nameCount);
    try {
      NameRecord name = DatabaseManager.getNameById(index);
      return name.fullName();
    } catch (RecordNotFound e) {
      throw new RuntimeException(e);
    }
  }

  public static NameRecord randomNameRecord() {
    int firstIndex = randomNumber(1, (int) DatabaseManager.nameCount);
    int lastIndex = randomNumber(1, (int) DatabaseManager.nameCount);
    try {
      NameRecord firstName = DatabaseManager.getNameById(firstIndex);
      NameRecord lastName = DatabaseManager.getNameById(lastIndex);
      return new NameRecord(
          firstName.first,
          lastName.last,
          firstName.gender
      );
    } catch (RecordNotFound e) {
      throw new RuntimeException(e);
    }
  }

  public static AddressRecord randomAddressRecord() {
    int streetIndex = randomNumber(1, (int) DatabaseManager.addressCount);
    int cityIndex = randomNumber(1, (int) DatabaseManager.addressCount);
    int number = randomNumber(100, 99999);
    try {
      AddressRecord street = DatabaseManager.getAddressById(streetIndex);
      AddressRecord city = DatabaseManager.getAddressById(cityIndex);
      return new AddressRecord(
          String.valueOf(number),
          street.street,
          city.city,
          city.state,
          city.zip
      );
    } catch (RecordNotFound e) {
      throw new RuntimeException(e);
    }
  }

  public static String randomPhoneNumber(String state) {
    int phoneIndex = randomNumber(1, Math.toIntExact(DatabaseManager.areaCodeCountByState.get(state)));
    int number = randomNumber(1, 9999);
    try {
      AreaCodeRecord phone = DatabaseManager.getAreaCodesByState(state).get(phoneIndex - 1);
      return phone.code + "-555-" + String.format("%04d", number);
    } catch (RecordNotFound e) {
      throw new RuntimeException(e);
    }
  }
}
