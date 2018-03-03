package io.sunshower.io;

public class CharacterSequenceSequence implements CharacterSequence {
  private int index;
  final CharSequence store;

  public CharacterSequenceSequence(CharSequence store) {
    this.index = 0;
    this.store = store;
  }

  @Override
  public char next() {
    return store.charAt(index++);
  }

  @Override
  public boolean hasNext() {
    return store.length() > index;
  }

  @Override
  public char peek() {
    if (index == store.length()) {
      return '\0';
    }
    return store.charAt(index);
  }
}
