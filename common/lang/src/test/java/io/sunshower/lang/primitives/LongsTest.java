package io.sunshower.lang.primitives;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class LongsTest {

  @Test
  public void ensureCopyingLongsProducesExpectedResult() {
    long[] l = new long[] {0, -123124, Long.MAX_VALUE, Long.MIN_VALUE};
    long[] k = Longs.fromByteArray(Longs.toByteArray(l));
    assertArrayEquals(l, k);
  }
}
