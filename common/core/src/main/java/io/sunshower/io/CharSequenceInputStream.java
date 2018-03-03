package io.sunshower.io;

import java.io.IOException;
import java.io.InputStream;

public class CharSequenceInputStream extends InputStream implements CharSequence {
  @Override
  public int read() throws IOException {
    return 0;
  }

  @Override
  public int length() {
    return 0;
  }

  @Override
  public char charAt(int index) {
    return 0;
  }

  @Override
  public CharSequence subSequence(int start, int end) {
    return null;
  }
}
