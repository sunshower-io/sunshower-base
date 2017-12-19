package io.sunshower.lang.primitives;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;


import static org.junit.Assert.*;


@RunWith(JUnitPlatform.class)
public class IntegersTest {

    int[] copy(int[] a) {
        return Integers.fromByteArray(Integers.toByteArray(a));
    }

    @Test
    public void ensureIntegerArrayIsCopiedCorrectly() {
        int[] a = new int[]{1, 2, 3, 4, 5, 6, 7, 18, Integer.MAX_VALUE, Integer.MIN_VALUE};
        assertArrayEquals(Integers.fromByteArray(Integers.toByteArray(a)), a);
    }

}