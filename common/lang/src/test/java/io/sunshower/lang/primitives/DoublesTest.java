package io.sunshower.lang.primitives;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class DoublesTest {

  @Test
  public void ensureDoublesArrayIsCopiedCorrectly() {
    double[] d =
        new double[] {0.0d, 4.0d, 99.45d, 113414123451234.4d, Double.MAX_VALUE, Double.MIN_VALUE};

    double[] c = Doubles.fromByteArray(Doubles.toByteArray(d));
    assertArrayEquals(d, c);
  }

  @Test
  void ensureNextDoubleIsPositive() {
    System.out.println(Doubles.next(0.0d));
    assertThat(Doubles.next(0.0) > 0, is(true));
  }
}
