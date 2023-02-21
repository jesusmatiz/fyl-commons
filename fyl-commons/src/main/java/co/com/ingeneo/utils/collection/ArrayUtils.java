package co.com.ingeneo.utils.collection;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ArrayUtils {
  public static Map toMap(List<Object[]> dataList)
  {
    return toMap(dataList, 0, 1);
  }
  
  public static Map toMap(List<Object[]> dataList, int keyIndex, int valueIndex)
  {
    Map map = new LinkedHashMap();
    for (Object[] data : dataList) {
      map.put(data[keyIndex], data[valueIndex]);
    }
    return map;
  }
  
  public static Map toMap(String text)
  {
    Map outputMap = new HashMap();
    if (text != null)
    {
      String mapText = text.replace("{", "");
      mapText = mapText.replace("}", "");
      String[] mapArray = mapText.split(",");
      for (String mapArrayItem : mapArray)
      {
        String[] mapEntryText = mapArrayItem.split("=");
        if (mapEntryText.length == 2)
        {
          String key = mapEntryText[0].trim();
          String value = mapEntryText[1].trim();
          outputMap.put(key, value);
        }
      }
    }
    return outputMap;
  }
}
