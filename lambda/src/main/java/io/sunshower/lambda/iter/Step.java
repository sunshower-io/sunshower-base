package io.sunshower.lambda.iter;


public interface Step<T, U, V extends Exception> {

    void read(T input);

    Iteratee.State getState();


    V getError();

    U getResult();

    void reset();

}
