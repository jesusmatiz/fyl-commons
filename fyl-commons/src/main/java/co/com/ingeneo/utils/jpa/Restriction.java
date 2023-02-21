package co.com.ingeneo.utils.jpa;

import java.text.MessageFormat;

public class Restriction {
  
	private static final String PARAM = "{0}";
	private static final String PARAM2 = "{1}";
	
    public static enum LikeDirectionEnum {
        BOTH,  LEFT,  RIGHT,  EQUAL;
    
        private LikeDirectionEnum() {}
    }
  
  private String restrictionStr = "";
  private String restrictionStrParam = "";
  private Object value;
  private Object value2;
  private boolean or;
  private boolean upperCase;
  
  private Restriction(String restrictionStr)
  {
    this.restrictionStr = restrictionStr;
  }
  
  
  
  public Restriction(String restrictionStr, String restrictionStrParam,
		Object value, boolean upperCase) {
	super();
	this.restrictionStr = restrictionStr;
	this.restrictionStrParam = restrictionStrParam;
	this.value = value;
	this.upperCase = upperCase;
}
  
  
  public Restriction(String restrictionStr, String restrictionStrParam,
		Object value1, Object value2) {
	super();
	this.restrictionStr = restrictionStr;
	this.restrictionStrParam = restrictionStrParam;
	this.value = value1;
	this.value2 = value2;
}



public static Restriction eq(String propertyName, Object value)
  {
    return getSimpleQuery(false, "=", propertyName, value);
  }
  
  public static Restriction eq(boolean uppercase, String propertyName, Object value)
  {
    return getSimpleQuery(uppercase, "=", propertyName, value);
  }
  
  public static Restriction eq(String propertyName, boolean ordinal, Enum enumValue)
  {
    return getEnumQuery("=", propertyName, ordinal, enumValue);
  }
  
  public static Restriction neq(String propertyName, Object value)
  {
    return getSimpleQuery(false, "!=", propertyName, value);
  }
  
  public static Restriction neq(boolean uppercase, String propertyName, Object value)
  {
    return getSimpleQuery(uppercase, "!=", propertyName, value);
  }
  
  public static Restriction neq(String propertyName, boolean ordinal, Enum enumValue)
  {
    return getEnumQuery("!=", propertyName, ordinal, enumValue);
  }
  
  public static Restriction gt(String propertyName, Object value)
  {
    return getSimpleQuery(false, ">", propertyName, value);
  }
  
  public static Restriction lt(String propertyName, Object value)
  {
    return getSimpleQuery(false, "<", propertyName, value);
  }
  
  public static Restriction ge(String propertyName, Object value)
  {
    return getSimpleQuery(false, ">=", propertyName, value);
  }
  
  public static Restriction le(String propertyName, Object value)
  {
    return getSimpleQuery(false, "<=", propertyName, value);
  }
  
  public static Restriction like(String propertyName, Object value)
  {
    return getLikeQuery(LikeDirectionEnum.BOTH, false, propertyName, value);
  }
  
  public static Restriction like(boolean uppercase, String propertyName, Object value)
  {
    return getLikeQuery(LikeDirectionEnum.BOTH, uppercase, propertyName, value);
  }
  
  public static Restriction like(LikeDirectionEnum likeDirection, boolean uppercase, String propertyName, Object value)
  {
    return getLikeQuery(likeDirection, uppercase, propertyName, value);
  }
  
  public static Restriction between(String propertyName, Object value1, Object value2)
  {
    return getBetweenQuery(propertyName, value1, value2);
  }
  
  public static Restriction in(String propertyName, Object... values)
  {
    return getInQuery(propertyName, values);
  }
  
  public static Restriction inSubquery(String propertyName, String subquery)
  {
    return getInQuery(propertyName, subquery);
  }
  
  private static Restriction getSimpleQuery(boolean uppercase, String sqlOperator, String propertyName, Object value)
  {
    boolean empty = value == null;
    if ((value instanceof String)) {
      empty = ((String)value).length() == 0;
    }
    if (!empty)
    {
      if (!uppercase)
      {
        String sql = propertyName + " " + sqlOperator + " " + value;
        String sqlPre = propertyName + " " + sqlOperator + " " + PARAM;
        return new Restriction(sql, sqlPre, value, false);
      }
      String sql = "UPPER(" + propertyName + ") " + sqlOperator + " " + value.toString().toUpperCase();
      String sqlPre = "UPPER(" + propertyName + ") " + sqlOperator + " " + PARAM;
      return new Restriction(sql, sqlPre, value, true);
    }
    return null;
  }
  
