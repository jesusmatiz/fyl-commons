package co.com.ingeneo.utils.parse;

public class ParseException
  extends Exception
{
  public ParseException(Throwable cause)
  {
    super(cause);
  }
  
  public ParseException(String message, Throwable cause)
  {
    super(message, cause);
  }
  
  public ParseException(String message)
  {
    super(message);
  }
}
