package com.codelry.util.datagen.cli;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Properties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CapellaConfig {
  public static final String CAPELLA_PROJECT_NAME = "capella.project.name";
  public static final String CAPELLA_PROJECT_ID = "capella.project.id";
  public static final String CAPELLA_DATABASE_NAME = "capella.database.name";
  public static final String CAPELLA_DATABASE_ID = "capella.database.id";
  public static final String CAPELLA_TOKEN = "capella.token";
  public static final String CAPELLA_USER_EMAIL = "capella.user.email";
  public static final String CAPELLA_USER_ID = "capella.user.id";
  private String project;
  private String projectId;
  private String database;
  private String databaseId;
  private String token;
  private String userEmail;
  private String userId;

  public Properties toProperties() {
    Properties properties = new Properties();
    properties.setProperty(CAPELLA_PROJECT_NAME, project);
    properties.setProperty(CAPELLA_PROJECT_ID, projectId);
    properties.setProperty(CAPELLA_DATABASE_NAME, database);
    properties.setProperty(CAPELLA_DATABASE_ID, databaseId);
    properties.setProperty(CAPELLA_TOKEN, token);
    properties.setProperty(CAPELLA_USER_EMAIL, userEmail);
    properties.setProperty(CAPELLA_USER_ID, userId);
    return properties;
  }

  public String getProject() {
    return project;
  }

  public void setProject(String project) {
    this.project = project;
  }

  public String getProjectId() {
    return projectId;
  }

  public void setProjectId(String projectId) {
    this.projectId = projectId;
  }

  public String getDatabase() {
    return database;
  }

  public void setDatabase(String database) {
    this.database = database;
  }

  public String getDatabaseId() {
    return databaseId;
  }

  public void setDatabaseId(String databaseId) {
    this.databaseId = databaseId;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getUserEmail() {
    return userEmail;
  }

  public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }
}
