package co.com.ingeneo.utils.jpa;

public class Table
{
  private String entity;
  private String alias;
  private boolean fetch = false;
  
  public Table(String entity, String alias)
  {
    this.entity = entity;
    this.alias = alias;
  }
  
  public Table(String entity, String alias, boolean fetch)
  {
    this.entity = entity;
    this.alias = alias;
    this.fetch = fetch;
  }
  
  public String getAlias()
  {
    return this.alias;
  }
  
  public void setAlias(String alias)
  {
    this.alias = alias;
  }
  
  public String getEntity()
  {
    return this.entity;
  }
  
  public void setEntity(String entity)
  {
    this.entity = entity;
  }
  
  public boolean isFetch()
  {
    return this.fetch;
  }
  
  public void setFetch(boolean fetch)
  {
    this.fetch = fetch;
  }
  
  public String toString()
  {
    return this.entity + " " + this.alias;
  }
}
