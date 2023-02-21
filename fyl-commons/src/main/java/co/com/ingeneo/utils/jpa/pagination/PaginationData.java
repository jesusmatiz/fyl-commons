package co.com.ingeneo.utils.jpa.pagination;

import java.io.Serializable;

public class PaginationData
  implements Serializable
{
  private int currentPage;
  private int rowsPerPage;
  private int totalRows;
  private int currentPageSize;
  
  public PaginationData() {}
  
  public PaginationData(int currentPage, int rowsPerPage, int totalRows)
  {
    this.currentPage = currentPage;
    this.rowsPerPage = rowsPerPage;
    this.totalRows = totalRows;
  }
  
  public int getCurrentPage()
  {
    return this.currentPage;
  }
  
  public void setCurrentPage(int currentPage)
  {
    this.currentPage = currentPage;
  }
  
  public int getRowsPerPage()
  {
    return this.rowsPerPage;
  }
  
  public void setRowsPerPage(int rowsPerPage)
  {
    this.rowsPerPage = rowsPerPage;
  }
  
  public int getTotalRows()
  {
    return this.totalRows;
  }
  
  public void setTotalRows(int totalRows)
  {
    this.totalRows = totalRows;
  }
  
  public int getCurrentPageFirstResultIndex()
  {
    return this.currentPage * this.rowsPerPage - this.rowsPerPage;
  }
  
  public int getCurrentPageSize() {
	final float maxPagesQuantity = new Float(totalRows) / new Float(rowsPerPage);
	
	if (new Float(currentPage) > maxPagesQuantity) {
		return totalRows - (rowsPerPage * (currentPage - 1));
	} else {
		return rowsPerPage;
	}
  }
  
  public void setCurrentPageSize(int currentPageSize) {
	this.currentPageSize = currentPageSize;
  }
}
