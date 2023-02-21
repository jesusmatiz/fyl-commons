package co.com.ingeneo.utils.web.messages;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ResourceBundleManager
{
  private static Map<String, ResourceBundle> bundles = new HashMap();
  
  public static String getMessage(Locale locale, String bundleName, String key, Object... params)
  {
    try
    {
      ResourceBundle bundle = getBundle(locale, bundleName);
      String message = bundle.getString(key);
      if (params.length > 0) {}
      return MessageFormat.format(message, params);
    }
    catch (MissingResourceException mEx) {}
    return "???unknown bundle???";
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
}