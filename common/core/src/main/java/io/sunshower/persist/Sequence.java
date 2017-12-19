package io.sunshower.persist;


import java.io.Serializable;


public interface Sequence<ID extends Serializable> {

    ID next();

}
