package io.sunshower.lambda.iter;

import java.util.Iterator;


public interface Stream<T> extends Iterator<T> {

    T next();

    boolean hasNext();

}
