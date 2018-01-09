package io.sunshower.reflect.incant;

import java.util.Set;

public interface ServiceResolver {

  Set<Class<?>> resolveServiceTypes();
}
