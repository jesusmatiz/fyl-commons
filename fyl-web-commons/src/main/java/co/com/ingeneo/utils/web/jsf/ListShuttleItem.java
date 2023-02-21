package co.com.ingeneo.utils.web.jsf;

import java.io.Serializable;

public class ListShuttleItem
  implements Serializable
{
  private Long id;
  private String label;
  
  public ListShuttleItem() {}
  
  public ListShuttleItem(Long id, String label)
  {
    this.id = id;
    this.label = label;
  }
  
  public Long getId()
  {
    return this.id;
  }
  
  public void setId(Long id)
  {
    this.id = id;
  }
  
  public String getLabel()
  {
    return this.label;
  }
  
  public void setLabel(String label)
  {
    this.label = label;
  }
  
  public int hashCode()
  {
    return this.id.hashCode() + this.label.hashCode();
  }
  
  public boolean equals(Object obj)
  {
    if ((obj instanceof ListShuttleItem))
    {
      ListShuttleItem other = (ListShuttleItem)obj;
      return (other.id.equals(this.id)) && (other.label.equals(this.label));
    }
    return false;
  }
}
