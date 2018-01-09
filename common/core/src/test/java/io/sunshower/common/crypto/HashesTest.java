package io.sunshower.common.crypto;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class HashesTest {

  static class Whatever {
    public String toString() {
      return "Frappus";
    }
  }

  @Test
  public void ensureHashFunctionReturnsExpectedResults_256() {
    Hashes.HashFunction hashFunction = Hashes.create(Multihash.Type.SHA_2_256);
    Multihash expected = Multihash.fromBase58("QmRN6wdp1S2A5EtjW9A3M1vKSBuQQGcgvuhoMUoEz4iiT5");
    Multihash actual = hashFunction.compute("hello".getBytes());
    assertThat(actual, is(expected));
  }
}
