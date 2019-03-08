package io.sunshower.reflect.incant;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class MethodDescriptorTest {

  @Test
  @SuppressWarnings("unchecked")
  public void ensureAssignmentMatches() throws NoSuchMethodException {

    class A {
      public void m(List<String> s) {}
    }

    final io.sunshower.reflect.incant.MethodDescriptor descriptor =
        new io.sunshower.reflect.incant.MethodDescriptor(
            A.class, A.class.getMethod("m", List.class));
    assertThat(descriptor.matches(new Class[] {ArrayList.class}, "m"), is(true));
  }

  @Test
  public void ensureNullaryMethodWithNoArgumentsCanBeInvokedAndAssigned() throws Exception {
    class A {
      public void m() {}
    }

    final io.sunshower.reflect.incant.MethodDescriptor<A, Void> methodDescriptor =
        new io.sunshower.reflect.incant.MethodDescriptor<>(A.class, A.class.getDeclaredMethod("m"));
    Void a = methodDescriptor.invoke(new A());
    assertThat(a, is(nullValue()));
  }

  @Test
  public void ensureNullaryMethodReturningAValueCanBeInvokedAndAssigned() throws Exception {
    class A {
      public String m() {
        return "Frapper";
      }
    }

    final io.sunshower.reflect.incant.MethodDescriptor<A, String> methodDescriptor =
        new io.sunshower.reflect.incant.MethodDescriptor<>(A.class, A.class.getDeclaredMethod("m"));
    String a = methodDescriptor.invoke(new A());
    assertThat(a, is("Frapper"));
  }

  @Test
  public void ensureInvokingMethodReturningNothingOnParametersProducesExpectedResults()
      throws NoSuchMethodException {
    class A {
      public String s;

      public void m(String s) {
        this.s = s;
      }
    }

    final io.sunshower.reflect.incant.MethodDescriptor<A, String> methodDescriptor =
        new io.sunshower.reflect.incant.MethodDescriptor<>(
            A.class, A.class.getDeclaredMethod("m", String.class));
    A a = new A();
    methodDescriptor.invoke(a, new Object[] {"Bean"});
    assertThat(a.s, is("Bean"));
  }

  @Test
  public void ensureMethodDescriptorReturnsCorrectValueForNonVoidMethodWithParameters()
      throws NoSuchMethodException {

    class A {
      public String m(String s) {
        return "M(" + s + ")";
      }
    }

    final io.sunshower.reflect.incant.MethodDescriptor<A, String> methodDescriptor =
        new io.sunshower.reflect.incant.MethodDescriptor<>(
            A.class, A.class.getDeclaredMethod("m", String.class));
    A a = new A();
    String s = methodDescriptor.invoke(a, new Object[] {"Bean"});
    assertThat(s, is("M(Bean)"));
  }

  @Test
  public void ensureMethodMatchesSameMethod() throws NoSuchMethodException {
    class A {
      public String m(String s) {
        return "M(" + s + ")";
      }
    }

    Method m = A.class.getDeclaredMethod("m", String.class);
    final io.sunshower.reflect.incant.MethodDescriptor<A, String> methodDescriptor =
        new io.sunshower.reflect.incant.MethodDescriptor<>(A.class, m);
    assertThat(methodDescriptor.matches(m), is(true));
  }

  @Test
  public void ensureMethodDescriptorsAreEquivalentWhenTheirBackingMethodsAreEquivalent()
      throws NoSuchMethodException {
    class A {
      public String m(String s) {
        return "M(" + s + ")";
      }
    }

    Method m = A.class.getDeclaredMethod("m", String.class);
    final io.sunshower.reflect.incant.MethodDescriptor<A, String> methodDescriptor =
        new io.sunshower.reflect.incant.MethodDescriptor<>(A.class, m);

    final io.sunshower.reflect.incant.MethodDescriptor<A, String> b =
        new io.sunshower.reflect.incant.MethodDescriptor<>(A.class, m);
    assertThat(b.equals(methodDescriptor), is(true));
  }

  @Test
  public void ensureMethodDescriptorCanBeUsedAsAKeyInAMap() throws NoSuchMethodException {

    class A {
      public String m(String s) {
        return "M(" + s + ")";
      }
    }

    Method m = A.class.getDeclaredMethod("m", String.class);
    final io.sunshower.reflect.incant.MethodDescriptor<A, String> methodDescriptor =
        new io.sunshower.reflect.incant.MethodDescriptor<>(A.class, m);

    final io.sunshower.reflect.incant.MethodDescriptor<A, String> b =
        new io.sunshower.reflect.incant.MethodDescriptor<>(A.class, m);
    final Map<MethodDescriptor<?, ?>, Boolean> descriptors = new HashMap<>();
    descriptors.put(methodDescriptor, true);
    assertThat(descriptors.containsKey(b), is(true));
  }
}
