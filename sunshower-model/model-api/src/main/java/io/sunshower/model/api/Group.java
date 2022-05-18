package io.sunshower.model.api;

import io.sunshower.persistence.id.Identifier;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
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
