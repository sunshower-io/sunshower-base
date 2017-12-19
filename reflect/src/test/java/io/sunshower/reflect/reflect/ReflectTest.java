package io.sunshower.reflect.reflect;
import io.sunshower.lambda.Option;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static io.sunshower.reflect.reflect.Reflect.instantiate;
import static org.junit.jupiter.api.Assertions.assertThrows;


@RunWith(JUnitPlatform.class)
public class ReflectTest {


    @Test
    public void ensureReflectConstructorIsInaccessible() throws Exception {
        try {
            Constructor ctor = io.sunshower.reflect.reflect.Reflect.class.getDeclaredConstructor();
            ctor.setAccessible(true);
            ctor.newInstance();
        } catch(InvocationTargetException ex) {
            Assert.assertThat(ex.getTargetException().getMessage().startsWith("No reflect"), CoreMatchers.is(true));
        }
    }

    @Test
    public void ensureStreamCollectsSingleType() {
        class A{}
        List<Class<?>> types = io.sunshower.reflect.reflect.Reflect
                .linearSupertypes(A.class).collect(Collectors.toList());
        Assert.assertThat(types.contains(A.class), CoreMatchers.is(true));
        Assert.assertThat(types.size(), CoreMatchers.is(2));
        Assert.assertThat(types.contains(Object.class), CoreMatchers.is(true));
        Assert.assertThat(types.contains(A.class), CoreMatchers.is(true));
    }


    @Test
    public void ensureStreamCollectsOnlyTypesAnnotatedWithAnnotation() {
        @Uninherited
        class A {

        }

        List<Annotation> a = io.sunshower.reflect.reflect.Reflect.mapOverHierarchy(A.class,
                i -> Option.of(i.getAnnotation(Uninherited.class)))
                .collect(Collectors.toList());
        Assert.assertThat(a.size(), CoreMatchers.is(1));
    }

    @Test
    public void ensureStreamCollectsTypesOnFirstLinearSupertype() {
        @Uninherited
        class A { }
        class B extends A {}

        HashSet<Uninherited> collect = io.sunshower.reflect.reflect.Reflect.mapOverHierarchy(
                B.class, i -> Option.of(i.getAnnotation(Uninherited.class)))
                .collect(Collectors.toCollection(HashSet::new));
        Assert.assertThat(collect.size(), CoreMatchers.is(1));
    }


    @Test
    public void ensureStreamCollectsInterfaceOnInterface() {
        class A implements UninheritedIface {}


        HashSet<Uninherited> collect = io.sunshower.reflect.reflect.Reflect.mapOverHierarchy(
                A.class, i -> Option.of(i.getAnnotation(Uninherited.class)))
                .collect(Collectors.toCollection(HashSet::new));
        Assert.assertThat(collect.size(), CoreMatchers.is(1));
    }

    @Test
    public void ensureStreamCollectsannotationOnImplementingLinearSupertype() {
        class A implements UninheritedIface{}

        class B extends A{}


        HashSet<Uninherited> collect = io.sunshower.reflect.reflect.Reflect.mapOverHierarchy(
                B.class, i -> Option.of(i.getAnnotation(Uninherited.class)))
                .collect(Collectors.toCollection(HashSet::new));
        Assert.assertThat(collect.size(), CoreMatchers.is(1));

    }

    @Test
    public void ensureStreamCollectsAnnotationsInCorrectOrder() {

        @Uninherited("a")
        class A implements UninheritedIface, OtherInterface {}

        @Uninherited("b")
        class B extends A implements UninheritedIface{}

        List<String> collect = Reflect.mapOverHierarchy(
                B.class, i -> Option.of(i.getAnnotation(Uninherited.class)))
                .map(Uninherited::value).collect(Collectors.toList());
        Assert.assertThat(collect, CoreMatchers.is(Arrays.asList("b", "", "a", "", "test")));
    }

    @Test
    public void ensureReflectCannotInstantiateNonStaticClass() {
        class A {}
        assertThrows(
                io.sunshower.reflect.reflect.InstantiationException.class, 
                () -> instantiate(A.class));
    }

    @Test
    public void ensureAttemptingToInstantiateNonInnerClassWithPrivateMethodThrowsException() {
        assertThrows(
                io.sunshower.reflect.reflect.InstantiationException.class, () -> instantiate(PrivateConstructor.class));
    }

    @Test
    public void ensureConstructorThrowingExceptionPassesCorrectException() {
        try {
            instantiate(ConstructorThrowsException.class);
        } catch(io.sunshower.reflect.reflect.InstantiationException e)  {
            Assert.assertThat(e.getCause().getMessage(), CoreMatchers.is("woah"));
        }
    }

    @Test
    public void ensureInstantiatingInterfaceFails() {
        assertThrows(
                InstantiationException.class, 
                () -> instantiate(AbstractClass.class)
        );
    }


    @Uninherited("test")
    interface OtherInterface {}


    @Uninherited
    interface UninheritedIface {

    }

    static abstract class AbstractClass {
        public AbstractClass() {}
    }

    public static class ConstructorThrowsException {

        public ConstructorThrowsException() {
            throw new IllegalStateException("woah");
        }

    }

    public static class PrivateConstructor {
        private PrivateConstructor(){}
    }

    @Retention(RetentionPolicy.RUNTIME)
    @interface Uninherited {
        String value() default "";
    }

}