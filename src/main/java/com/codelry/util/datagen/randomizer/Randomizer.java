package com.codelry.util.datagen.randomizer;

import com.codelry.util.datagen.db.*;

import java.util.List;
import java.util.Random;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Randomizer {
  private final Random rand = new Random();
  private static DatabaseManager databaseManager;

  public Randomizer() {
    databaseManager = DatabaseManager.getInstance();
  }

  public int randomNumber(int minValue, int maxValue) {
    return rand.nextInt((maxValue - minValue) + 1) + minValue;
  }

  public double roundDouble(double value, int places) {
    BigDecimal bd;
    bd = BigDecimal.valueOf(value);
    bd = bd.setScale(places, RoundingMode.HALF_UP);
    return bd.doubleValue();
  }

  public double randomDouble(double minValue, double maxValue, int places) {
    double randomValue = minValue + (maxValue - minValue) * rand.nextDouble();
    return roundDouble(randomValue, places);
  }

  public String randomListElement(List<String> values) {
    return values.get(randomNumber(0, values.size() - 1));
  }

  public String randomFirstName() {
    int index = randomNumber(1, (int) DatabaseManager.nameCount);
    NameRecord name = databaseManager.getNameById(index);
    return name.first;
  }

  public String randomLastName() {
    int index = randomNumber(1, (int) DatabaseManager.nameCount);
    NameRecord name = databaseManager.getNameById(index);
    return name.last;
  }

  public String randomFullName() {
    int index = randomNumber(1, (int) DatabaseManager.nameCount);
    NameRecord name = databaseManager.getNameById(index);
    return name.fullName();
  }

  public NameRecord randomNameRecord() {
    int firstIndex = randomNumber(1, (int) DatabaseManager.nameCount);
    int lastIndex = randomNumber(1, (int) DatabaseManager.nameCount);
    NameRecord firstName = databaseManager.getNameById(firstIndex);
    NameRecord lastName = databaseManager.getNameById(lastIndex);
    return new NameRecord(
        firstName.first,
        lastName.last,
        firstName.gender
    );
  }

  public AddressRecord randomAddressRecord() {
    int streetIndex = randomNumber(1, (int) DatabaseManager.addressCount);
    int cityIndex = randomNumber(1, (int) DatabaseManager.addressCount);
    int number = randomNumber(100, 99999);
    AddressRecord street = databaseManager.getAddressById(streetIndex);
    AddressRecord city = databaseManager.getAddressById(cityIndex);
    return new AddressRecord(
        String.valueOf(number),
        street.street,
        city.city,
        city.state,
        city.zip
    );
  }

  public String randomPhoneNumber(String state) {
    List<String> areaCodes = databaseManager.getAreaCodesByState(state);
    int number = randomNumber(1, 9999);
    int codeIndex = randomNumber(1, areaCodes.size());
    return areaCodes.get(codeIndex - 1) + "-555-" + String.format("%04d", number);
  }
}
