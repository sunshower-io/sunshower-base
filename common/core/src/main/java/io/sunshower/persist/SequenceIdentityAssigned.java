package io.sunshower.persist;

import java.io.Serializable;

public interface SequenceIdentityAssigned<ID extends Serializable> {

  Sequence<ID> getSequence();
}
