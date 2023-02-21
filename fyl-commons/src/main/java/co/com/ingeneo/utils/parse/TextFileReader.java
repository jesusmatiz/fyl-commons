package co.com.ingeneo.utils.parse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TextFileReader
{
  private int nLines;
  private int minSizeBuffer;
  private boolean eof = false;
  private int currentLine;
  private BufferedReader reader = null;
  private ArrayList<String> buffer = new ArrayList();
  
  public TextFileReader(File file)
    throws FileNotFoundException
  {
    this(file, 10, 5);
  }
  
  public TextFileReader(InputStream inputStream)
    throws IOException
  {
    this.nLines = 10;
    this.minSizeBuffer = 5;
    InputStreamReader isr = new InputStreamReader(inputStream);
    this.reader = new BufferedReader(isr);
  }
  
  public TextFileReader(File file, int pNLines, int pMinSizeBuffer)
    throws FileNotFoundException
  {
    this.nLines = pNLines;
    this.minSizeBuffer = pMinSizeBuffer;
    this.reader = new BufferedReader(new FileReader(file));
  }
  
  public String nextLine()
    throws IOException
  {
    if ((this.buffer.size() <= this.minSizeBuffer) && (!this.eof)) {
      readNextBlock();
    }
    return this.buffer.size() != 0 ? (String)this.buffer.remove(0) : null;
  }
  
  public void moveToLine(int line)
    throws IOException
  {
    for (int i = this.currentLine; i < line; this.currentLine += 1)
    {
      nextLine();i++;
    }
  }
  
  private void readNextBlock()
    throws IOException
  {
    String line = null;
    try
    {
      for (int i = 0; (i < this.nLines) && ((line = this.reader.readLine()) != null); i++) {
        this.buffer.add(line);
      }
    }
    finally
    {
      if (line == null)
      {
        this.eof = true;
        this.reader.close();
        this.reader = null;
      }
    }
  }
  
  public void close()
    throws IOException
  {
    if (this.reader != null)
    {
      this.reader.close();
      this.reader = null;
    }
  }
}
