package io.sunshower.model.api;

import java.util.Collection;
import java.util.Collections;
import lombok.val;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class Session implements Authentication {

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    val authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null) {
      return authentication.getAuthorities();
    }
    return Collections.emptyList();
  }

  @Override
  public String getCredentials() {
    val principal = getPrincipal();
    return principal != null ? principal.getPassword() : null;
  }

  @Override
  public UserDetails getDetails() {
    val user = getPrincipal();
    if (user != null) {
      return user.getDetails();
    }
    return null;
  }

  @Override
  public User getPrincipal() {
    val authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null) {
      return (User) authentication.getPrincipal();
    }
    return null;
  }

  @Override
  public boolean isAuthenticated() {
    val principal = getPrincipal();
    return principal != null;
  }

  @Override
  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {}

  @Override
  public String getName() {
    val user = getPrincipal();
    return user != null ? user.getUsername() : null;
  }
}
