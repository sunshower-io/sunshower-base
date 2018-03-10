package io.sunshower.lambda;

import io.sunshower.lambda.spliterators.IntTakeUntil;
import io.sunshower.lambda.spliterators.IntTakeWhile;
import io.sunshower.lambda.spliterators.TakeWhile;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Lazy {

  private Lazy() {}

  public static <T> Stream<T> takeWhile(Stream<T> stream, Predicate<T> f) {
    return StreamSupport.stream(new TakeWhile<>(stream.spliterator(), f), false);
  }

  public static class Ints {

    public static IntStream takeUntil(IntStream intStream, IntPredicate condition) {
      return StreamSupport.intStream(new IntTakeUntil(intStream.spliterator(), condition), false);
    }

    public static IntStream takeWhile(IntStream intStream, IntPredicate condition) {
      return StreamSupport.intStream(new IntTakeWhile(intStream.spliterator(), condition), false);
    }
  }
}
