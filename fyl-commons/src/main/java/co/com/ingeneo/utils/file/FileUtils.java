package co.com.ingeneo.utils.file;

import co.com.ingeneo.utils.io.IOUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils
{
  public static boolean deleteFile(File file)
  {
    if (!file.exists()) {
      return false;
    }
    if (file.isFile()) {
      return file.delete();
    }
    if (file.isDirectory())
    {
      File[] childs = file.listFiles();
      for (File child : childs) {
        if (!deleteFile(child)) {
          return false;
        }
      }
      return file.delete();
    }
    return false;
  }
  
  public static void createFile(File file, byte[] data)
    throws IOException
  {
    BufferedOutputStream bufOutput = null;
    try
    {
      bufOutput = new BufferedOutputStream(new FileOutputStream(file));
      IOUtils.writeBytes(data, bufOutput);
    }
    finally
    {
      if (bufOutput != null) {
        bufOutput.close();
      }
    }
  }
  
  public static String getNameFromPath(String fullPath)
  {
    int index = fullPath.lastIndexOf(File.separator);
    if (index >= 0) {
      return fullPath.substring(index + 1);
    }
    return fullPath;
  }
}
