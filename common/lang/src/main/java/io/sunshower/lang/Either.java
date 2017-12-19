package io.sunshower.lang;


public abstract class Either<K, V> {


    public abstract boolean isLeft();

    public abstract boolean isRight();


}
