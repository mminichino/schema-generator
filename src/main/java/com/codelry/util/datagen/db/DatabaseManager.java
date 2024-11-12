package com.codelry.util.datagen.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteOpenMode;

import java.net.URL;
import java.sql.*;
import java.util.*;

public class DatabaseManager {
  private static final Logger LOGGER = LogManager.getLogger(DatabaseManager.class);
  private static volatile Connection conn;
  private static volatile Statement stmt;
  private static final Object COORDINATOR = new Object();
  public long nameCount;
  public long addressCount;
  public long areaCodeCount;
  public Map<String, Long> areaCodeCountByState;

  public DatabaseManager() {
    try {
      SQLiteConfig config = new SQLiteConfig();
      config.setOpenMode(SQLiteOpenMode.FULLMUTEX);
      config.setJournalMode(SQLiteConfig.JournalMode.WAL);
      Properties properties = config.toProperties();
      conn = DriverManager.getConnection("jdbc:sqlite::memory:", properties);
      stmt = conn.createStatement();
      cacheDatabase();
      nameCount = getNameCount();
      addressCount = getAddressCount();
      areaCodeCount = getAreaCodeCount();
      areaCodeCountByState = getAreaCodeCountByState();
      LOGGER.debug("Database initialized");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public void cacheDatabase() {
    try {
      URL sourceDb = DatabaseManager.class.getClassLoader().getResource("data/source.db");
      Statement stmt = conn.createStatement();
      stmt.executeUpdate("restore from " + Objects.requireNonNull(sourceDb).getPath());
      stmt.close();
    } catch (SQLException | NullPointerException e) {
      throw new RuntimeException(e);
    }
  }

  public Connection getConnection() {
    return conn;
  }

  public Statement getStatement() {
    return stmt;
  }

  public ResultSet executeQuery(PreparedStatement stmt) throws SQLException {
    synchronized (COORDINATOR) {
      return stmt.executeQuery();
    }
  }

  public long getNameCount() {
    String sql = "SELECT COUNT(*) FROM names";
    try {
      ResultSet rs = stmt.executeQuery(sql);
      rs.next();
      return rs.getLong(1);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public long getAddressCount() {
    String sql = "SELECT COUNT(*) FROM addresses";
    try {
      ResultSet rs = stmt.executeQuery(sql);
      rs.next();
      return rs.getLong(1);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public long getAreaCodeCount() {
    String sql = "SELECT COUNT(*) FROM areacodes";
    try {
      ResultSet rs = stmt.executeQuery(sql);
      rs.next();
      return rs.getLong(1);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public Map<String, Long> getAreaCodeCountByState() {
    Map<String, Long> map = new HashMap<>();
    String sql = "SELECT state, COUNT(*) FROM areacodes GROUP BY state";
    try {
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        String state = rs.getString(1);
        Long count = rs.getLong(2);
        map.put(state, count);
      }
      return map;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public NameRecord getNameById(long id) throws RecordNotFound {
    try {
      String query = "SELECT * FROM names where id = ?";
      PreparedStatement stmt = conn.prepareStatement(query);
      stmt.setLong(1, id);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        return new NameRecord(
            rs.getString(2),
            rs.getString(3),
            rs.getString(4)
        );
      } else {
        throw new RecordNotFound(String.format("Record %d not found", id));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public AddressRecord getAddressById(long id) throws RecordNotFound {
    try {
      String query = "SELECT * FROM addresses where id = ?";
      PreparedStatement stmt = conn.prepareStatement(query);
      stmt.setLong(1, id);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        return new AddressRecord(
            rs.getString(2),
            rs.getString(3),
            rs.getString(4),
            rs.getString(5),
            rs.getString(6)
        );
      } else {
        throw new RecordNotFound(String.format("Record %d not found", id));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public List<AreaCodeRecord> getAreaCodesByState(String state) throws RecordNotFound {
    List<AreaCodeRecord> records = new ArrayList<>();
    try {
      String query = "SELECT * FROM areacodes where state = ?";
      PreparedStatement stmt = conn.prepareStatement(query);
      stmt.setString(1, state);
      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
        AreaCodeRecord record = new AreaCodeRecord(
            rs.getString(2),
            rs.getString(3)
        );
        records.add(record);
      }
      if (records.isEmpty()) {
        throw new RecordNotFound(String.format("No records found for %s", state));
      }
      return records;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
