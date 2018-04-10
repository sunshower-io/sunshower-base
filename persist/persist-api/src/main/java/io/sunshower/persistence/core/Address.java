package io.sunshower.persistence.core;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Arrays;
import java.util.Objects;

public abstract class Address {

  protected char[] value;

  protected Address() {}

  protected Address(char[] value) {
    Objects.requireNonNull(value);
    this.value = value;
  }

  public String toString() {
    return Arrays.toString(value);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Address)) return false;
    Address that = (Address) o;
    return Arrays.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(value);
  }

  public char[] getValue() {
    return value;
  }
}
