package io.sunshower.io;

import java.util.function.IntSupplier;
import java.util.stream.IntStream;

public interface CharacterTokenizer extends IntSupplier {
  char peek();

  char read();

  char push(char ch);

  void forEach(CharToVoid ch);

  IntStream stream();

  IntStream filter(CharPredicate predicate);
}
