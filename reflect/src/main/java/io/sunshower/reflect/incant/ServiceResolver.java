package io.sunshower.reflect.incant;

import java.util.Set;

/**
 * Created by haswell on 4/10/16.
 */
public interface ServiceResolver {

    Set<Class<?>> resolveServiceTypes();
}
