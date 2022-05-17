package io.sunshower.model.api;

import io.sunshower.persistence.id.Identifier;
import java.util.Set;

/**
 * a tenant is a collection of users and realms
 */
public class Tenant extends AbstractEntity<Identifier> {

  private Set<User> users;

  private Set<Realm> realms;

}
