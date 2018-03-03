package io.sunshower.io;

import io.sunshower.lambda.Lazy;

import java.util.stream.IntStream;

public class CharSequenceTokenizer implements CharacterTokenizer {

  public static final char EOF = '\0';

  private char unread;
  private boolean nextUnread;
  private final CharacterSequence seq;

  private boolean skipCarriageReturn;

  public CharSequenceTokenizer(final CharacterSequence seq) {
    this.seq = seq;
    this.skipCarriageReturn = true;
  }

  @Override
  public char peek() {
    if (nextUnread) {
      return unread;
    }
    if (seq.hasNext()) {
      return collapseNewline(seq.peek());
    }
    return EOF;
  }

  @Override
  public char read() {
    if (nextUnread) {
      nextUnread = false;
      return unread;
    }

    if (seq.hasNext()) {
      skipCarriageReturn();
      return collapseNewline(seq.next());
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

  boolean hasNext = true;

  @Override
  public IntStream stream() {
    return Lazy.Ints.takeWhile(IntStream.generate(this), new LookPast(seq));
  }

  @Override
  public IntStream filter(CharPredicate predicate) {
    return stream().filter((ch) -> predicate.apply((char) ch));
  }

  private char collapseNewline(char ch) {
    return ch == '\r' ? '\n' : ch;
  }

  private void skipCarriageReturn() {
    if (seq.hasNext()) {
      char current = seq.peek();
      if (current == '\r') {
        seq.next();
      }
    }
  }

  @Override
  public int getAsInt() {
    return read();
  }
}
