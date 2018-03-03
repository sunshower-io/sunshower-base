package io.sunshower.lambda.spliterators;

import java.util.Spliterator;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;

public class IntTakeWhile implements Spliterator.OfInt {

  private final OfInt source;
  private final IntPredicate condition;
  private boolean conditionHolds = true;

  public IntTakeWhile(OfInt source, IntPredicate condition) {
    this.source = source;
    this.condition = condition;
  }

  @Override
  public OfInt trySplit() {
    return source.trySplit();
  }

  @Override
  public long estimateSize() {
    return source.estimateSize();
  }

  @Override
  public int characteristics() {
    return source.characteristics();
  }

  @Override
  public boolean tryAdvance(IntConsumer action) {
    return conditionHolds
        && source.tryAdvance(
            (IntConsumer)
                (e) -> {
                  if (condition.test(e)) {
                    action.accept(e);
                  } else conditionHolds = false;
                });
  }
}
