package io.sunshower.persist;

import java.io.Serializable;

public interface Persistable<ID extends Serializable> {

  ID getId();

  int hashCode();

  boolean equals(Object o);

  String toString();
}
