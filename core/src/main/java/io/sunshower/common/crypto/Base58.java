package io.sunshower.common.crypto;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * Created by haswell on 11/8/16.
 * @deprecated Use io.sunshower.encodings.Base58 instead
 */

@Deprecated
public class Base58 {


    public static final char[] CHARS =
            ("123456789ABCDEFGHJKLMNPQRSTU" +
                    "VWXYZabcdefghijkmnopqrstuvwxyz"
            ).toCharArray();


    private static final char ZERO = CHARS[0];
    private static final int[] INDEXES = new int[128];

    static {
        Arrays.fill(INDEXES, -1);
        for (int i = 0; i < CHARS.length; i++) {
            INDEXES[CHARS[i]] = i;
        }
    }

    /**
     * @param input
     * @return
     */

    public static String encode(byte[] input) {
        if (input.length == 0) {
            return "";
        }
        int zeros = 0;
        while (zeros < input.length && input[zeros] == 0) {
            ++zeros;
        }
        input = Arrays.copyOf(input, input.length);
        char[] encoded = new char[input.length * 2];
        int outputStart = computeStart(input, zeros, encoded);
        while (outputStart < encoded.length && encoded[outputStart] == ZERO) {
            ++outputStart;
        }
        while (--zeros >= 0) {
            encoded[--outputStart] = ZERO;
        }
        return new String(encoded, outputStart, encoded.length - outputStart);
    }

    private static int computeStart(byte[] input, int zeros, char[] encoded) {
        int outputStart = encoded.length;
        for (int inputStart = zeros; inputStart < input.length; ) {
            encoded[--outputStart] = CHARS[divide(input, inputStart, 256, 58)];
            if (input[inputStart] == 0) {
                ++inputStart;
            }
        }
        return outputStart;
    }

    /**
     * @param input
     * @return
     */

    public static BigInteger numericValue(String input) {
        return new BigInteger(1, decode(input));
    }

    public static byte[] decode(String input) {
        if (input.length() == 0) {
            return new byte[0];
        }

        byte[] input58 = new byte[input.length()];
        int zeros = fill(input, input58);
        return doDecode(input, input58, zeros);
    }

    private static int fill(String input, byte[] input58) {
        for (int i = 0; i < input.length(); ++i) {
            char c = input.charAt(i);
            int digit = c < 128 ? INDEXES[c] : -1;
            if (digit < 0) {
                throw new IllegalArgumentException("unrecognized " + c + " at " + i);
            }
            input58[i] = (byte) digit;
        }
        int zeros = 0;
        while (zeros < input58.length && input58[zeros] == 0) {
            ++zeros;
        }
        return zeros;
    }

    private static byte[] doDecode(String input, byte[] input58, int zeros) {
        byte[] decoded = new byte[input.length()];

        int outputStart = decoded.length;
        for (int inputStart = zeros; inputStart < input58.length; ) {
            decoded[--outputStart] = divide(input58, inputStart, 58, 256);
            if (input58[inputStart] == 0) {
                ++inputStart;
            }
        }
        while (outputStart < decoded.length && decoded[outputStart] == 0) {
            ++outputStart;
        }

        return Arrays.copyOfRange(decoded, outputStart - zeros, decoded.length);
    }


    private static byte divide(byte[] number, int firstDigit, int base, int divisor) {
        int remainder = 0;
        for (int i = firstDigit; i < number.length; i++) {
            int digit = (int) number[i] & 0xFF;
            int temp = remainder * base + digit;
            number[i] = (byte) (temp / divisor);
            remainder = temp % divisor;
        }
        return (byte) remainder;
    }
}