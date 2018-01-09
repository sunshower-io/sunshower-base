package io.sunshower.lang;

import io.sunshower.lang.tuple.Pair;
import java.util.Set;

public interface PropertyAware {

  boolean hasProperty(String key);

  String getProperty(String key);

  String removeProperty(String key);

  Set<Pair<String, String>> getProperties();

  boolean addProperty(String key, String value);
}