  private static Restriction getLikeQuery(LikeDirectionEnum likeDirection, boolean uppercase, String propertyName, Object value)
  {
    boolean empty = value == null;
    if ((value instanceof String)) {
      empty = ((String)value).length() == 0;
    }
    if (!empty)
    {
      if (!uppercase)
      {
        String sql = null;
        String sqlPre = propertyName + " LIKE " + PARAM;
        String newValue = null;
        switch (likeDirection.ordinal())
        {
        case 1: 
          sql = propertyName + " LIKE '" + value + "%'";
          newValue = value.toString() + "%";
          break;
        case 2: 
          sql = propertyName + " LIKE '%" + value + "'";
          newValue = "%" + value.toString();
          break;
        case 3: 
          sql = propertyName + " LIKE '" + value + "'";
          newValue = value.toString();
          break;
        default: 
          sql = propertyName + " LIKE '%" + value + "%'";
          newValue = "%" + value.toString() + "%";
        }
        return new Restriction(sql, sqlPre, newValue, false);
      }
      String sql = null;
      String sqlPre = "UPPER(" + propertyName + ") LIKE " + PARAM;
      String newValue = null;
      switch (likeDirection.ordinal())
      {
      case 1: 
        sql = "UPPER(" + propertyName + ") LIKE '" + value.toString().toUpperCase() + "%'";
        newValue = value.toString().toUpperCase() + "%";
        break;
      case 2: 
        sql = "UPPER(" + propertyName + ") LIKE '%" + value.toString().toUpperCase() + "'";
        newValue = "%" + value.toString().toUpperCase();
        break;
      case 3: 
        sql = "UPPER(" + propertyName + ") LIKE '" + value.toString().toUpperCase() + "'";
        newValue = value.toString().toUpperCase();
        break;
      default: 
        sql = "UPPER(" + propertyName + ") LIKE '%" + value.toString().toUpperCase() + "%'";
        newValue = "%" + value.toString().toUpperCase() + "%";
      }
      return new Restriction(sql, sqlPre, newValue, true);
    }
    return null;
  }
  
  private static Restriction getInQuery(String propertyName, Object... values)
  {
    boolean empty = (values == null) || (values.length == 0);
    if (!empty)
    {
      String sql = propertyName + " IN(";
      for (int i = 0; i < values.length; i++)
      {
        Object value = values[i];
        sql = sql + value.toString();
        if (i < values.length - 1) {
          sql = sql + ", ";
        }
      }
      sql = sql + ")";
      return new Restriction(sql);
    }
    return null;
  }
  
  private static Restriction getInQuery(String propertyName, String subquery)
  {
    if (subquery != null)
    {
      String sql = propertyName + " IN(" + subquery + ")";
      return new Restriction(sql);
    }
    return null;
  }
  
	private static Restriction getEnumQuery(String sqlOperator,
			String propertyName, boolean ordinal, Enum value) {
		if (value != null) {
			String sql = null;
			String sqlPre = null;
			if (ordinal) {
				sql = propertyName + " " + sqlOperator + " " + value.ordinal();
			} else {
				sql = propertyName + " " + sqlOperator + " '"
						+ value.toString() + "'";
			}

			sqlPre = propertyName + " " + sqlOperator + PARAM;

			return new Restriction(sql, sqlPre, (Object) (ordinal ? value.ordinal() : value.toString()), false);
		}
		return null;
	}
  
  private static Restriction getBetweenQuery(String propertyName, Object value1, Object value2)
  {
    if ((value1 != null) && (value2 != null))
    {
      String sql = propertyName + " BETWEEN " + value1 + " AND " + value2;
      String sqlPre = propertyName + " BETWEEN " + PARAM + " AND " + PARAM2;
      return new Restriction(sql,sqlPre,value1, value2);
    }
    return null;
  }
  
  public String toString()
  {
    return this.restrictionStr;
  }
  
  public boolean isOr()
  {
    return this.or;
  }
  
  public void setOr(boolean or)
  {
    this.or = or;
  }



	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * @return the value2
	 */
	public Object getValue2() {
		return value2;
	}

	/**
	 * @param value2
	 *            the value2 to set
	 */
	public void setValue2(Object value2) {
		this.value2 = value2;
	}

	/**
	 * @return the upperCase
	 */
	public boolean isUpperCase() {
		return upperCase;
	}

	/**
	 * @param upperCase
	 *            the upperCase to set
	 */
	public void setUpperCase(boolean upperCase) {
		this.upperCase = upperCase;
	}

	public void updateSQLParam(Object[] args) {
		if (this.restrictionStrParam == null) {
			return;
		}

		this.restrictionStrParam = MessageFormat.format(
				this.restrictionStrParam, args);
	}

	/**
	 * @return the restrictionStrParam
	 */
	public String getRestrictionStrParam() {
		return restrictionStrParam;
	}

}
