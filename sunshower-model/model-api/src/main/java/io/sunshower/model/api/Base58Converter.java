package io.sunshower.model.api;

import io.sunshower.arcus.condensation.Converter;
import io.sunshower.lang.common.encodings.Encoding;
import io.sunshower.lang.common.encodings.Encodings;
import io.sunshower.lang.common.encodings.Encodings.Type;

public class Base58Converter implements Converter<byte[], String> {

  static final Encoding encoding;

  static {
    encoding = Encodings.create(Type.Base58);
  }

  @Override
  public byte[] read(String s) {
    if (s == null) {
      return null;
    }
    return encoding.decode(s);
  }

  @Override
  public String write(byte[] bytes) {
    if (bytes == null) {
      return null;
    }
    return encoding.encode(bytes);
  }
}
