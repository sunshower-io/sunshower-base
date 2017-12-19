package io.sunshower.lang;

import java.util.Set;


public interface ObjectFactory {


    <T> T resolve(String name);

    <T> T resolve(Class<T> type);

    <T> T resolve(Class<T> type, String name);

    <T> Set<String> resolveNames(Class<T> type);

}
