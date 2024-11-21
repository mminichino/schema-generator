package com.codelry.util.datagen.cli;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Properties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CouchbaseConfig {
  public final String COUCHBASE_HOSTNAME = "couchbase.hostname";
  public final String COUCHBASE_USERNAME = "couchbase.username";
  public final String COUCHBASE_PASSWORD = "couchbase.password";
  private String hostname;
  private String username;
  private String password;

  public Properties toProperties() {
    Properties properties = new Properties();
    properties.setProperty(COUCHBASE_HOSTNAME, hostname);
    properties.setProperty(COUCHBASE_USERNAME, username);
    properties.setProperty(COUCHBASE_PASSWORD, password);
    return properties;
  }

  public String getHostname() {
    return hostname;
  }

  public void setHostname(String hostname) {
    this.hostname = hostname;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
