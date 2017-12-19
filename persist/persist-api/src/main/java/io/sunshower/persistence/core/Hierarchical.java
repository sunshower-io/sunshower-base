package io.sunshower.persistence.core;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by haswell on 3/13/17.
 */
public interface Hierarchical<T, U extends Hierarchical<T, U>> {

    U getParent();


    Collection<U> getChildren();

    boolean addChild(U child);


    void setParent(U parent);
}
