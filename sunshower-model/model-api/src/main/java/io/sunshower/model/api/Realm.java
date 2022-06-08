package io.sunshower.model.api;

import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/** a realm a source of user information such as OAuth, databases, or filesystems */
@Entity
@Table(name = "REALMS")
public class Realm extends TenantedEntity {

  @Setter
  @Getter(onMethod = @__({@Basic, @Column(name = "name")}))
  private String name;

  @Setter
  @Getter(onMethod = @__({@Basic, @Column(name = "provider")}))
  @Convert(converter = ClassConverter.class)
  private Class<?> provider;

  @Setter
  @Getter(
      onMethod =
          @__({
            @OneToMany,
            @JoinTable(
                name = "REALM_TO_USERS",
                joinColumns = @JoinColumn(name = "realm_id"),
                inverseJoinColumns = @JoinColumn(name = "user_id"))
          }))
  private List<User> users;
}
