package io.sunshower.lang.primitives;

import static org.junit.Assert.*;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class FloatsTest {

  @Test
  public void ensureCopyingFloatsProducesExpectedResults() {
    float[] a =
        new float[] {
          1f, 4.0f, 0.0f, 400f, 1245.1f, 900f, Float.MAX_VALUE, Float.MIN_VALUE, Float.NaN
        };
    float[] b = Floats.fromByteArray(Floats.toByteArray(a));
    System.out.println(Arrays.toString(b));
    assertArrayEquals(a, b, 0.0f);
  }
}
