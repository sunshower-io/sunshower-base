package io.sunshower.test.ws;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.ContextCustomizer;
import org.springframework.test.context.MergedContextConfiguration;

class RESTConfigCustomizer implements ContextCustomizer {

  private final EnableJAXRS config;

  public RESTConfigCustomizer(EnableJAXRS config) {
    this.config = config;
  }

  @Override
  public void customizeContext(
      ConfigurableApplicationContext context, MergedContextConfiguration mergedConfig) {
    GenericApplicationContext ctx = (GenericApplicationContext) context;
    ctx.getBeanFactory().registerSingleton("SpringServerConfiguration" + config.hashCode(), config);
  }
}
