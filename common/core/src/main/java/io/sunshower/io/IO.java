package io.sunshower.io;

import java.io.Reader;

public class IO {
    

  public static CharacterTokenizer tokenize(Reader sequence) {
    return new CharSequenceTokenizer(new ReaderCharacterSequence(sequence));
  }

  public static CharacterTokenizer tokenize(CharSequence sequence) {
    return new CharSequenceTokenizer(new CharacterSequenceSequence(sequence));
  }
}
