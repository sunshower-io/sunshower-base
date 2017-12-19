package io.sunshower.persist;

import java.io.Serializable;

/**
 * Created by haswell on 7/21/17.
 */
public interface SequenceIdentityAssigned<ID extends Serializable> {

    Sequence<ID> getSequence();

}
