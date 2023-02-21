package co.com.ingeneo.utils.collection;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MapUtils
{
  public static Map<String, Difference> getDifferences(Map<String, String> oldMap, Map<String, String> newMap)
  {
    Map<String, Difference> differencesMap = new HashMap();
    

    Set<String> keys = new HashSet(oldMap.keySet());
    keys.addAll(newMap.keySet());
    for (String key : keys)
    {
      String oldData = (String)oldMap.get(key);
      String newData = (String)newMap.get(key);
      if ((oldData != null) || (newData != null)) {
        if ((oldData == null) && (newData != null))
        {
          Difference difference = new Difference(null, newData);
          differencesMap.put(key, difference);
        }
        else if ((oldData != null) && (newData == null))
        {
          Difference difference = new Difference(oldData, null);
          differencesMap.put(key, difference);
        }
        else if (!oldData.equals(newData))
        {
          Difference difference = new Difference(oldData, newData);
          differencesMap.put(key, difference);
        }
      }
    }
    return differencesMap;
  }
  
  public static class Difference
    implements Serializable
  {
    private String oldData;
    private String newData;
    
    private Difference(String oldData, String newData)
    {
      this.oldData = oldData;
      this.newData = newData;
    }
    
    public String getNewData()
    {
      return this.newData;
    }
    
    public void setNewData(String newData)
    {
      this.newData = newData;
    }
    
    public String getOldData()
    {
      return this.oldData;
    }
    
    public void setOldData(String oldData)
    {
      this.oldData = oldData;
    }
    
    public String toString()
    {
      return "[" + this.oldData + ", " + this.newData + "]";
    }
  }
}
