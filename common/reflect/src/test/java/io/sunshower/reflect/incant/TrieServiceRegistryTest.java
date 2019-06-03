package io.sunshower.reflect.incant;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrieServiceRegistryTest {

  private io.sunshower.reflect.incant.OperationScanner objectScanner;

  @BeforeEach
  void setUp() {
    objectScanner = new OpScanner();
  }

  @Test
  void ensureScanningSimpleClassWithSingleMethodProducesExpectedResults() {
    class A {
      String a() {
        return "a";
      }
    }
    final io.sunshower.reflect.incant.TrieServiceRegistry registry =
        new io.sunshower.reflect.incant.TrieServiceRegistry(objectScanner, init(A.class));
    registry.refresh();
    final A instance = new A();
    io.sunshower.reflect.incant.ServiceDescriptor<A> service = registry.resolve("A");
    assertThat(service.getIdentifier(), is("A"));
    assertThat(service.resolve("a").invoke(instance), is("a"));
  }

  @Test
  void ensureScanningSimpleClassWithMultipleMethodsWithVaryingTypesProducesResults() {
    class A {
      String a() {
        return "a";
      }

      String b(String a, String b, String c) {
        return a + b + c;
      }
    }

    final io.sunshower.reflect.incant.TrieServiceRegistry registry =
        new TrieServiceRegistry(objectScanner, init(A.class));
    registry.refresh();
    final A instance = new A();
    io.sunshower.reflect.incant.ServiceDescriptor<A> service = registry.resolve("A");
    assertThat(service.getIdentifier(), is("A"));
    assertThat(service.resolve("a").invoke(instance), is("a"));
    assertThat(
        service
            .resolve("b", String.class, String.class, String.class)
            .invoke(instance, "a", "b", "c"),
        is("abc"));
  }

  static io.sunshower.reflect.incant.ServiceResolver init(Class<?> types) {
    return new TestServiceResolver(types);
  }

  private static class OpScanner implements OperationScanner {

    @Override
    public Set<io.sunshower.reflect.incant.ServiceDescriptor<?>> scan(Class<?> type) {
      return Collections.singleton(
          new ServiceDescriptor<>(type, type.getSimpleName(), type.getDeclaredMethods()));
    }
  }

  static class TestServiceResolver implements ServiceResolver {
    final Set<Class<?>> types;

    TestServiceResolver(Class<?>... types) {
      this.types = new HashSet<>(Arrays.asList(types));
    }

    @Override
    public Set<Class<?>> resolveServiceTypes() {
      return types;
    }
  }
}
