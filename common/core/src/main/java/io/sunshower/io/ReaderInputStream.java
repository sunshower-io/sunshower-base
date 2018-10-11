package io.sunshower.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public class ReaderInputStream extends InputStream {

  private final Reader reader;

  public ReaderInputStream(final Reader reader) {
    if (reader instanceof BufferedReader) {
      this.reader = reader;
    } else {
      this.reader = new BufferedReader(reader);
    }
  }

  public int read() throws IOException {
    return reader.read();
  }
}
