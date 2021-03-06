package io.sunshower.reflect.incant;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import io.sunshower.lang.PropertyAware;
import io.sunshower.lang.tuple.Pair;
import java.util.Collections;
import java.util.Set;
import org.junit.jupiter.api.Test;

class LazyPropertyAwareTest {

  @Test
  void ensurePropertyAwareHasPropertyReturnsFalseWhenNoPropertyIsAdded() {
    assertThat(new io.sunshower.reflect.incant.LazyPropertyAware().hasProperty("hello"), is(false));
  }

  @Test
  void ensureHasPropertyReturnsFalseWhenPropertiesIsEmpty() {

    PropertyAware aware = new io.sunshower.reflect.incant.LazyPropertyAware();
    aware.addProperty("whatever", "whatever");
    aware.removeProperty("whatever");
    assertThat(aware.hasProperty("whatever"), is(false));
  }

  @Test
  void ensureHasPropertyReturnsTrueWhenPropertyExists() {

    PropertyAware aware = new io.sunshower.reflect.incant.LazyPropertyAware();
    aware.addProperty("whatever", "whatever");
    assertThat(aware.hasProperty("whatever"), is(true));
  }

  @Test
  void ensureGetPropertyReturnsPropertyWhenPropertyExists() {
    PropertyAware aware = new io.sunshower.reflect.incant.LazyPropertyAware();
    aware.addProperty("whatever", "whatever");
    assertThat(aware.getProperty("whatever"), is("whatever"));
  }

  @Test
  void ensureGetPropertiesReturnsEmptySetWhenNoPropertiesHaveBeenAdded() {

    PropertyAware aware = new io.sunshower.reflect.incant.LazyPropertyAware();
    assertThat(aware.getProperties().isEmpty(), is(true));
  }

  @Test
  void ensureGetPropertiesReturnsPropertiesWhenPropertiesExist() {
    PropertyAware aware = new io.sunshower.reflect.incant.LazyPropertyAware();
    aware.addProperty("whatever", "whatever");
    Set<Pair<String, String>> results = Collections.singleton(Pair.of("whatever", "whatever"));
    assertThat(aware.getProperties(), is(results));
  }

  @Test
  void ensureGettingNonExistantPropertyReturnsNothing() {
    assertThat(
        new io.sunshower.reflect.incant.LazyPropertyAware().getProperty("whatever"),
        is(nullValue()));
  }

  @Test
  void ensureRemovingNonExistantPropertyReturnsNull() {
    assertThat(new LazyPropertyAware().removeProperty("whatever"), is(nullValue()));
  }
}
