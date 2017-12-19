package io.sunshower.persistence.core.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;



@RunWith(JUnitPlatform.class)
public class ClassConverterTest {
    private ClassConverter converter;

    @BeforeEach
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