package co.com.ingeneo.utils.jpa;

public class Order
{
  private OrderType type;
  private String columnName;
  
  public Order(String columnName)
  {
    this(OrderType.ASC, columnName);
  }
  
  public Order(OrderType type, String columnName)
  {
    this.type = type;
    this.columnName = columnName;
  }
  
  public String getColumnName()
  {
    return this.columnName;
  }
  
  public void setColumnName(String columnName)
  {
    this.columnName = columnName;
  }
  
  public OrderType getType()
  {
    return this.type;
  }
  
  public void setType(OrderType type)
  {
    this.type = type;
  }
  
  public String toString()
  {
    return this.columnName + " " + this.type.toString();
  }
}
