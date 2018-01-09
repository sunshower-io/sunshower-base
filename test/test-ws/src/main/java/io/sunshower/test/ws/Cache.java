package io.sunshower.test.ws;

import java.util.LinkedHashMap;
import java.util.Map;

class Cache extends LinkedHashMap<String, Class<?>> {

  private final int maxSize;

  Cache(int maxSize) {
    super(16, 0.75f, true);
    this.maxSize = maxSize;
  }

  @Override
  protected boolean removeEldestEntry(Map.Entry<String, Class<?>> eldest) {
    return size() > this.maxSize;
  }
}
