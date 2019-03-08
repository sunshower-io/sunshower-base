package io.sunshower.lang.common;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

public class StringsTest {

  @Test
  public void ensureEmptyStringIsEmpty() {
    assertThat(Strings.isBlank(""), is(true));
  }
}
