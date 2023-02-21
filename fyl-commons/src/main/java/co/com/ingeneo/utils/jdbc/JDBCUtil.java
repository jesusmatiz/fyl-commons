package co.com.ingeneo.utils.jdbc;

import co.com.ingeneo.utils.io.IOUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCUtil
{
  public static void closeConnection(Connection con)
    throws SQLException
  {
    if (con != null) {
      con.close();
    }
  }
  
  public static void closeConnection(Connection con, PreparedStatement ps)
    throws SQLException
  {
    if (ps != null) {
      ps.close();
    }
    if (con != null) {
      con.close();
    }
  }
  
  public static String getString(Clob clob)
  {
    StringBuilder builder = new StringBuilder();
    BufferedReader reader = null;
    String line = null;
    try
    {
      try
      {
        reader = new BufferedReader(clob.getCharacterStream());
        while ((line = reader.readLine()) != null) {
          builder.append(line);
        }
      }
      finally
      {
        if (reader != null) {
          reader.close();
        }
      }
    }
    catch (Exception ex)
    {
      throw new IllegalArgumentException(ex.getMessage(), ex.getCause());
    }
    return builder.toString();
  }
  
  public static String getStringFromObject(Object obj)
  {
    if (obj == null) {
      return null;
    }
    if ((obj instanceof Clob)) {
      return getString((Clob)obj);
    }
    if ((obj instanceof String)) {
      return (String)obj;
    }
    return obj.toString();
  }
  
  public static byte[] getBytes(Blob blob)
  {
    ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
    try
    {
      IOUtils.copy(blob.getBinaryStream(), byteOutput);
      return byteOutput.toByteArray();
    }
    catch (Exception ex)
    {
      throw new IllegalArgumentException(ex.getMessage(), ex.getCause());
    }
  }
}
