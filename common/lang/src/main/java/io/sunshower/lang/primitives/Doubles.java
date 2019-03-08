package io.sunshower.lang.primitives;

import static java.lang.Double.doubleToLongBits;
import static java.lang.Double.longBitsToDouble;

import java.nio.ByteBuffer;
import lombok.val;
import lombok.var;

public class Doubles {

  public static double next(double n) {
    if (Double.isInfinite(n) || Double.isNaN(n)) {
      return n;
    }

    if (n == 0) {
      return Double.MIN_VALUE;
    }

    val bytes = toByteArray(n);
    val sign = (byte) (bytes[7] & 0x80);
    var exponent = (byte) ((((long) bytes[7]) & 0x7F) << 4) + (((long) bytes[6] >> 4) & 0x0F);
    var mantissa =
        ((long) 1 << 52)
            | (((long) bytes[6] & 0x0F) << 48)
            | (((long) bytes[5]) << 40)
            | (((long) bytes[4]) << 32)
            | (((long) bytes[3]) << 24)
            | (((long) bytes[2]) << 16)
            | (((long) bytes[1]) << 8)
            | ((long) bytes[0]);
    --mantissa;

    if ((mantissa & ((long) 1 << 52)) == 0) {
      mantissa <<= 1;
      exponent--;
    }

    bytes[7] = (byte) ((long) sign | ((exponent >> 4) & 0x7F));
    bytes[6] = (byte) (((exponent & 0x0F) << 4) | ((mantissa >> 48) & 0x0F));
    bytes[5] = (byte) ((mantissa >> 40) & 0xFF);
    bytes[4] = (byte) ((mantissa >> 32) & 0xFF);
    bytes[3] = (byte) ((mantissa >> 24) & 0xFF);
    bytes[2] = (byte) ((mantissa >> 16) & 0xFF);
    bytes[1] = (byte) ((mantissa >> 8) & 0xFF);
    bytes[0] = (byte) (mantissa & 0xFF);
    return ByteBuffer.wrap(bytes).getDouble();
  }

  public static final byte[] toByteArray(double[] ds) {
    int length = ds.length;
    byte[] result = new byte[length * 8];
    for (int i = 0, j = 0; i < length; i++) {
      long data = doubleToLongBits(ds[i]);
      result[j] = (byte) (data >>> 56);
      result[j + 1] = (byte) (data >>> 48);
      result[j + 2] = (byte) (data >>> 40);
      result[j + 3] = (byte) (data >>> 32);
      result[j + 4] = (byte) (data >>> 24);
      result[j + 5] = (byte) (data >>> 16);
      result[j + 6] = (byte) (data >>> 8);
      result[j + 7] = (byte) (data);
      j += 8;
    }
    return result;
  }

  public static final byte[] toByteArray(double n) {
    val d = doubleToLongBits(n);
    val result = new byte[8];
    result[1] = (byte) (d >>> 48);
    result[2] = (byte) (d >>> 40);
    result[3] = (byte) (d >>> 32);
    result[4] = (byte) (d >>> 24);
    result[5] = (byte) (d >>> 16);
    result[6] = (byte) (d >>> 8);
    result[7] = (byte) (d);
    return result;
  }

  public static final double[] fromByteArray(byte[] floats) {
    final int len = floats.length;
    if (len % 8 != 0) {
      throw new IllegalArgumentException("Byte array must be divisible by 8");
    }
    final double[] result = new double[len / 8];
    for (int i = 0, j = 0; i < result.length; i++) {
      long r =
          (long) floats[j] << 56
              | (long) (floats[j + 1] & 0xFF) << 48
              | (long) (floats[j + 2] & 0xFF) << 40
              | (long) (floats[j + 3] & 0xFF) << 32
              | (long) (floats[j + 4] & 0xFF) << 24
              | (long) (floats[j + 5] & 0xFF) << 16
              | (long) (floats[j + 6] & 0xFF) << 8
              | (long) (floats[j + 7] & 0xFF);
      result[i] = longBitsToDouble(r);
      j += 8;
    }
    return result;
  }
}
