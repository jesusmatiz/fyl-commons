package co.com.ingeneo.utils.jpa;

import java.util.Collection;

public class ProxyUtils
{
  public static void initializeProperty(Object property)
  {
    if (property != null) {
      property.toString();
    }
  }
  
  public static void initializeProperty(Collection property)
  {
    if (property != null) {
      property.size();
    }
  }
}
