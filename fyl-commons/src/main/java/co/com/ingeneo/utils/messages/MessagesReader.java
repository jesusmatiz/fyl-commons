package co.com.ingeneo.utils.messages;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MessagesReader
{
  private static Map<String, ResourceBundle> bundles = new HashMap();
  
  public static String getMessage(String bundleName, String key, Object... params)
  {
    ResourceBundle localeBundle = getBundle(getLocale(), bundleName);
    String message = getMessageFromBundle(localeBundle, key, params);
    if (message == null)
    {
      ResourceBundle defaultBundle = ResourceBundle.getBundle(bundleName);
      message = defaultBundle.getString(key);
      if (message == null) {
        message = "???unknown bundle???";
      }
    }
    return message;
  }
  
  private static String getMessageFromBundle(ResourceBundle bundle, String key, Object... params)
  {
    try
    {
      String message = bundle.getString(key);
      if (params.length > 0) {}
      return MessageFormat.format(message, params);
    }
    catch (MissingResourceException mEx) {}
    return null;
  }
  
  private static ResourceBundle getBundle(Locale locale, String bundleName)
    throws MissingResourceException
  {
    String localeName = "_" + locale.toString();
    ClassLoader cl = Thread.currentThread().getContextClassLoader();
    ResourceBundle bundle = (ResourceBundle)bundles.get(bundleName + localeName);
    if (bundle == null)
    {
      if (locale != null) {
        bundle = ResourceBundle.getBundle(bundleName, locale, cl);
      } else {
        bundle = ResourceBundle.getBundle(bundleName);
      }
      bundles.put(bundleName + localeName, bundle);
    }
    return bundle;
  }
  
  protected static Locale getLocale()
  {
    return MessageLocale.getLocale();
  }
}
