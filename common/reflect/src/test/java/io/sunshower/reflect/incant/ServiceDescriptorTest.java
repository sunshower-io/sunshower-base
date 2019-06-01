package io.sunshower.reflect.incant;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.lang.reflect.Method;
import org.junit.jupiter.api.Test;

class ServiceDescriptorTest {

  @Test
  void ensureRetrievingAUnitaryMethodWithAnEmptyParameterArrayProducesCorrectResult()
      throws Exception {
    class A {
      String m() {
        return "M(" + "x" + ")";
      }
    }

    final io.sunshower.reflect.incant.ServiceDescriptor<A> serviceDescriptor =
        new ServiceDescriptor<>(A.class, "A", new Method[] {A.class.getDeclaredMethod("m")});

    assertThat(serviceDescriptor.resolve("m", new Class[0]), is(not(nullValue())));
  }

  @Test
  void ensureService() {}
}
