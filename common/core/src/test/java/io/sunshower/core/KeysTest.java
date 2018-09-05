package io.sunshower.core;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
class KeysTest {

  @Test
  public void ensureKeysNormalizeCorrectly() {
    String key = Keys.asKey("Tĥïŝ ĩš â fůňķŷ Šťŕĭńġ; don't you think so?");
    assertThat(key, is("THIS_IS_A_FUNKY_STRING__DON_T_YOU_THINK_SO_"));
  }
}
