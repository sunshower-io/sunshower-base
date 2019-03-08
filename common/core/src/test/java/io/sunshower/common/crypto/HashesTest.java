package io.sunshower.common.crypto;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

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

  @Test
  void hashJalapeno() {
    Hashes.HashFunction hashFunction = Hashes.create(Multihash.Type.SHA_2_512);

    String toHash = "jalapeno.ii";

    final String hash = hashFunction.hash(toHash);
    System.out.println(hash);

    final String actual = hashFunction.hash("jalapeno.io");
    System.out.println("Actually, what I was expecting is:\n" + actual);
  }
}
