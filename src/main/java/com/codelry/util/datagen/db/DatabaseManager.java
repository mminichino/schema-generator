package com.codelry.util.datagen.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sqlite.SQLiteConfig;

import java.net.URL;
import java.sql.*;
import java.util.*;

public class DatabaseManager {
  private static final Logger LOGGER = LogManager.getLogger(DatabaseManager.class);
  private static DatabaseManager instance;
  public static long nameCount;
  public static long addressCount;
  public static List<NameRecord> nameList = new ArrayList<>();
  public static List<AddressRecord> addressList = new ArrayList<>();
  public static Map<String, List<String>> areaCodeList = new HashMap<>();

  private DatabaseManager() {}

  public static DatabaseManager getInstance() {
    if (instance == null) {
      instance = new DatabaseManager();
      instance.init();
    }
    return instance;
  }

  public void init() {
    try {
      SQLiteConfig config = new SQLiteConfig();
      Properties properties = config.toProperties();
      URL sourceDb = DatabaseManager.class.getClassLoader().getResource("data/source.db");
      Connection conn = DriverManager.getConnection("jdbc:sqlite:" + Objects.requireNonNull(sourceDb).getPath(), properties);
      buildNameList(conn);
      buildAddressList(conn);
      buildAreaCodeList(conn);
      nameCount = getNameCount();
      addressCount = getAddressCount();
      conn.close();
      LOGGER.debug("Database initialized");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public long getNameCount() {
    return nameList.size();
  }

  public long getAddressCount() {
    return addressList.size();
  }

  public void buildNameList(Connection conn) {
    String sql = "SELECT first, last, gender FROM names";
    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        nameList.add(new NameRecord(
            rs.getString("first"),
            rs.getString("last"),
            rs.getString("gender")
        ));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public void buildAddressList(Connection conn) {
    String sql = "SELECT number, street, city, state, zip FROM addresses";
    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        addressList.add(new AddressRecord(
            rs.getString("number"),
            rs.getString("street"),
            rs.getString("city"),
            rs.getString("state"),
            rs.getString("zip")
        ));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public void buildAreaCodeList(Connection conn) {
    String sql = "SELECT state, code FROM areacodes";
    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        String state = rs.getString("state");
        String code = rs.getString("code");
        if (!areaCodeList.containsKey(state)) {
          areaCodeList.put(state, new ArrayList<>());
        }
        areaCodeList.get(state).add(code);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public NameRecord getNameById(int id) {
    return nameList.get(id - 1);
  }

  public AddressRecord getAddressById(int id) {
    return addressList.get(id - 1);
  }

  public List<String> getAreaCodesByState(String state) {
    return areaCodeList.getOrDefault(state, new ArrayList<>());
  }
}
