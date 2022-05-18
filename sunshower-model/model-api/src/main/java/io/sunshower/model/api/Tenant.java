package io.sunshower.model.api;

import io.sunshower.persistence.id.Identifier;
import java.util.Date;
import java.util.Set;

/**
 * a tenant is a collection of users and realms
 */
public class Tenant extends AbstractEntity<Identifier> {

  private String name;

  private String description;

  private Date created;


  private Set<User> users;

  private Set<Realm> realms;

  private Tenant parent;
  private Set<Tenant> children;
}
