package io.sunshower.io;

import java.util.function.IntPredicate;

final class LookPast implements IntPredicate {
  private boolean next;
  final CharacterSequence seq;

  public LookPast(CharacterSequence seq) {
    this.next = true;
    this.seq = seq;
  }

  @Override
  public boolean test(int value) {
    if (!next) {
      return false;
    }
    next = seq.hasNext();
    return true;
  }
}
