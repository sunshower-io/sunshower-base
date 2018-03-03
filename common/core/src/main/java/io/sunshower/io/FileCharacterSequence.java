package io.sunshower.io;

import java.io.*;

public class FileCharacterSequence implements CharSequence, AutoCloseable {
  private final int length;
  private final StringBuilder buffer = new StringBuilder();
  private final InputStream input;

  public FileCharacterSequence(File file) throws FileNotFoundException {
    if (file.length() > (long) Integer.MAX_VALUE) {
      throw new IllegalArgumentException("Cannot handle file of this size");
    }
    this.length = (int) file.length();
    this.input = new BufferedInputStream(new FileInputStream(file));
  }

  @Override
  public int length() {
    return length;
  }

  @Override
  public char charAt(int index) {
    ensureFilled(index + 1);
    return buffer.charAt(index);
  }

  @Override
  public CharSequence subSequence(int start, int end) {
    ensureFilled(end + 1);
    return buffer.subSequence(start, end);
  }

  private void ensureFilled(int index) {
    if (buffer.length() < index) {
      buffer.ensureCapacity(index);
      final byte[] bytes = new byte[index - buffer.length()];
      try {
        int length = input.read(bytes);
        if (length < bytes.length) {
          throw new IllegalArgumentException("Unexpected EOF");
        }
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      try {
        buffer.append(new String(bytes, "utf-8"));
      } catch (UnsupportedEncodingException ex) {
        // meh
      }
    }
  }

  @Override
  public void close() throws Exception {
    this.input.close();
  }
}
