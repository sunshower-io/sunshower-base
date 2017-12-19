package io.sunshower.common.crypto;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by haswell on 11/22/16.
 */
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

//    @Test
//    public void ensureHashFunctionReturnsExpectedResults_512() {
//        Hashes.HashFunction hashFunction = Hashes.create(Multihash.Type.SHA_2_512);
//        String expected = "5qWYQ9U6jDxZmm45p2gGLpYUKN8hhTXhtQo93NEU2EKFuuq4svEQ4kuoLhdbhEvqt7zZ38gkzqAVsFGXua6piiqJ";
//        String actual = hashFunction.hash("hello", null, new Whatever());
//        assertThat(actual, is(expected));
//    }
}