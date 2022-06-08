package io.sunshower.model.api;

import io.sunshower.persistence.id.Identifier;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "ROLES")
public class Role extends AbstractEntity<Identifier> implements GrantedAuthority {

  @Setter
  @Getter(onMethod = @__({@Basic, @Column(name = "name")}))
  private String name;

  @Setter
  @Getter(
      onMethod =
          @__({
            @ManyToMany,
            @JoinTable(
                name = "ROLES_TO_USERS",
                joinColumns = @JoinColumn(name = "role_id"),
                inverseJoinColumns = @JoinColumn(name = "user_id"))
          }))
  private Set<User> users;

  @Override
  public String getAuthority() {
    return name;
  }

  public void setAuthority(String authority) {
    this.name = authority;
  }
}
