package io.sunshower.lang.tuple;

public class Tuple {
  private Tuple() {
    throw new RuntimeException("No Tuple instances for you!");
  }

  public static <K, V> Pair<K, V> of(K fst, V snd) {
    return Pair.of(fst, snd);
  }
}
