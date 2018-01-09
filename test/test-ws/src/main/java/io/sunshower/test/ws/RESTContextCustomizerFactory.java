package io.sunshower.test.ws;

import java.util.List;
import org.springframework.lang.Nullable;
import org.springframework.test.context.ContextConfigurationAttributes;
import org.springframework.test.context.ContextCustomizer;
import org.springframework.test.context.ContextCustomizerFactory;

public class RESTContextCustomizerFactory implements ContextCustomizerFactory {

  @Nullable
  @Override
  public ContextCustomizer createContextCustomizer(
      Class<?> testClass, List<ContextConfigurationAttributes> configAttributes) {
    EnableJAXRS config = resolveConfig(testClass);
    return new RESTConfigCustomizer(config);
  }

  EnableJAXRS resolveConfig(Class<?> type) {
    for (Class<?> current = type; current != null; current = type.getSuperclass()) {
      if (type.isAnnotationPresent(EnableJAXRS.class)) {
        return type.getAnnotation(EnableJAXRS.class);
      }
    }
    throw new IllegalStateException(
        String.format("No @EnableJAXRS found in class hierarchy (class: %s)", type));
  }
}
