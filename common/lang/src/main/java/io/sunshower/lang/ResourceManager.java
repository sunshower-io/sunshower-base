package io.sunshower.lang;

public interface ResourceManager {

  boolean exists(String s);

  Resource forName(String s);

  Resource getResource(String s);

  ClassLoader getClassLoader();

  <T> Class<T> getClass(String name);

  <T> Class<T> getClass(String name, boolean load);

  default Package[] getPackages() {
    return Package.getPackages();
  }
}
