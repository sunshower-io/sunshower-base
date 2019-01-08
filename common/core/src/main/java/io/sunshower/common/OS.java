package io.sunshower.common;

import java.util.Locale;

public class OS {
  public enum Type {
    Windows,
    Linux,
    Apple,
    Other
  }

  protected static Type type;
  /// http://www.docjar.com/html/api/org/apache/commons/lang/SystemUtils.java.html

  public static Type resolve() {
    if (type == null) {
      String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
      if ((OS.contains("mac")) || (OS.contains("darwin"))) {
        type = Type.Apple;
      } else if (OS.contains("win")) {
        type = Type.Windows;
      } else if (OS.contains("nux")) {
        type = Type.Linux;
      } else {
        type = Type.Other;
      }
    }
    return type;
  }
}
