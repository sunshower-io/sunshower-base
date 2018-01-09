package io.sunshower.reflect.incant;

import java.util.Set;

public interface OperationScanner {

  Set<ServiceDescriptor<?>> scan(Class<?> type);
}
