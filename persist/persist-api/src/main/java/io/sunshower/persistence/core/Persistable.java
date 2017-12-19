package io.sunshower.persistence.core;

import java.io.Serializable;

import io.sunshower.common.Identifier;
import io.sunshower.persist.Sequence;

/**
 * Created by haswell on 2/23/17.
 */
public interface Persistable<ID extends Serializable> {

    ID getId();

    void setId(ID id);


    String toString();


    int hashCode();

    Identifier getIdentifier();

    boolean equals(Object o);

    
    Sequence<ID> getSequence();

}
