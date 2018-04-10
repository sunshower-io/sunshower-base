package io.sunshower.persistence.core;

import sun.net.util.IPAddressUtil;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
public class NetworkAddress extends Address {

  public enum Type {
    IPV4,
    IPV6,
    DNS
  }

  @Transient private transient Type version;

  public NetworkAddress() {}

  public NetworkAddress(char[] value) {
    super(value);
    checkValue();
  }

  public NetworkAddress(String chars) {
    super(chars);

    checkValue();
  }

  private void checkValue() {
    if (IPAddressUtil.isIPv4LiteralAddress(value)) {
      version = Type.IPV4;
    } else if (IPAddressUtil.isIPv6LiteralAddress(value)) {
      version = Type.IPV6;
    } else {
      version = Type.DNS;
    }
  }

  public Type getType() {
    return version;
  }
}
