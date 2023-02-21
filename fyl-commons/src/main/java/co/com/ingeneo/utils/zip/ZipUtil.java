package co.com.ingeneo.utils.zip;

import java.io.ByteArrayOutputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ZipUtil {
  
    public static byte[] compress(byte[] data) {
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        Deflater compressor = new Deflater(9);
        compressor.setInput(data);
        compressor.finish();

        byte[] buffer = new byte[2048];
        while (!compressor.finished()) {
            int count = compressor.deflate(buffer);
            byteOutput.write(buffer, 0, count);
        }
        return byteOutput.toByteArray();
    }

    public static byte[] decompress(byte[] data) {
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        Inflater decompressor = new Inflater();
        decompressor.setInput(data);

        byte[] buffer = new byte[2048];
        try {
            while (!decompressor.finished()) {
                int count = decompressor.inflate(buffer);
                byteOutput.write(buffer, 0, count);
            }
        } catch (DataFormatException dEx) {
            throw new RuntimeException(dEx.getMessage(), dEx);
        }
        return byteOutput.toByteArray();
    }
}
