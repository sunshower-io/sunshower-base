package io.sunshower.model.api;

import io.sunshower.persistence.id.Identifier;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.Set;
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
