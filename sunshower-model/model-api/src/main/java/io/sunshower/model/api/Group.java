package io.sunshower.model.api;

import io.sunshower.persistence.id.Identifier;
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
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "GROUPS")
public class Group extends AbstractEntity<Identifier> implements IconAware {

  @Setter
  @Getter(onMethod = @__(@Embedded))
  private Icon icon;

  @Setter
  @Getter(onMethod = @__({@Basic, @Column(name = "name")}))
  private String name;

  @Setter
  @Getter(onMethod = @__({@Basic, @Column(name = "description")}))
  private String description;

  @Setter
  @Getter(
      onMethod =
          @__({
            @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true),
            @JoinTable(
                name = "GROUPS_TO_ROLES",
                joinColumns = @JoinColumn(name = "group_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
          }))
  private Set<Role> roles;

  @Setter
  @Getter(
      onMethod =
          @__({
            @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true),
            @JoinTable(
                name = "GROUPS_TO_USERS",
                joinColumns = @JoinColumn(name = "group_id"),
                inverseJoinColumns = @JoinColumn(name = "user_id"))
          }))
  private Set<User> users;

  @Setter
  @Getter(
      onMethod =
          @__({
            @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true),
            @JoinTable(
                name = "PERMISSIONS_TO_USERS",
                joinColumns = @JoinColumn(name = "group_id"),
                inverseJoinColumns = @JoinColumn(name = "user_id"))
          }))
  private Set<Permission> permissions;
}
