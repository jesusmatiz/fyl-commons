package co.com.ingeneo.utils.jpa.pagination;

import java.util.ArrayList;
import java.util.Collection;

public class PageCollection<E>
  extends ArrayList<E>
{
  private int totalRows;
  private int rowsPerPage;
  
  public PageCollection(Collection<? extends E> c, int totalRows, int rowsPerPage)
  {
    super(c);
    this.totalRows = totalRows;
    this.rowsPerPage = rowsPerPage;
  }
  
  public int size()
  {
    return this.totalRows;
  }
  
  public E get(int index)
  {
    if ((super.size() != 0) && (index >= super.size()))
    {
      int fakeIndex = index % this.rowsPerPage;
      return super.get(fakeIndex);
    }
    if (super.size() != 0) {
      return super.get(index);
    }
    return null;
  }
}
