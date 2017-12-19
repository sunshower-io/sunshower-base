package io.sunshower.reflect.incant;

import io.sunshower.lang.PropertyAware;
import io.sunshower.lang.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.util.Collections;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;


@RunWith(JUnitPlatform.class)
public class LazyPropertyAwareTest {


    @Test
    public void ensurePropertyAwareHasPropertyReturnsFalseWhenNoPropertyIsAdded() {
        assertThat(new io.sunshower.reflect.incant.LazyPropertyAware().hasProperty("hello"), is(false));
    }

    @Test
    public void ensureHasPropertyReturnsFalseWhenPropertiesIsEmpty () {

        PropertyAware aware = new io.sunshower.reflect.incant.LazyPropertyAware();
        aware.addProperty("whatever", "whatever");
        aware.removeProperty("whatever");
        assertThat(aware.hasProperty("whatever"), is(false));
    }

    @Test
    public void ensureHasPropertyReturnsTrueWhenPropertyExists() {

        PropertyAware aware = new io.sunshower.reflect.incant.LazyPropertyAware();
        aware.addProperty("whatever", "whatever");
        assertThat(aware.hasProperty("whatever"), is(true));
    }

    @Test
    public void ensureGetPropertyReturnsPropertyWhenPropertyExists() {
        PropertyAware aware = new io.sunshower.reflect.incant.LazyPropertyAware();
        aware.addProperty("whatever", "whatever");
        assertThat(aware.getProperty("whatever"), is("whatever"));
    }


    @Test
    public void ensureGetPropertiesReturnsEmptySetWhenNoPropertiesHaveBeenAdded() {

        PropertyAware aware = new io.sunshower.reflect.incant.LazyPropertyAware();
        assertThat(aware.getProperties().isEmpty(), is(true));
    }

    @Test
    public void ensureGetPropertiesReturnsPropertiesWhenPropertiesExist() {
        PropertyAware aware = new io.sunshower.reflect.incant.LazyPropertyAware();
        aware.addProperty("whatever", "whatever");
        Set<Pair<String, String>> results = Collections.singleton(Pair.of("whatever", "whatever"));
        assertThat(aware.getProperties(), is(results));
    }

    @Test
    public void ensureGettingNonExistantPropertyReturnsNothing() {
        assertThat(new io.sunshower.reflect.incant.LazyPropertyAware().getProperty("whatever"), is(nullValue()));
    }

    @Test
    public void ensureRemovingNonExistantPropertyReturnsNull() {
        assertThat(new LazyPropertyAware().removeProperty("whatever"), is(nullValue()));
    }
}