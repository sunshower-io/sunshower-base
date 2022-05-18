package io.sunshower.model.api;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * a realm a source of user information such as OAuth, databases, or filesystems
 */
@Entity
@Table(name = "REALMS")
public class Realm extends TenantedEntity {

  @Setter
  @Getter(onMethod = @__({
      @Basic,
      @Column(name = "name")
  }))
  private String name;

  @Setter
  @Getter(onMethod = @__({
      @Basic,
      @Column(name = "provider")
  }))
  @Convert(converter = ClassConverter.class)
  private Class<?> provider;


  @Setter
  @Getter(onMethod = @__({
      @OneToMany,
      @JoinTable(name = "REALM_TO_USERS",
          joinColumns = @JoinColumn(name = "realm_id"),
          inverseJoinColumns = @JoinColumn(name = "user_id")
      )
  }))
  private List<User> users;

}
