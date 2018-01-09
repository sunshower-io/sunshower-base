package io.sunshower.lang.common;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class StringsTest {

  @Test
  public void ensureEmptyStringIsEmpty() {
    assertThat(Strings.isBlank(""), is(true));
  }
}
