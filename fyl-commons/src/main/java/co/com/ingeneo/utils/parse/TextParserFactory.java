package co.com.ingeneo.utils.parse;

public class TextParserFactory
{
  public static <T extends ITextData> T createInstance(String className, String text)
    throws ParseException
  {
    Class<T> clazz = null;
    try
    {
      clazz = (Class<T>)Class.forName(className);
    }
    catch (ClassNotFoundException cEx)
    {
      throw new ParseException("Error creando la clase ", cEx);
    }
    return createInstance(clazz, text);
  }
  
  public static <T extends ITextData> T createInstance(Class<T> clazz, String text)
    throws ParseException
  {
	  ITextData object = null;
    try
    {
      object = (ITextData)clazz.newInstance();
    }
    catch (Exception ex)
    {
      throw new ParseException("Error instanciando la clase ", ex);
    }
    object.loadFromText(text);
    return (T)object;
  }
}
