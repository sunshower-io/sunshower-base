package io.sunshower.model.api;

import io.sunshower.persistence.id.Identifier;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

/** a tenant is a collection of users and realms */
@Entity
@Table(name = "TENANTS")
public class Tenant extends AbstractEntity<Identifier> implements IconAware {

  @Setter
  @Getter(onMethod = @__({@Basic, @Column(name = "name")}))
  private String name;

  @Setter
  @Getter(onMethod = @__(@Embedded))
  private Icon icon;

  @Setter
  @Getter(onMethod = @__({@Basic, @Column(name = "description")}))
  private String description;

  @Setter
  @Getter(onMethod = @__({@Basic, @Temporal(TemporalType.TIMESTAMP), @Column(name = "created")}))
  private Date created;

  @Setter
  @Getter(
      onMethod =
          @__({
            @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true),
            @JoinTable(
                name = "TENANTS_TO_USERS",
                joinColumns = @JoinColumn(name = "tenant_id"),
                inverseJoinColumns = @JoinColumn(name = "user_id"))
          }))
  private Set<User> users;

  private Set<Realm> realms;

  @Setter
  @Getter(onMethod = @__({@OneToOne(fetch = FetchType.LAZY), @JoinColumn(name = "parent_id")}))
  private Tenant parent;

  @Setter
  @Getter(onMethod = @__({@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)}))
  private Set<Tenant> children;

  public void addUser(User user) {
    if (users == null) {
      users = new HashSet<>();
    }
    if (user != null) {
      users.add(user);
      user.setTenant(this);
    }
  }

  public void removeUser(User user) {
    if (users == null) {
      users = new HashSet<>();
    }
    if (user != null) {
      users.remove(user);
      user.setTenant(null);
    }
  }
}
