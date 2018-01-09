package io.sunshower.test.ws;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.test.context.*;
import org.springframework.test.context.support.DefaultTestContextBootstrapper;

public class RESTBootstrapper extends DefaultTestContextBootstrapper
    implements TestContextBootstrapper {

  @Override
  protected List<ContextCustomizerFactory> getContextCustomizerFactories() {
    final List<ContextCustomizerFactory> customizerFactories =
        new ArrayList<>(super.getContextCustomizerFactories());
    customizerFactories.add(new RESTContextCustomizerFactory());
    return customizerFactories;
  }

  @Override
  protected MergedContextConfiguration processMergedContextConfiguration(
      MergedContextConfiguration mergedConfig) {
    final Class<?>[] classes = resolveClasses(mergedConfig);

    return new MergedContextConfiguration(
        mergedConfig.getTestClass(),
        mergedConfig.getLocations(),
        classes,
        mergedConfig.getContextInitializerClasses(),
        mergedConfig.getActiveProfiles(),
        mergedConfig.getPropertySourceLocations(),
        mergedConfig.getPropertySourceProperties(),
        mergedConfig.getContextCustomizers(),
        mergedConfig.getContextLoader(),
        getCacheAwareContextLoaderDelegate(),
        mergedConfig.getParent());
  }

  private Class<?>[] resolveClasses(MergedContextConfiguration mergedConfig) {
    List<Class<?>> results = new ArrayList<>(Arrays.asList(mergedConfig.getClasses()));
    results.add(JAXRSConfiguration.class);
    return results.toArray(new Class<?>[results.size()]);
  }
}
