package io.sunshower.io;

public class IO {

  public static CharacterTokenizer tokenize(CharSequence sequence) {
    return new CharSequenceTokenizer(sequence);
  }
}
