package io.sunshower.model.api;

import java.util.List;

/**
 * a realm a source of user information such as OAuth, databases, or filesystems
 */
public class Realm {

  private String name;

  private Class<?> provider;

  private List<User> users;

}
