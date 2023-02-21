package co.com.ingeneo.utils.jpa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Query;

public class Criteria
{
  
  private static final String PARAM_NAME = "PARAM";
  private String classNew;
  private ArrayList<String> selectColumns = new ArrayList();
  private ArrayList<Table> tables = new ArrayList();
  private ArrayList<Restriction> restrictions = new ArrayList();
  private ArrayList<Order> orders = new ArrayList();
  private boolean preparedParameters;
  private Map<String,Object> paramValues = new HashMap<String, Object>();
  private int countParams =  0;
  
  public Criteria() {
	  
  } 
  
  
  public Criteria(boolean preparedParameters) {
	super();
	this.preparedParameters = preparedParameters;
}

public void setClassNew(String classNew)
  {
    this.classNew = classNew;
  }
  
  public void addSelectColumn(String selectData)
  {
    this.selectColumns.add(selectData);
  }
  
  public void addTable(Table table)
  {
    if (table != null) {
      this.tables.add(table);
    }
  }
  
	public void addRestriction(Restriction restriction) {
		if (restriction == null) {
			return;
		}
		
		String paramName = "";
		if (restriction.getValue() != null) {
			this.countParams++;
			paramName = PARAM_NAME + this.countParams;
			this.paramValues.put(paramName,
					restriction.isUpperCase() ? restriction.getValue()
							.toString().toUpperCase() : restriction.getValue());
		}

		boolean isBetween = restriction.getValue2() != null;

		if (isBetween) {			
			this.countParams++;
			String secondParamName = PARAM_NAME + this.countParams;
			this.paramValues.put(paramName,
					restriction.getValue());
			restriction.updateSQLParam(new Object[]{":"+paramName,":"+secondParamName});
		} else {
			restriction.updateSQLParam(new Object[]{":"+paramName});
		}

		this.restrictions.add(restriction);

	}

  public void addRestriction(boolean or, Restriction restriction)
  {
    if (restriction != null)
    {
      restriction.setOr(or);
      this.restrictions.add(restriction);
    }
  }
  
  public void addOrder(Order order)
  {
    if (order != null) {
      this.orders.add(order);
    }
  }
  
  public String getQueryString()
  {
    StringBuilder queryBuilder = new StringBuilder();
    
    queryBuilder.append(getSelectString());
    queryBuilder.append(getFromString());
    queryBuilder.append(getWhereString());
    queryBuilder.append(getOrderByString());
    
    return queryBuilder.toString();
  }
  
  private String getSelectString()
  {
    StringBuilder selectBuilder = new StringBuilder();
    int size = this.selectColumns.size();
    boolean hasClassNew = this.classNew != null;
    if (size > 0)
    {
      selectBuilder.append("SELECT ");
      if (hasClassNew) {
        selectBuilder.append("NEW " + this.classNew + "(");
      }
      for (int i = 0; i < size; i++)
      {
        String column = (String)this.selectColumns.get(i);
        selectBuilder.append(column);
        if (i < size - 1) {
          selectBuilder.append(", ");
        }
      }
      if (hasClassNew) {
        selectBuilder.append(") ");
      }
    }
    return selectBuilder.toString();
  }
  
  private String getFromString()
  {
    StringBuilder fromBuilder = new StringBuilder();
    int size = this.tables.size();
    if (size > 0) {
      for (int i = 0; i < size; i++)
      {
        Table join = (Table)this.tables.get(i);
        if (i == 0) {
          fromBuilder.append(" FROM ");
        } else if (join.isFetch()) {
          fromBuilder.append(" JOIN FETCH ");
        } else {
          fromBuilder.append(" JOIN ");
        }
        fromBuilder.append(join.toString());
      }
    }
    return fromBuilder.toString();
  }
  
	private String getWhereString() {
		StringBuilder whereBuilder = new StringBuilder();
		int size = this.restrictions.size();
		if (size > 0) {
			whereBuilder.append(" WHERE ");
			for (int i = 0; i < size; i++) {
				Restriction restriction = (Restriction) this.restrictions
						.get(i);
				if (restriction != null) {
					String restrictionStr = restriction.toString();
					if (this.preparedParameters
							&& restriction.getRestrictionStrParam() != null
							&& !restriction.getRestrictionStrParam().isEmpty()) {
						whereBuilder.append(restriction
								.getRestrictionStrParam());
					} else {
						whereBuilder.append(restrictionStr);
					}

					if (i < size - 1) {
						if (!restriction.isOr()) {
							whereBuilder.append(" AND ");
						} else {
							whereBuilder.append(" OR ");
						}
					}
				}
			}
		}
		return whereBuilder.toString();
	}
  
  private String getOrderByString()
  {
    StringBuilder orderBuilder = new StringBuilder();
    int size = this.orders.size();
    if (size > 0)
    {
      orderBuilder.append(" ORDER BY ");
      for (int i = 0; i < size; i++)
      {
        Order order = (Order)this.orders.get(i);
        String orderStr = order.toString();
        orderBuilder.append(orderStr);
        if (i < size - 1) {
          orderBuilder.append(" AND ");
        }
      }
    }
    return orderBuilder.toString();
  }

	
  
  /**
   * Set the current parameters to query object. 
   * @param query Query object.
   */
  public void setQueryParams(Query query) {
	   if (query == null  || this.paramValues == null || this.paramValues.isEmpty()) {
		   return;
	   }
	   
	   for (Map.Entry<String, Object> param : this.paramValues.entrySet()) {
		   query.setParameter(param.getKey(), param.getValue());
	   }
	   	
   }
  
   /**
	 * @return the preparedParameters
	 */
	public boolean isPreparedParameters() {
		return preparedParameters;
	}

	/**
	 * @param preparedParameters
	 *            the preparedParameters to set
	 */
	public void setPreparedParameters(boolean preparedParameters) {
		this.preparedParameters = preparedParameters;
	}


	/**
	 * @return the paramValues
	 */
	public Map<String, Object> getParamValues() {
		return paramValues;
	}
	
	
		
  
}
