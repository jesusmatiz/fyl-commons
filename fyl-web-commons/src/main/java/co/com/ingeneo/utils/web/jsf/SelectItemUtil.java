package co.com.ingeneo.utils.web.jsf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.model.SelectItem;

public class SelectItemUtil
{
  public static List<SelectItem> mapToSelectItem(Map maps)
  {
    List<SelectItem> items = new ArrayList();
    Iterator entryIterator = maps.entrySet().iterator();
    while (entryIterator.hasNext())
    {
      Map.Entry entry = (Map.Entry)entryIterator.next();
      Object key = entry.getKey();
      Object value = entry.getValue();
      SelectItem item = new SelectItem(key.toString(), value.toString());
      items.add(item);
    }
    
    
        Collections.sort(items, new Comparator<SelectItem>() { 
            @Override
     public int compare(SelectItem sItem1, SelectItem sItem2) {  
     String sItem1Label = sItem1.getLabel();  
     String sItem2Label = sItem2.getLabel();  
      
     return (sItem1Label.compareToIgnoreCase(sItem2Label));  
     }  
     });  
    return items;
  }
  
  public static List<ListShuttleItem> mapToListItem(Map maps)
  {
    List<ListShuttleItem> items = new ArrayList();
    Iterator entryIterator = maps.entrySet().iterator();
    while (entryIterator.hasNext())
    {
      Map.Entry entry = (Map.Entry)entryIterator.next();
      Object key = entry.getKey();
      Object value = entry.getValue();
      ListShuttleItem item = new ListShuttleItem((Long)key, value.toString());
      items.add(item);
    }
    return items;
  }
  
  public static Long getSelectedId(String selectedId)
  {
    if (selectedId == null) {
      return null;
    }
    return selectedId.length() > 0 ? new Long(selectedId) : null;
  }
  
  public static <T extends Enum> T getSelectedEnum(Class<T> enumClass, String selectedEnum)
  {
    if ((selectedEnum == null) || (selectedEnum.length() == 0)) {
      return null;
    }
    return (T)Enum.valueOf(enumClass, selectedEnum);
  }
  
  public static boolean checkEmptyList(List<SelectItem> list)
  {
    return (list == null) || (list.size() == 0);
  }
}
