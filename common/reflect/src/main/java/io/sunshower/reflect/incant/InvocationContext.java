package io.sunshower.reflect.incant;

import io.sunshower.lang.Refreshable;


public interface InvocationContext extends Refreshable {


    <T> ServiceDescriptor<T> resolve(String name);

    <T> ServiceDescriptor<T> resolve(Class<T> type, String name);


}
