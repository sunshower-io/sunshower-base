package io.sunshower.persistence.core.converters;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


/**
 * Created by haswell on 5/11/17.
 */
public class ClassConverterTest {
    private ClassConverter converter;

    @Before
    public void setUp() {
        converter = new ClassConverter();
    }

    @Test
    public void ensureVoidIsInClassLoader() throws ClassNotFoundException {
        Class.forName("java.lang.Void");
    }

    @Test
    public void ensureConvertedWorksForVoid() {
        assertThat(converter.convertToDatabaseColumn(Void.class), is("java.lang.Void"));
    }

    @Test
    public void ensureConverterWorksForNull() {
        assertThat(converter.convertToDatabaseColumn(null), is("java.lang.Void"));
    }

}