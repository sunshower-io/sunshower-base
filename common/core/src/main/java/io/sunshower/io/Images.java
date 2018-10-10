package io.sunshower.io;

import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import javax.imageio.ImageIO;

public class Images {

  public static Image fromBase64(byte[] bytes) throws IOException {
    return fromBase64(bytes, false);
  }

  public static Image fromBase64(byte[] data, boolean compressed) throws IOException {
    byte[] bytes = compressed ? compress(data) : data;
    final ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
    return ImageIO.read(inputStream);
  }

  public static Image fromBase64(String data) throws IOException {
    final byte[] decode = Base64.getDecoder().decode(data);
    return fromBase64(decode);
  }

  public static String toBase64(Image image, String format) throws IOException {
    final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    ImageIO.write((RenderedImage) image, "png", outputStream);
    return Base64.getEncoder().encodeToString(outputStream.toByteArray());
  }

  public static byte[] decompress(byte[] data) throws IOException, DataFormatException {
    Inflater inflater = new Inflater();
    inflater.setInput(data);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
    byte[] buffer = new byte[1024];
    while (!inflater.finished()) {
      int count = inflater.inflate(buffer);
      outputStream.write(buffer, 0, count);
    }
    outputStream.close();
    byte[] output = outputStream.toByteArray();
    return output;
  }

  public static byte[] compress(byte[] data) throws IOException {
    Deflater deflater = new Deflater();
    deflater.setInput(data);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
    deflater.finish();
    byte[] buffer = new byte[1024];
    while (!deflater.finished()) {
      int count = deflater.deflate(buffer); // returns the generated code... index
      outputStream.write(buffer, 0, count);
    }
    outputStream.close();
    byte[] output = outputStream.toByteArray();
    return output;
  }

  public static String toBase64(byte[] data) {
    return Base64.getEncoder().encodeToString(data);
  }

  public static byte[] readAndCompress(InputStream imgstream) throws IOException {
    final byte[] read = Files.read(imgstream);
    return compress(read);
  }

  public static String decompressToBase64(byte[] data) throws IOException, DataFormatException {
    return toBase64(decompress(data));
  }

  public static byte[] read(ReaderInputStream readerInputStream) throws IOException {
    return Files.read(readerInputStream);
  }
}
