package co.com.ingeneo.utils.xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLUtils
{
  public static void validateXML(Reader inputXML, InputStream schemaStream)
    throws IOException, SAXException, ParserConfigurationException
  {
    DocumentBuilder parser = null;
    InputSource source = null;
    Document document = null;
    SchemaFactory factory = null;
    InputStream is = null;
    Source schemaFile = null;
    Schema schema = null;
    Validator validator = null;
    try
    {
      parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      source = new InputSource(inputXML);
      document = parser.parse(source);
      

      factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
      

      schemaFile = new StreamSource(schemaStream);
      schema = factory.newSchema(schemaFile);
      

      validator = schema.newValidator();
      

      validator.validate(new DOMSource(document));
    }
    finally
    {
      parser = null;
      source = null;
      document = null;
      factory = null;
      if (is != null) {
        is.close();
      }
      is = null;
      schemaFile = null;
      schema = null;
      validator = null;
    }
  }
  
  public static String transformXML(Reader inputXML, InputStream isStyleSource)
    throws TransformerException
  {
    StringWriter writerOut = new StringWriter();
    StreamSource source = new StreamSource(inputXML);
    StreamSource styleSource = new StreamSource(isStyleSource);
    
    TransformerFactory factory = TransformerFactory.newInstance();
    Transformer transformer = factory.newTransformer(styleSource);
    
    StreamResult result = new StreamResult(writerOut);
    transformer.transform(source, result);
    return writerOut.toString();
  }
  
  public static void main(String[] args)
  {
    try
    {
      String filenameXML = args[0];
      String filenameXSL = args[1];
      String filenameXSD = args[2];
      String filenameTXT = args[3];
      validateXML(new BufferedReader(new FileReader(filenameXML)), new FileInputStream(filenameXSD));
      String output = transformXML(new BufferedReader(new FileReader(filenameXML)), new FileInputStream(filenameXSL));
      
      String[] outputSplit = output.split("\n");
      StringBuffer outputBuffer = new StringBuffer();
      boolean first = true;
      for (int i = 0; i < outputSplit.length; i++)
      {
        String outputTempTrim = outputSplit[i].trim();
        if (outputTempTrim.length() > 0) {
          if (first)
          {
            outputBuffer.append(outputTempTrim);
            first = false;
          }
          else
          {
            outputBuffer.append("\n");
            outputBuffer.append(outputTempTrim);
          }
        }
      }
      PrintWriter writer = new PrintWriter(new File(filenameTXT));
      writer.print(outputBuffer.toString());
      writer.flush();
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }
}
