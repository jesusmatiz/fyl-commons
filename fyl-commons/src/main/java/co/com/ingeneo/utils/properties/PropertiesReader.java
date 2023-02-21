package co.com.ingeneo.utils.properties;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PropertiesReader
{
  private static PropertiesReader instance = new PropertiesReader();
  private Properties properties = new Properties();
  private List<String> propertiesLoaded = new ArrayList();
  
  public static PropertiesReader getInstance()
  {
    if (instance == null) {
      instance = new PropertiesReader();
    }
    return instance;
  }
  
  public void loadProperties(String propertiesFile)
    throws IOException
  {
    if (this.propertiesLoaded.contains(propertiesFile)) {
      return;
    }
    InputStream propertiesStream = null;
    try
    {
      ClassLoader classLoader = PropertiesReader.class.getClassLoader();
      propertiesStream = classLoader.getResourceAsStream(propertiesFile);
      if (propertiesStream == null) {
        throw new FileNotFoundException("The file " + propertiesFile + " does not exist in the classloader.");
      }
      this.properties.load(propertiesStream);
      this.propertiesLoaded.add(propertiesFile);
    }
    finally
    {
      if (propertiesStream != null) {
        propertiesStream.close();
      }
    }
  }
  
  public Integer getIntProperty(String propertyName)
  {
    String value = this.properties.getProperty(propertyName);
    if (value != null) {
      return new Integer(value);
    }
    return null;
  }
  
  public String getTextProperty(String propertyName)
  {
    return this.properties.getProperty(propertyName);
  }
  
  public Boolean getBooleanProperty(String propertyName)
  {
    String value = this.properties.getProperty(propertyName);
    if (value != null) {
      return new Boolean(value);
    }
    return null;
  }
}
