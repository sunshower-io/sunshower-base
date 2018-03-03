package io.sunshower.io;

import java.util.stream.IntStream;

public class CharSequenceTokenizer implements CharacterTokenizer {

  public static final char EOF = '\0';

  private int index;
  private char unread;
  private boolean nextUnread;
  private final CharSequence seq;

  private boolean skipCarriageReturn;

  public CharSequenceTokenizer(final CharSequence seq) {
    this.seq = seq;
    this.index = 0;
    this.skipCarriageReturn = true;
  }

  @Override
  public char peek() {
    if (nextUnread) {
      return unread;
    }
    if (index < seq.length()) {
      return collapseNewline(seq.charAt(index));
    }
    return EOF;
  }

  @Override
  public char read() {
    if (nextUnread) {
      nextUnread = false;
      return unread;
    }

    if (index < seq.length()) {
      skipCarriageReturn();
      return collapseNewline(seq.charAt(index++));
    }
    return EOF;
  }

  @Override
  public char push(char ch) {
    if (nextUnread) {
      throw new IllegalStateException("This is a LL(1) parser--cannot unread more than 1 token");
    }
    nextUnread = true;
    unread = ch;
    return ch;
  }

  @Override
  public void forEach(CharToVoid ch) {
    stream().forEach(t -> ch.apply((char) t));
  }

  @Override
  public IntStream stream() {
    return IntStream.generate(this).limit(seq.length()).filter(t -> (char) t != EOF);
  }

  @Override
  public IntStream filter(CharPredicate predicate) {
    return stream().filter((ch) -> predicate.apply((char) ch));
  }

  private char collapseNewline(char ch) {
    return ch == '\r' ? '\n' : ch;
  }

  private void skipCarriageReturn() {
    if (index + 1 < seq.length()) {
      char current = seq.charAt(index);
      char next = seq.charAt(index + 1);
      if (current == '\r' && next == '\n') {
        index++;
      }
    }
  }

  @Override
  public int getAsInt() {
    return read();
  }
}
