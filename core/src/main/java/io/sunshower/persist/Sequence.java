package io.sunshower.persist;


import java.io.Serializable;

/**
 * Created by haswell on 7/18/17.
 */
public interface Sequence<ID extends Serializable> {

    ID next();

}
