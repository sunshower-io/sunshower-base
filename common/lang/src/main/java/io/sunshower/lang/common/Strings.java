package io.sunshower.lang.common;

import javax.annotation.Nullable;

public class Strings {
  private Strings() {}

  public static boolean isBlank(@Nullable String value) {
    if (value == null) {
      return true;
    }
    return value.trim().isEmpty();
  }
}
