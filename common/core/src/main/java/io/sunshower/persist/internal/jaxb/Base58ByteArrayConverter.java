package io.sunshower.persist.internal.jaxb;

import io.sunshower.encodings.Base58;
import io.sunshower.encodings.Encoding;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class Base58ByteArrayConverter extends XmlAdapter<String, byte[]> {

  static final Encoding encoding = Base58.getInstance(Base58.Alphabets.Default);

  @Override
  public byte[] unmarshal(String v) throws Exception {
    return encoding.decode(v);
  }

  @Override
  public String marshal(byte[] v) throws Exception {
    if (v == null) {
      return null;
    }
    return encoding.encode(v);
  }
}
