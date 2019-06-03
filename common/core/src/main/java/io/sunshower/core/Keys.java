package io.sunshower.core;

import java.text.Normalizer;

public class Keys {
  private Keys() {
    throw new UnsupportedOperationException("No keys for you!");
  }

  public static String asKey(String input) {
    if (input == null || input.isEmpty()) {
      throw new IllegalArgumentException("Cannot construct a key from nothing or the empty string");
    }
    return Normalizer.normalize(input, Normalizer.Form.NFD)
        .replaceAll("[^\\p{ASCII}]", "")
        .replaceAll("[^a-zA-Z]", "_")
        .toUpperCase();
  }
}
