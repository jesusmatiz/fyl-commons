package co.com.ingeneo.utils.crypto;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

public class KeyUtil
{
  private static PrivateKey privateKey = null;
  private static PublicKey publicKey = null;
  
  public static PrivateKey getPrivateKey()
  {
    return privateKey;
  }
  
  public static PublicKey getPublicKey()
  {
    return publicKey;
  }
  
  public static void loadPublicKeyFromCertificate(String cerFileName)
  {
    try
    {
      publicKey = getPublicKeyFromCertificate(cerFileName);
    }
    catch (Exception ex)
    {
      throw new RuntimeException(ex.getMessage(), ex.getCause());
    }
  }
  
  public static void loadPrivateKeyFromKeystore(String alias, String jksFileName, String jksPassword, String keyPassword)
  {
    try
    {
      privateKey = getPrivateKeyFromKeystore(jksFileName, jksPassword, alias, keyPassword);
    }
    catch (Exception ex)
    {
      throw new RuntimeException(ex.getMessage(), ex.getCause());
    }
  }
  
  private static PublicKey getPublicKeyFromCertificate(String cerFileName)
    throws IOException, CertificateException
  {
    InputStream cerInputStream = KeyUtil.class.getClassLoader().getResourceAsStream(cerFileName);
    if (cerInputStream == null) {
      throw new FileNotFoundException("File " + cerFileName + " does not exist in the classloader");
    }
    CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
    Certificate certificate = certificateFactory.generateCertificate(cerInputStream);
    return certificate.getPublicKey();
  }
  
  private static PrivateKey getPrivateKeyFromKeystore(String jksFileName, String jksPassword, String alias, String keyPassword)
    throws IOException, GeneralSecurityException
  {
    InputStream jksInputStream = KeyUtil.class.getClassLoader().getResourceAsStream(jksFileName);
    if (jksInputStream == null) {
      throw new FileNotFoundException("File " + jksFileName + " does not exist in the classloader");
    }
    KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
    keyStore.load(jksInputStream, jksPassword.toCharArray());
    Key key = keyStore.getKey(alias, keyPassword.toCharArray());
    
    return (key instanceof PrivateKey) ? (PrivateKey)key : null;
  }
}
