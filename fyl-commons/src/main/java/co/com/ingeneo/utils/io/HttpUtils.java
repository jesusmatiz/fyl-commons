package co.com.ingeneo.utils.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtils
{
  private static final String UTF8_ENCODE = "UTF-8";
  
  static
  {
    HttpURLConnection.setFollowRedirects(true);
  }
  
  public static String encode(String request)
    throws UnsupportedEncodingException
  {
    String response = URLEncoder.encode(request, "UTF-8");
    return response.replaceAll("\\+", "%20");
  }
  
  public static String sendRequest(String urlStr, Map<String, String> parameters)
    throws IOException
  {
    BufferedWriter writer = null;
    BufferedReader reader = null;
    try
    {
      URL url = new URL(urlStr);
      HttpURLConnection httpConnection = (HttpURLConnection)url.openConnection();
      httpConnection.setRequestMethod("POST");
      httpConnection.setDefaultUseCaches(false);
      httpConnection.setDoOutput(true);
      

      writer = new BufferedWriter(new OutputStreamWriter(httpConnection.getOutputStream()));
      String request = encodeParameters(parameters);
      writer.write(request.toString());
      writer.flush();
      

      reader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
      StringBuilder response = new StringBuilder();
      String line = null;
      while ((line = reader.readLine()) != null) {
        response.append(line);
      }
      return response.toString();
    }
    finally
    {
      if (writer != null) {
        writer.close();
      }
      if (reader != null) {
        reader.close();
      }
    }
  }
  
  public static String sendGetRequest(String urlStr)
    throws IOException
  {
    HttpURLConnection httpConnection = null;
    BufferedReader reader = null;
    try
    {
      URL url = new URL(urlStr);
      httpConnection = (HttpURLConnection)url.openConnection();
      httpConnection.setRequestMethod("GET");
      reader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
      
      StringBuilder response = new StringBuilder();
      String line = null;
      while ((line = reader.readLine()) != null) {
        response.append(line);
      }
      return response.toString();
    }
    finally
    {
      if (reader != null) {
        reader.close();
      }
      if (httpConnection != null) {
        httpConnection.disconnect();
      }
    }
  }
  
  private static String encodeParameters(Map<String, String> parameters)
    throws UnsupportedEncodingException
  {
    List<String> keyArray = new ArrayList(parameters.keySet());
    int keyLength = keyArray.size();
    StringBuffer requestParameters = new StringBuffer();
    for (int i = 0; i < keyLength; i++)
    {
      String key = (String)keyArray.get(i);
      String data = URLEncoder.encode(key, "UTF-8");
      String value = URLEncoder.encode((String)parameters.get(key), "UTF-8");
      requestParameters.append(data);
      requestParameters.append("=");
      requestParameters.append(value);
      if (i < keyLength - 1) {
        requestParameters.append("&");
      }
    }
    return requestParameters.toString();
  }
}
