package co.com.ingeneo.utils.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

public class IOUtils
{
  private static final int BUFFER_SIZE = 2048;
  
  public static void copy(InputStream input, OutputStream output)
    throws IOException
  {
    BufferedInputStream bufferedInput = null;
    BufferedOutputStream bufferedOutput = null;
    try
    {
      bufferedInput = new BufferedInputStream(input);
      bufferedOutput = new BufferedOutputStream(output);
      byte[] buffer = new byte[2048];
      int size = bufferedInput.read(buffer, 0, 2048);
      while (size != -1)
      {
        bufferedOutput.write(buffer, 0, size);
        size = bufferedInput.read(buffer, 0, 2048);
      }
      bufferedOutput.flush();
    }
    finally
    {
      if (bufferedOutput != null) {
        bufferedOutput.close();
      }
      if (bufferedInput != null) {
        bufferedInput.close();
      }
    }
  }
  
  public static void copy(Reader reader, Writer writer)
    throws IOException
  {
    BufferedReader bufferedReader = null;
    BufferedWriter bufferedWriter = null;
    try
    {
      bufferedReader = new BufferedReader(reader);
      bufferedWriter = new BufferedWriter(writer);
      char[] buffer = new char[2048];
      int size = bufferedReader.read(buffer, 0, 2048);
      while (size != -1)
      {
        bufferedWriter.write(buffer, 0, size);
        size = bufferedReader.read(buffer, 0, 2048);
      }
      bufferedWriter.flush();
    }
    finally
    {
      if (bufferedWriter != null) {
        bufferedWriter.close();
      }
      if (bufferedReader != null) {
        bufferedReader.close();
      }
    }
  }
  
  public static byte[] toByteArray(Object obj)
    throws IOException
  {
    ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
    ObjectOutputStream objOutput = null;
    try
    {
      objOutput = new ObjectOutputStream(new BufferedOutputStream(byteOutput));
      objOutput.writeObject(obj);
      objOutput.flush();
    }
    finally
    {
      if (objOutput != null) {
        objOutput.close();
      }
    }
    return byteOutput.toByteArray();
  }
  
  public static Object fromByteArray(byte[] bytes)
    throws IOException, ClassNotFoundException
  {
    ObjectInputStream objInput = null;
    Object obj = null;
    try
    {
      objInput = new ObjectInputStream(new ByteArrayInputStream(bytes));
      obj = objInput.readObject();
    }
    finally
    {
      if (objInput != null) {
        objInput.close();
      }
    }
    return obj;
  }
  
  public static byte[] readBytes(InputStream input)
    throws IOException
  {
    ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
    
    byte[] buffer = new byte[2048];
    int length = input.read(buffer, 0, buffer.length);
    while (length > 0)
    {
      byteOutput.write(buffer, 0, length);
      byteOutput.flush();
      if (length < 2048) {
        break;
      }
      length = input.read(buffer, 0, buffer.length);
    }
    return byteOutput.toByteArray();
  }
  
  public static void writeBytes(byte[] data, OutputStream output)
    throws IOException
  {
    output.write(data, 0, data.length);
    output.flush();
  }
  
  public static String getFileExtension(String filename)
  {
    int dotIndex = filename.lastIndexOf(".");
    return filename.substring(dotIndex);
  }
}
