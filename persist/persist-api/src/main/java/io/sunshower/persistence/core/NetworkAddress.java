package io.sunshower.persistence.core;

import javax.persistence.Embeddable;

@Embeddable
public class NetworkAddress extends Address {

  public NetworkAddress() {}

  public NetworkAddress(char[] value) {
    super(value);
  }
}
