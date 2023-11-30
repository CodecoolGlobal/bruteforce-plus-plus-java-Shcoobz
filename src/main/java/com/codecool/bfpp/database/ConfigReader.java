package com.codecool.bfpp.database;

import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

  public static Properties readDatabaseConfig(String propertiesFilePath) {
    Properties prop = new Properties();

    try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesFilePath)) {
      if (inputStream == null) {
        throw new IllegalArgumentException("File not found: " + propertiesFilePath);
      }
      prop.load(inputStream);
    } catch (IOException ex) {
      ex.printStackTrace();
      throw new RuntimeException("Error reading the configuration file", ex);
    }

    return prop;
  }
}
