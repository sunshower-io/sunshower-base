package io.sunshower.model.api;

import java.util.Set;
import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {




  private String name;
  private Set<User> users;

  @Override
  public String getAuthority() {
    return null;
  }

}
