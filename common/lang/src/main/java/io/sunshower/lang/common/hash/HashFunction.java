package io.sunshower.lang.common.hash;

import io.sunshower.lang.common.hash.integers.IntegerHashFunction;
import io.sunshower.lang.common.hash.integers.Murmur3;
import io.sunshower.lang.common.hash.integers.StringHash;

public interface HashFunction {

  int bits();

  static IntegerHashFunction murmur3(int i) {
    return new Murmur3(i);
  }

  static IntegerHashFunction jdk() {
    return StringHash.INSTANCE;
  }
}
