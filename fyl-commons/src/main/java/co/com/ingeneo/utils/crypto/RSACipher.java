package co.com.ingeneo.utils.crypto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;

public class RSACipher
{
  private static final String RSA = "RSA";
  private static final int ENCRYPT_SIZE_BLOCK = 100;
  private static final int DECRYPT_SIZE_BLOCK = 128;
  
  public static byte[] encrypt(PublicKey publicKey, byte[] data)
    throws GeneralSecurityException, IOException
  {
    ByteArrayInputStream byteInput = new ByteArrayInputStream(data);
    ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
    
    Cipher encryptor = Cipher.getInstance("RSA");
    encryptor.init(1, publicKey);
    byte[] buffer = new byte[100];
    int size = byteInput.read(buffer, 0, 100);
    while (size != -1)
    {
      byte[] encrypted = encryptor.doFinal(buffer);
      byteOutput.write(encrypted, 0, encrypted.length);
      byteOutput.flush();
      size = byteInput.read(buffer, 0, 100);
    }
    return byteOutput.toByteArray();
  }
  
  public static byte[] decrypt(PrivateKey privateKey, byte[] data)
    throws GeneralSecurityException, IOException
  {
    ByteArrayInputStream byteInput = new ByteArrayInputStream(data);
    ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
    
    Cipher decryptor = Cipher.getInstance("RSA");
    decryptor.init(2, privateKey);
    byte[] buffer = new byte[''];
    int size = byteInput.read(buffer, 0, 128);
    while (size != -1)
    {
      byte[] decrypted = decryptor.doFinal(buffer);
      byteOutput.write(decrypted, 0, decrypted.length);
      byteOutput.flush();
      size = byteInput.read(buffer, 0, 128);
    }
    return byteOutput.toByteArray();
  }
}
