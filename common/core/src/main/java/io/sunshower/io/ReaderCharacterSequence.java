package io.sunshower.io;

import java.io.IOException;
import java.io.Reader;

public class ReaderCharacterSequence implements CharacterSequence {
  private int read;
  private int position;
  private char[] buffer;
  private Reader reader;

  static final int DEFAULT_BUFFER_SIZE = 8192;

  public ReaderCharacterSequence(Reader reader) {
    this.reader = reader;
    buffer = new char[DEFAULT_BUFFER_SIZE];
  }

  @Override
  public char next() {
    if (position < read) {
      return buffer[position++];
    }
    hydrate();
    return buffer[position++];
  }

  private void hydrate() {
    this.position = 0;
    try {
      this.read = reader.read(buffer, 0, DEFAULT_BUFFER_SIZE);
      position = 0;
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }

  @Override
  public boolean hasNext() {
    if (read == 0 || position == read) {
      hydrate();
    }
    return read != -1;
  }

  @Override
  public char peek() {
    if (position < read) {
      return buffer[position];
    }
    hydrate();
    return buffer[position];
  }
}
