package com.qualiantech.sharepoint.connections;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class InitializeSharePointProperty {

  public Properties getProperty() {
    Properties prop = new Properties();

    try {
      String propFileName = "SharepointConfiguration.Properties";

      InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

      if (inputStream != null) {
        prop.load(inputStream);
      } else {
        throw new FileNotFoundException(
            "property file '" + propFileName + "' not found in the classpath");
      }

    } catch (Exception e) {

    }
    return prop;
  }
}
