package io.sunshower.reflect.incant;

import io.sunshower.reflect.incant.ServiceDescriptor;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.lang.reflect.Method;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;


@RunWith(JUnitPlatform.class)
public class ServiceDescriptorTest {

    @Test
    public void ensureRetrievingAUnitaryMethodWithAnEmptyParameterArrayProducesCorrectResult() throws Exception {
        class A {
            public String m() {
                return "M(" + "x" + ")";
            }
        }

        final io.sunshower.reflect.incant.ServiceDescriptor<A> serviceDescriptor =
                new ServiceDescriptor<>(A.class, "A", new Method[]{
                        A.class.getDeclaredMethod("m")

                });

        assertThat(serviceDescriptor.resolve("m", new Class[0]), is(not(nullValue())));

    }

    @Test
    public void ensureService() {
        
    }

}